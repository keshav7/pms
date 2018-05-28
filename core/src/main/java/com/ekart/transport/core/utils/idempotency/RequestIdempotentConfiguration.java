package com.ekart.transport.core.utils.idempotency;

import com.ekart.transport.core.utils.logging.LogExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class RequestIdempotentConfiguration {
    @Bean
    public RequestIdempotentAspect getRequestIdempotentAspect() {
        return new RequestIdempotentAspect();
    }
}
