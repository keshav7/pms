package com.ekart.transport.core.utils.idempotency;

import com.ekart.transport.core.domain.ApiRequest;
import com.ekart.transport.core.dto.response.ClientResponse;
import com.ekart.transport.core.enums.CoreErrors;
import com.ekart.transport.core.repository.ApiRequestRepository;
import com.ekart.transport.core.service.api.ApiRequestService;
import com.ekart.transport.core.utils.Constants;
import com.ekart.transport.core.utils.PayloadUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;

@Aspect
@Slf4j
public class RequestIdempotentAspect {
    @Autowired
    private ApiRequestRepository apiRequestRepository;

    @Autowired
    private ApiRequestService apiRequestService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(@com.ekart.transport.core.utils.idempotency.RequestIdempotent  * *(..))")
    public void isAnnotated() {}


    @Around("isAnnotated()")
    @Transactional
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object[] signatureArgs = joinPoint.getArgs();
        HttpServletRequest servletRequest = null;
        for (Object signatureArg: signatureArgs) {
            if(signatureArg instanceof HttpServletRequest) {
                servletRequest = (HttpServletRequest) signatureArg;
                break;
            }
        }

        if(servletRequest != null) {
            Map<String, String> requestHeaders = getHeaders(servletRequest);
            if (!requestHeaders.containsKey(Constants.REQUEST_ID_HEADER)) {
                ClientResponse clientResponse = new ClientResponse<>(CoreErrors.REQUEST_ID_MISSING);
                return new ResponseEntity<>(clientResponse, clientResponse.getHttpStatus());
            }

            // Expects you are setting this attribute in request filter
            String requestPayload = PayloadUtils.getPayload(servletRequest);
            String queryString = servletRequest.getQueryString();
            String hashString = new StringBuilder().append(queryString).append(requestPayload).toString();
            String hash = null;
            if (hashString != null && !hashString.isEmpty()) {
                hash = hashString(hashString);
            }

            String requestId = requestHeaders.get(Constants.REQUEST_ID_HEADER);
            String requestName = joinPoint.getSignature().getName();

            List<ApiRequest> apiRequests = apiRequestRepository.findByRequestIdAndRequestName(requestId, requestName);

            if (CollectionUtils.isNotEmpty(apiRequests)) {
                if (!shouldRetry(joinPoint)) {
                    ApiRequest sameRequest = null;
                    for(ApiRequest apiRequest : apiRequests) {
                        if (apiRequest.getHash() != null && apiRequest.getHash().equals(hash)) {
                            sameRequest = apiRequest;
                            break;
                        }
                    }
                    if (sameRequest != null) {
                        log.info("Request Id already processed: " + requestId + " for method: " + requestName + " with same hash");
                        JsonNode responseEntity = objectMapper.readValue(sameRequest.getResponseBody(), JsonNode.class);
                        HttpStatus httpStatus = HttpStatus.valueOf(sameRequest.getResponseCode());
                        if(httpStatus.is2xxSuccessful()){
                            httpStatus = HttpStatus.IM_USED;
                        }
                        if (!Response.Status.Family.familyOf(httpStatus.value()).equals(Response.Status.Family.SERVER_ERROR)) {
                            return new ResponseEntity<>(responseEntity, httpStatus);
                        } else {
                            // TODO: This behavior can be made client driven. We can take internal status codes to retry upon.
                            log.info("Previous response code was " + httpStatus +" .Hence retrying");
                        }
                    } else {
                        log.info("Request Id already processed: " + requestId + " for method: " + requestName + " but different hash");
                        ClientResponse clientResponse = new ClientResponse(CoreErrors.REQUEST_ID_DUPLICATE_FOR_SAME_METHOD);
                        return new ResponseEntity<>(clientResponse, clientResponse.getHttpStatus());
                    }
                }
            }

            // Call the method implementation
            Object retVal = joinPoint.proceed();
            stopWatch.stop();

            // Saving API Request
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.setRequestId(requestId);
            apiRequest.setRequestName(requestName);
            apiRequest.setRequestUrl(servletRequest.getRequestURI());
            apiRequest.setRequestType(servletRequest.getMethod());
            apiRequest.setRequestHeaders(requestHeaders.toString());
            apiRequest.setRequestTime(new Date(stopWatch.getStartTime()));
            apiRequest.setResponseTime(new Date(stopWatch.getTime() + stopWatch.getStartTime()));
            apiRequest.setHash(hash);
            apiRequest.setRequestPayload(requestPayload);

            ResponseEntity responseEntity = (ResponseEntity) retVal;

            apiRequest.setResponseBody(objectMapper.writeValueAsString(objectMapper.valueToTree(responseEntity.getBody())));
            apiRequest.setResponseCode(responseEntity.getStatusCode().value());

            if (Response.Status.Family.familyOf(apiRequest.getResponseCode()).equals(Response.Status.Family.SUCCESSFUL)) {
                apiRequest.setSuccess(true);
            } else {
                apiRequest.setSuccess(false);
            }

            String requestedBy = requestHeaders.get(Constants.REQUESTED_BY_HEADER);
            apiRequest.setCreatedBy(requestedBy);
            apiRequest.setLastUpdatedBy(requestedBy);
            apiRequestService.saveApiRequest(apiRequest);

            return retVal;
        } else {
            log.warn("Request Idempotency annotation is put at a wrong place as ServletRequest is not passed");
            return joinPoint.proceed();
        }
    }

    private boolean shouldRetry(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestIdempotent requestIdempotent = method.getAnnotation(RequestIdempotent.class);
        return requestIdempotent.shouldRetry();
    }

    private Map<String, String> getHeaders(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        if (headerNames == null) return Collections.emptyMap();

        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        // Add cookies as headers for audit
        if (httpServletRequest.getCookies() == null) return headers;

        Arrays.stream(httpServletRequest.getCookies()).forEach(cookie -> headers.put("Cookie-" + cookie.getName(), cookie.getValue()));

        return headers;
    }

    private String hashString(String requestPayload) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = requestPayload.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            return DigestUtils.sha1Hex(digest);
        } catch (Exception e) {
            log.error("Hashing Error ", e);
            throw new Exception("Hashing Error");
        }
    }
}
