package com.ekart.transport.core.statemachine.api;

/**
 * Created by surender.s on 15/11/17.
 */
public interface TransitionCompleteHook <S, E, C, Entity extends StatefulEntity<S, ?>> {
    void performAction(S from, S to, E on, C context, Entity entity);
}
