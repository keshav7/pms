package com.ekart.transport.core.dto;

import lombok.Data;

@Data
public class EmailInfoConfigDto {
    private EmailModel.EmailContact sender;
    private EmailModel.EmailContact receiver;
    private EmailModel.Template template;
    private EmailModel.TrackingInfo trackingInfo;
}
