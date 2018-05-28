package com.ekart.transport.core.service.api;

import com.flipkart.restbus.client.entity.OutboundMessage;

import java.util.List;

public interface OutboundMessageService {
    void persistOutboundMessage(OutboundMessage outboundMessage);
    void persistOutboundMessages(List<OutboundMessage> outboundMessages);
}
