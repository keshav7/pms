package com.ekart.transport.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "api_request")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class ApiRequest extends BaseEntity {

    @Column(name="request_id")
    private String requestId;

    @Column(name="request_name")
    private String requestName;

    @Column(name="type")
    private String requestType;

    @Column(name="url")
    private String requestUrl;

    @Column(name="request_headers", columnDefinition = "LONGTEXT")
    private String requestHeaders;

    @Column(name="request_payload", columnDefinition = "LONGTEXT")
    private String requestPayload;

    @Column(name="response_code")
    private Integer responseCode;

    @Column(name="response_headers", columnDefinition = "LONGTEXT")
    private String responseHeaders;

    @Column(name="response_body", columnDefinition = "LONGTEXT")
    private String responseBody;

    @Column(name="hash")
    private String hash;

    @Column(name="request_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;

    @Column(name="response_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseTime;

    @Column(name="success")
    private Boolean success;
}
