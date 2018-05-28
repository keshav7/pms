package com.ekart.transport.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by rijil.koyyodan on 27/02/18.
 */
@Slf4j
public abstract class DefaultClient {

    private Client client;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public DefaultClient(Integer connectTimeOut) {
        this.client = createClient(connectTimeOut);
    }

    private Client createClient(Integer connectTimeOut) {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, connectTimeOut);
        client = Client.create(clientConfig);
        return client;
    }

    public com.sun.jersey.api.client.ClientResponse  call(String url, MediaType mediaType, CallType callType,
                                                          Object requestMap, Map<String, Object> headerMap) {
        com.sun.jersey.api.client.ClientResponse response;
        WebResource.Builder builder = client.resource(url).accept(MediaType.APPLICATION_JSON_TYPE).type(mediaType);
        if (headerMap != null) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        long startTime = System.currentTimeMillis();
        if (callType.equals(CallType.POST))
            response = builder.post(com.sun.jersey.api.client.ClientResponse.class, requestMap);
        else if (callType.equals(CallType.PUT))
            response = builder.put(com.sun.jersey.api.client.ClientResponse.class, requestMap);
        else
            response = builder.get(com.sun.jersey.api.client.ClientResponse.class);
        long endTime = System.currentTimeMillis();
        String printObject = null;
        try {
            printObject = objectMapper.writeValueAsString(requestMap);
        } catch (Exception e) {
            log.warn("Error in printing obejct", e);
        }
        log.info("Response of Calling " + callType + " request for "
                + url + " with body: " + printObject + " and headers : " + headerMap + " is : " + response
                + " and took " + (endTime - startTime) + " millis.");
        return response;
    }


    public enum CallType {
        POST,
        PUT,
        GET
    }
}
