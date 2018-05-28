package com.ekart.transport.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditedBaseEntity is a Querydsl query type for AuditedBaseEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAuditedBaseEntity extends EntityPathBase<AuditedBaseEntity> {

    private static final long serialVersionUID = 1399048560L;

    public static final QAuditedBaseEntity auditedBaseEntity = new QAuditedBaseEntity("auditedBaseEntity");

    public final DateTimePath<org.joda.time.DateTime> createdAt = createDateTime("createdAt", org.joda.time.DateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<org.joda.time.DateTime> lastUpdatedAt = createDateTime("lastUpdatedAt", org.joda.time.DateTime.class);

    public final StringPath lastUpdatedBy = createString("lastUpdatedBy");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QAuditedBaseEntity(String variable) {
        super(AuditedBaseEntity.class, forVariable(variable));
    }

    public QAuditedBaseEntity(Path<? extends AuditedBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditedBaseEntity(PathMetadata metadata) {
        super(AuditedBaseEntity.class, metadata);
    }

}

