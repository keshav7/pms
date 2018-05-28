package com.ekart.transport.core.web.filter;

import com.ekart.transport.core.utils.Constants;
import com.ekart.transport.core.utils.PayloadUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class CustomFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CustomFilter.class);
    private static final int maxOutputLength = 8192;
    private static final int maxInputLength = 8192;
    public static final String BODY = "body";
    private static final String SEPARATOR = " :: ";
    private AtomicLong id = new AtomicLong(1);

    public CustomFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    protected Map<String, Object> storeRequestTrace(HttpServletRequest request) {

        Map<String, Object> trace = new HashMap<>();
        trace.put("method", request.getMethod());
        trace.put("path", request.getRequestURI());
        Map<String,String> headers = new HashMap<>();
        List<String> headersList = Collections.list(request.getHeaderNames());
        for(String headerName: headersList){
            headers.put(headerName,request.getHeader(headerName));
        }
        trace.put("headers", headers);
        trace.put("remoteUser", request.getRemoteUser());
        trace.put("query", request.getQueryString());

        return trace;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletRequest wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletResponse responseToCache = new ContentCachingResponseWrapper(httpServletResponse);
        if (!httpServletRequest.getRequestURI().equals(Constants.HEALTH_CHECK_URI) &&
                !Constants.SWAGGER_URIS.contains(httpServletRequest.getRequestURI())){

            long requestId = this.id.getAndIncrement();
            MDC.put("request-id", requestId);

            long startTime = System.currentTimeMillis();
            chain.doFilter(wrappedRequest, responseToCache);
            long endTime = System.currentTimeMillis();
            log.info("API Took {} millis. ",(endTime-startTime));

            Map<String, Object> trace = storeRequestTrace(httpServletRequest);
            String payload = PayloadUtils.getPayload(wrappedRequest);
            trace.put(BODY, payload);

            logRequestTrace(trace);
            logResponseTrace(responseToCache);

        } else {
            chain.doFilter(request, response);
        }
    }

    private void logResponseTrace(HttpServletResponse responseToCache) {
        log.info("********************* Response Trace ********************************");
        log.info("Response status code {} {}", SEPARATOR, responseToCache.getStatus());
        Collection<String> responseHeaderNames = responseToCache.getHeaderNames();
        Set<String> responseHeaders = new HashSet<>();
        for(String headerName : responseHeaderNames){
            responseHeaders.add(headerName + SEPARATOR + responseToCache.getHeader(headerName));
        }
        log.info("response headers {} {} ",SEPARATOR, responseHeaders);
        String responseData = null;
        try {
            responseData = PayloadUtils.getResponseData(responseToCache);
        } catch (IOException e) {
            log.error("error in logging response ", e);
        }
        if (responseData != null) {
            if (responseData.length() < maxOutputLength) {
                log.info("response {} {}", SEPARATOR, responseData);
            } else {
                log.info("response data exceeds max output length, it will be logged in debug level.");
                log.debug("response {} {}", SEPARATOR, responseData);
            }
        }
        log.info("********************* Response Trace ********************************");
    }



    private void logRequestTrace(Map<String, Object> trace) {

        log.info("********************* Request Trace ********************************");
        Iterator traceIterator = trace.entrySet().iterator();
        while(traceIterator.hasNext()){
            Map.Entry<String,Object> traceEntry = (Map.Entry)traceIterator.next();
            if(BODY.equals(traceEntry.getKey())){
                String payload = (String)traceEntry.getValue();
                if(payload != null) {
                    if (payload.length() < maxInputLength) {
                        log.info("request body {} {}",SEPARATOR, payload);
                    } else {
                        log.info("request body exceeds max input length, it will be logged in debug level.");
                        log.debug("request body {} {}", SEPARATOR, payload);
                    }
                }
            }else{
                log.info(traceEntry.getKey()+ SEPARATOR +traceEntry.getValue());
            }
        }
        log.info("********************* Request Trace ********************************");
    }


    @Override
    public void destroy() {

    }


}