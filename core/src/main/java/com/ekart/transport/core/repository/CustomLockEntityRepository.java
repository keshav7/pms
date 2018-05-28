package com.ekart.transport.core.repository;


import com.ekart.transport.core.domain.CustomLockEntity;
import com.ekart.transport.core.domain.enums.CustomLockNameEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sarathkumar.k on 22/05/17.
 */
public interface CustomLockEntityRepository extends CrudRepository<CustomLockEntity,Long>, CustomLockEntityRepositoryCustom {

    CustomLockEntity findByEntityNameAndEntityId(CustomLockNameEnum entityName, String entityId);

    List<CustomLockEntity> findByEntityNameAndEntityIdIn(CustomLockNameEnum entityName, List<String> entityIds);
}
