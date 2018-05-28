package com.ekart.transport.core.repository;

import com.ekart.transport.core.domain.ApiRequest;
import com.ekart.transport.core.exception.ServiceException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApiRequestRepository extends CrudRepository<ApiRequest, Long> {
    List<ApiRequest> findByRequestIdAndRequestName(String requestId, String requestName) throws ServiceException;
}
