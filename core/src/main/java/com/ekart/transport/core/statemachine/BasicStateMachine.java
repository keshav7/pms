package com.ekart.transport.core.statemachine;

import com.ekart.transport.core.statemachine.api.StatefulEntity;
import com.ekart.transport.core.statemachine.api.TransitionCompleteHook;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * Created by surender.s on 15/11/17.
 */

@Slf4j
public abstract class BasicStateMachine<T extends AbstractStateMachine<T, S, E, C>, S, E, C,
        Entity extends StatefulEntity<S, ?>,
        I extends TransitionCompleteHook<S, E, C, Entity>>
        extends AbstractStateMachine<T, S, E, C> {

    @Getter
    @NonNull
    public final Entity entity;
    @NonNull
    public final I transitionCompleteHook;
    @NonNull
    public final ApplicationContext applicationContext;

    @Override
    protected void afterTransitionDeclined(S from, E event, C context){
        log.info(String.format("Transition from %s on %s is declined", from,event));
        throw new RuntimeException(String.format("Transition from %s on %s is declined", from.toString(),event.toString()));
    }

    @Override
    @SneakyThrows
    protected void afterTransitionCausedException(S from, S to, E on, C context) {
        final Throwable targetException = getLastException().getTargetException();
        if (targetException != null) {
            handleTargetException(from, to, on, targetException);
        }
        throw getLastException();
    }

    private void handleTargetException(S from, S to, E on, Throwable targetException) throws Throwable {
        final String msg = String.format("Some exception happened during transition of %s from %s to %s on %s", getEntityString(entity), from, to, on);
        log.error(msg, targetException);

        throw targetException;

    }

    public BasicStateMachine(Entity entity,I transitionCompleteHook, ApplicationContext applicationContext) {
        this.entity = entity;
        this.transitionCompleteHook = transitionCompleteHook;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void beforeTransitionBegin(S from, E on, C context) {

    }


    @Override
    protected void afterTransitionCompleted(S from, S to, E on, C context) {
       if(to != null) {
           entity.setStatus(to);
           transitionCompleteHook.performAction(from, to, on, context, entity);
       }
    }

    private String getEntityString(Entity entity) {
        return String.format("%s with id: %s ", entity.getClass().getName(), entity.getId());
    }

    @Override
    protected void beforeActionInvoked(S from, S to, E on, C context) {
    }

    @Override
    protected void afterTransitionEnd(S from, S to, E on, C context) {

    }
}
