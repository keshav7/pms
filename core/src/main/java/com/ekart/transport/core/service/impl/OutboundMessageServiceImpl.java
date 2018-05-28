package com.ekart.transport.core.service.impl;

import com.ekart.transport.core.service.api.OutboundMessageService;
import com.flipkart.restbus.client.entity.OutboundMessage;
import com.flipkart.restbus.hibernate.client.OutboundMessageRepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class OutboundMessageServiceImpl implements OutboundMessageService {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${outbound.appId:Transport}")
    private String outboundAppId;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void persistOutboundMessage(OutboundMessage outboundMessage) {
        outboundMessage.setAppId(outboundAppId);
        outboundMessage.setMessageId(UUID.randomUUID().toString());
        OutboundMessageRepositoryFactory.getOutboundMessageRepository(entityManager.unwrap(Session.class)).persist(outboundMessage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void persistOutboundMessages(List<OutboundMessage> outboundMessages){
        for(OutboundMessage outboundMessage : outboundMessages){
            outboundMessage.setAppId(outboundAppId);
            outboundMessage.setMessageId(UUID.randomUUID().toString());
        }
        OutboundMessageRepositoryFactory.getOutboundMessageRepository(entityManager.unwrap(Session.class)).persist(outboundMessages);
    }
}
