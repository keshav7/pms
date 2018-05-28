package com.ekart.transport.core.service.impl;

import com.ekart.transport.core.dto.EmailModel;
import com.ekart.transport.core.dto.QueueConfigDto;
import com.ekart.transport.core.service.api.OutboundMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.restbus.client.entity.OutboundMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EmailService {

    private OutboundMessageService outboundMessageService;

    private QueueConfigDto queueConfigDto;

    private String connektSecret;

    private static EmailService instance = null;

    private static final String EMAIL_API_KEY = "x-api-key";

    public static EmailService getInstance(QueueConfigDto queueConfigDto,
                                           OutboundMessageService outboundMessageService,
                                           String connektSecret) {
        if(instance == null) {
            instance = new EmailService(queueConfigDto, outboundMessageService, connektSecret);
        }
        return instance;
    }

    private EmailService(QueueConfigDto queueConfigDto, OutboundMessageService outboundMessageService, String connektSecret) {
        this.queueConfigDto = queueConfigDto;
        this.outboundMessageService = outboundMessageService;
        this.connektSecret = connektSecret;
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public void sendEmail(List<EmailModel.EmailContact> contacts, Map<String, Object> data, EmailModel.Template template, EmailModel.EmailContact sender) throws Exception {
        EmailModel.EmailHeader header = EmailModel.EmailHeader.builder()
                .type(template.getMode())
                .from(sender)
                .to(contacts)
                .build();

        EmailModel emailModel = EmailModel.builder()
                .contextId("TRANSPORT")
                .sla("H")
                .stencilId(template.getTemplateId())
                .channelInfo(header)
                .channelDataModel(data)
                .build();

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(EMAIL_API_KEY, connektSecret);

        OutboundMessage outboundMessage = getOutboundMessageForEmail(emailModel, queueConfigDto, headerMap);
        outboundMessageService.persistOutboundMessage(outboundMessage);
    }

    private OutboundMessage getOutboundMessageForEmail(EmailModel emailModel, QueueConfigDto queueConfigDto, Map<String, Object> headerMap) throws Exception {
        OutboundMessage outboundMessage = new OutboundMessage();
        try {
            outboundMessage.setMessage(objectMapper.writeValueAsString(emailModel));
        } catch (JsonProcessingException e) {
            String msg = "Cannot create email.";
            log.error(msg, e);
            throw new Exception(msg);
        }
        outboundMessage.setExchangeName(queueConfigDto.getExchangeName());
        outboundMessage.setExchangeType(QueueConfigDto.exchangeType);
        outboundMessage.setHttpMethod(queueConfigDto.getHttpMethod());
        outboundMessage.setHttpUri(queueConfigDto.getHttpUri());
        outboundMessage.setCustomHeaders(objectMapper.writeValueAsString(headerMap));
        return outboundMessage;
    }
}
