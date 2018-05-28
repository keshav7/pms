package com.ekart.transport.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventBaseEntity is a Querydsl query type for EventBaseEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QEventBaseEntity extends EntityPathBase<EventBaseEntity> {

    private static final long serialVersionUID = -1617813808L;

    public static final QEventBaseEntity eventBaseEntity = new QEventBaseEntity("eventBaseEntity");

    public final DateTimePath<org.joda.time.DateTime> createdAt = createDateTime("createdAt", org.joda.time.DateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEventBaseEntity(String variable) {
        super(EventBaseEntity.class, forVariable(variable));
    }

    public QEventBaseEntity(Path<? extends EventBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventBaseEntity(PathMetadata metadata) {
        super(EventBaseEntity.class, metadata);
    }

}

