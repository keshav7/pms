package com.ekart.transport.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueConfigDto {
    public static final String exchangeType = "queue";
    private String exchangeName;
    private String httpUri;
    private String httpMethod;
    private String replyTo;
    private String replyToHttpUri;
    private String replyToHttpMethod;
}
