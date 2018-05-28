package com.ekart.transport.core.statemachine.api;

/**
 * Created by surender.s on 15/11/17.
 */
public interface StatefulEntity<S, ID> {

    S getStatus();

    void setStatus(S status);

    ID getId();
}
