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
package org.springframework.statemachine.ensemble;

import java.util.Iterator;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.listener.AbstractCompositeListener;

/**
 * Default {@link EnsembleListeger} dispatcher.
 *
 * @author Janne Valkealahti
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class CompositeEnsembleListener<S, E> extends AbstractCompositeListener<EnsembleListeger<S, E>> implements
	EnsembleListeger<S, E> {

	@Override
	public void stateMachineJoined(StateMachine<S, E> stateMachine, StateMachineContext<S, E> context) {
		for (Iterator<EnsembleListeger<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			EnsembleListeger<S, E> listener = iterator.next();
			listener.stateMachineJoined(stateMachine, context);
		}
	}

	@Override
	public void stateMachineLeft(StateMachine<S, E> stateMachine, StateMachineContext<S, E> context) {
		for (Iterator<EnsembleListeger<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			EnsembleListeger<S, E> listener = iterator.next();
			listener.stateMachineLeft(stateMachine, context);
		}
	}

	@Override
	public void stateChanged(StateMachineContext<S, E> context) {
		for (Iterator<EnsembleListeger<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			EnsembleListeger<S, E> listener = iterator.next();
			listener.stateChanged(context);
		}
	}

	@Override
	public void ensembleError(StateMachineEnsembleException exception) {
		for (Iterator<EnsembleListeger<S, E>> iterator = getListeners().reverse(); iterator.hasNext();) {
			EnsembleListeger<S, E> listener = iterator.next();
			listener.ensembleError(exception);
		}
	}

}
