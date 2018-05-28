package com.ekart.transport.core.service.api;


import com.ekart.transport.core.domain.CustomLockEntity;
import com.ekart.transport.core.domain.enums.CustomLockNameEnum;

import java.util.List;

/**
 * Created by sarathkumar.k on 23/05/17.
 */
public interface CustomLockEntityService {

    CustomLockEntity createLockEntry(CustomLockNameEnum entityName, String entityId) throws Exception;

    List<CustomLockEntity> createLockEntry(CustomLockNameEnum entityName, List<String> entityId) throws Exception;

    void acquireLock(CustomLockNameEnum entityName, String entityId) throws Exception;

    void acquireLocks(CustomLockNameEnum entityName, List<String> entityId) throws Exception;
}
