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
package org.springframework.statemachine.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachineContext;

/**
 * Default implementation of a {@link StateMachineContext}.
 *
 * @author Janne Valkealahti
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class DefaultStateMachineContext<S, E> implements StateMachineContext<S, E> {

	private final List<StateMachineContext<S, E>> childs;
	private final S state;
	private final E event;
	private final Map<String, Object> eventHeaders;
	private final ExtendedState extendedState;

	/**
	 * Instantiates a new default state machine context.
	 *
	 * @param state the state
	 * @param event the event
	 * @param eventHeaders the event headers
	 * @param extendedState the extended state
	 */
	public DefaultStateMachineContext(S state, E event, Map<String, Object> eventHeaders, ExtendedState extendedState) {
		this(new ArrayList<StateMachineContext<S, E>>(), state, event, eventHeaders, extendedState);
	}

	/**
	 * Instantiates a new default state machine context.
	 *
	 * @param childs the child state machine contexts
	 * @param state the state
	 * @param event the event
	 * @param eventHeaders the event headers
	 * @param extendedState the extended state
	 */
	public DefaultStateMachineContext(List<StateMachineContext<S, E>> childs, S state, E event,
			Map<String, Object> eventHeaders, ExtendedState extendedState) {
		this.childs = childs;
		this.state = state;
		this.event = event;
		this.eventHeaders = eventHeaders;
		this.extendedState = extendedState;
	}

	@Override
	public List<StateMachineContext<S, E>> getChilds() {
		return childs;
	}

	@Override
	public S getState() {
		return state;
	}

	@Override
	public E getEvent() {
		return event;
	}

	@Override
	public Map<String, Object> getEventHeaders() {
		return eventHeaders;
	}

	@Override
	public ExtendedState getExtendedState() {
		return extendedState;
	}

	@Override
	public String toString() {
		return "DefaultStateMachineContext [state=" + state + ", event=" + event + ", eventHeaders=" + eventHeaders
				+ ", extendedState=" + extendedState + "]";
	}

}
