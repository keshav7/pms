package com.ekart.transport.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailModel {
    private String sla;
    private String contextId;
    private EmailHeader channelInfo;
    private Map<String, Object> channelDataModel;
    private String stencilId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmailHeader {
        private String type;
        private List<EmailContact> to;
        private List<EmailContact> cc;
        private List<EmailContact> bcc;
        private EmailContact from;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Template {
        private String mode;
        private String templateId;
        private String clientName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmailContact {
        private String address;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackingInfo {
        private String campaignName;
        private String campaignSource;
    }
}
