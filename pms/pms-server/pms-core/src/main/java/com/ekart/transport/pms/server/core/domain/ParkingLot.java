package com.ekart.transport.pms.server.core.domain;

import com.ekart.transport.core.domain.AuditedBaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by keshav.gupta on 28/05/18.
 */
@Data
@Entity
@Table(name = "parking_lot")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class ParkingLot extends AuditedBaseEntity {

}
