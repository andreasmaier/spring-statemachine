/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.statemachine.listener;

import java.util.Iterator;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * Default {@link StateMachineListener} dispatcher.
 *
 * @author Janne Valkealahti
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class CompositeStateMachineListener<S,E> extends AbstractCompositeListener<StateMachineListener<S,E>> implements
		StateMachineListener<S, E> {

	@Override
	public void stateChanged(State<S, E> from, State<S, E> to) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateChanged(from, to);
		}
	}

	@Override
	public void stateEntered(State<S, E> state) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateEntered(state);
		}
	}

	@Override
	public void stateExited(State<S, E> state) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateExited(state);
		}
	}

	@Override
	public void eventNotAccepted(Message<E> event) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.eventNotAccepted(event);
		}
	}

	@Override
	public void transition(Transition<S, E> transition) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.transition(transition);
		}
	}

	@Override
	public void transitionStarted(Transition<S, E> transition) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.transitionStarted(transition);
		}
	}

	@Override
	public void transitionEnded(Transition<S, E> transition) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.transitionEnded(transition);
		}
	}

	@Override
	public void stateMachineStarted(StateMachine<S, E> stateMachine) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateMachineStarted(stateMachine);
		}
	}

	@Override
	public void stateMachineStopped(StateMachine<S, E> stateMachine) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateMachineStopped(stateMachine);
		}
	}

	@Override
	public void stateMachineError(StateMachine<S, E> stateMachine, Exception exception) {
		for (Iterator<StateMachineListener<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			StateMachineListener<S, E> listener = iterator.next();
			listener.stateMachineError(stateMachine, exception);
		}
	}

}
