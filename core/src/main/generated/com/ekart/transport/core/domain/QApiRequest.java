package com.ekart.transport.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QApiRequest is a Querydsl query type for ApiRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QApiRequest extends EntityPathBase<ApiRequest> {

    private static final long serialVersionUID = -1860156589L;

    public static final QApiRequest apiRequest = new QApiRequest("apiRequest");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<org.joda.time.DateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath hash = createString("hash");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<org.joda.time.DateTime> lastUpdatedAt = _super.lastUpdatedAt;

    //inherited
    public final StringPath lastUpdatedBy = _super.lastUpdatedBy;

    public final StringPath requestHeaders = createString("requestHeaders");

    public final StringPath requestId = createString("requestId");

    public final StringPath requestName = createString("requestName");

    public final StringPath requestPayload = createString("requestPayload");

    public final DateTimePath<java.util.Date> requestTime = createDateTime("requestTime", java.util.Date.class);

    public final StringPath requestType = createString("requestType");

    public final StringPath requestUrl = createString("requestUrl");

    public final StringPath responseBody = createString("responseBody");

    public final NumberPath<Integer> responseCode = createNumber("responseCode", Integer.class);

    public final StringPath responseHeaders = createString("responseHeaders");

    public final DateTimePath<java.util.Date> responseTime = createDateTime("responseTime", java.util.Date.class);

    public final BooleanPath success = createBoolean("success");

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QApiRequest(String variable) {
        super(ApiRequest.class, forVariable(variable));
    }

    public QApiRequest(Path<? extends ApiRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApiRequest(PathMetadata metadata) {
        super(ApiRequest.class, metadata);
    }

}

