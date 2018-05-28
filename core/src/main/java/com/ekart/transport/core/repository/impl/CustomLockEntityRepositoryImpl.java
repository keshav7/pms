package com.ekart.transport.core.repository.impl;


import com.ekart.transport.core.domain.CustomLockEntity;
import com.ekart.transport.core.domain.QCustomLockEntity;
import com.ekart.transport.core.domain.enums.CustomLockNameEnum;
import com.ekart.transport.core.repository.CustomLockEntityRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 */
@Slf4j
public class CustomLockEntityRepositoryImpl implements CustomLockEntityRepositoryCustom {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    @Value("${lockConfig.lockTimeOut:10000}")
    private Integer lockTimeOut;

    QCustomLockEntity qCustomLockEntity = QCustomLockEntity.customLockEntity;
    
    @Override
    public CustomLockEntity findByEntityNameAndEntityId(CustomLockNameEnum entityName, String entityId, LockModeType lockMode) {
        BooleanBuilder builder = getBuilder(entityName, entityId);
        if (builder.getValue() != null) {
            JPAQuery jpaQuery = getJpaQuery(builder, lockMode);
            List<CustomLockEntity> entities = jpaQuery.select(qCustomLockEntity).fetch();
            if(CollectionUtils.isEmpty(entities)) {
                return null;
            }
            return entities.get(0);
        }
        return null;
    }

    @Override
    public List<CustomLockEntity> findByEntityNameAndEntityIdIn(CustomLockNameEnum entityName, List<String> entityIds, LockModeType lockMode) {
        if (CollectionUtils.isEmpty(entityIds)) {
            return null;
        }
        BooleanBuilder builder = getBuilder(entityName, entityIds);
        if (builder.getValue() != null) {
            JPAQuery jpaQuery = getJpaQuery(builder, lockMode);
            List<CustomLockEntity> entities = jpaQuery.select(qCustomLockEntity).fetch();
            if(CollectionUtils.isEmpty(entities)) {
                return null;
            }
            return entities;
        }
        return null;
    }

    private JPAQuery getJpaQuery(BooleanBuilder builder, LockModeType lockMode) {
        JPAQuery jpaQuery = new JPAQueryFactory(entityManager).from(qCustomLockEntity)
                .where(builder);
        if (lockMode != null) {
            jpaQuery.setLockMode(lockMode);
            jpaQuery.setHint("javax.persistence.query.timeout", lockTimeOut);
        }
        return jpaQuery;
    }

    private BooleanBuilder getBuilder(CustomLockNameEnum entityName, String entityId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (entityName != null) {
            builder.and(qCustomLockEntity.entityName.eq(entityName));
        }
        if (entityId != null) {
            builder.and(qCustomLockEntity.entityId.eq(entityId));
        }
        log.info("Predicate is : " + builder.getValue());
        return builder;
    }

    private BooleanBuilder getBuilder(CustomLockNameEnum entityName, List<String> entityIds) {
        BooleanBuilder builder = new BooleanBuilder();
        if (entityName != null) {
            builder.and(qCustomLockEntity.entityName.eq(entityName));
        }
        if (!CollectionUtils.isEmpty(entityIds)) {
            builder.and(qCustomLockEntity.entityId.in(entityIds));
        }
        log.info("Predicate is : " + builder.getValue());
        return builder;
    }
    
}
