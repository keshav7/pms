package com.ekart.transport.core.utils;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by ankush.a on 08/03/18.
 */
public class PayloadUtils {

    public static boolean isBinaryContent(final HttpServletRequest request) {
        if (request.getContentType() == null) {
            return true;
        }
        return request.getContentType().contains("image") ||
                request.getContentType().contains("video") ||
                request.getContentType().contains("audio");
    }

    public static boolean isMultipart(final HttpServletRequest request) {
        if (request.getContentType() == null)
            return true;
        return request.getContentType().contains("multipart/form-data");
    }

    public static String getPayload(HttpServletRequest wrappedRequest) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(wrappedRequest, ContentCachingRequestWrapper.class);
        String payload = null;
        if (wrappedRequest != null && !isBinaryContent(wrappedRequest)
                && !isMultipart(wrappedRequest)) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    payload = new String(buf, 0, buf.length, wrappedRequest.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }
            }
        }
        return payload;
    }

    public static String getResponseData(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return payload;
    }
}
