package com.ekart.transport.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomLockEntity is a Querydsl query type for CustomLockEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomLockEntity extends EntityPathBase<CustomLockEntity> {

    private static final long serialVersionUID = 1810076925L;

    public static final QCustomLockEntity customLockEntity = new QCustomLockEntity("customLockEntity");

    public final DateTimePath<org.joda.time.DateTime> createdAt = createDateTime("createdAt", org.joda.time.DateTime.class);

    public final StringPath entityId = createString("entityId");

    public final EnumPath<com.ekart.transport.core.domain.enums.CustomLockNameEnum> entityName = createEnum("entityName", com.ekart.transport.core.domain.enums.CustomLockNameEnum.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCustomLockEntity(String variable) {
        super(CustomLockEntity.class, forVariable(variable));
    }

    public QCustomLockEntity(Path<? extends CustomLockEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomLockEntity(PathMetadata metadata) {
        super(CustomLockEntity.class, metadata);
    }

}

