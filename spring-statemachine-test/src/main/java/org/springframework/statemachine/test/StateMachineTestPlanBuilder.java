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
package org.springframework.statemachine.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.statemachine.StateMachine;

/**
 * A builder for {@link StateMachineTestPlan}.
 *
 * @author Janne Valkealahti
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class StateMachineTestPlanBuilder<S, E> {

	private Map<Object, StateMachine<S, E>> stateMachines = new HashMap<Object, StateMachine<S, E>>();
	private final List<StateMachineTestPlanStep<S, E>> steps = new ArrayList<StateMachineTestPlanStep<S, E>>();
	private Integer defaultAwaitTime;

	/**
	 * Gets a new instance of this builder.
	 *
	 * @param <S> the type of state
	 * @param <E> the type of event
	 * @return the state machine test plan builder
	 */
	public static <S, E> StateMachineTestPlanBuilder<S, E> builder() {
		return new StateMachineTestPlanBuilder<S, E>();
	}

	/**
	 * Associate a state machine with this builder.
	 *
	 * @param stateMachine the state machine
	 * @return the state machine test plan builder
	 */
	public StateMachineTestPlanBuilder<S, E> stateMachine(StateMachine<S, E> stateMachine) {
		return stateMachine(stateMachine, stateMachine);
	}

	/**
	 * Associate a state machine with this builder.
	 *
	 * @param stateMachine the state machine
	 * @param machineId the machine id to use for sending
	 * @return the state machine test plan builder
	 */
	public StateMachineTestPlanBuilder<S, E> stateMachine(StateMachine<S, E> stateMachine, Object machineId) {
		this.stateMachines.put(machineId, stateMachine);
		return this;
	}

	/**
	 * Sets default await time. This is in seconds how long a latch
	 * will be waited for listening various callbacks.
	 *
	 * @param seconds the default await time in seconds
	 * @return the state machine test plan builder
	 */
	public StateMachineTestPlanBuilder<S, E> defaultAwaitTime(int seconds) {
		if (seconds < 0) {
			throw new IllegalArgumentException("Default await time cannot be negative, was " + seconds);
		}
		this.defaultAwaitTime = seconds;
		return this;
	}

	/**
	 * Gets a new step builder.
	 *
	 * @return the state machine test plan step builder
	 */
	public StateMachineTestPlanStepBuilder step() {
		return new StateMachineTestPlanStepBuilder();
	}

	/**
	 * Builds the state machine test plan.
	 *
	 * @return the state machine test plan
	 */
	public StateMachineTestPlan<S, E> build() {
		return new StateMachineTestPlan<S, E>(stateMachines, steps, defaultAwaitTime);
	}

	/**
	 * Builder for individual plan steps.
	 */
	public class StateMachineTestPlanStepBuilder {

		E sendEvent;
		Object sendEventMachineId;
		boolean sendEventToAll = false;
		final Collection<S> expectStates = new ArrayList<S>();
		Integer expectStateChanged;
		Integer expectStateEntered;
		Integer expectStateExited;
		Integer expectEventNotAccepted;
		Integer expectTransition;
		Integer expectTransitionStarted;
		Integer expectTransitionEnded;
		Integer expectStateMachineStarted;
		Integer expectStateMachineStopped;
		final Collection<Object> expectVariableKeys = new ArrayList<Object>();
		final Map<Object, Object> expectVariables = new HashMap<Object, Object>();


		/**
		 * Expect a state {@code S}.
		 *
		 * @param state the state
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectState(S state) {
			this.expectStates.add(state);
			return this;
		}

		/**
		 * Expect a states {@code S}.
		 *
		 * @param states the states
		 * @return the state machine test plan step builder
		 */
		@SuppressWarnings("unchecked")
		public StateMachineTestPlanStepBuilder expectStates(S... states) {
			this.expectStates.addAll(Arrays.asList(states));
			return this;
		}

		/**
		 * Send an event {@code E}. In case multiple state machines
		 * exists, a random one will be chosen to send this event.
		 *
		 * @param event the event
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder sendEvent(E event) {
			return sendEvent(event, false);
		}

		/**
		 * Send an event {@code E}. If {@code sendToAll} is set to {@code TRUE} event
		 * will be send to all existing machines.
		 *
		 * @param event the event
		 * @param sendToAll send to all machines
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder sendEvent(E event, boolean sendToAll) {
			this.sendEvent = event;
			this.sendEventMachineId = null;
			this.sendEventToAll = sendToAll;
			return this;
		}

		/**
		 * Send an event {@code E} into a state machine identified
		 * by {@code machineId}.
		 *
		 * @param event the event
		 * @param machineId the machine identifier for sending event
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder sendEvent(E event, Object machineId) {
			this.sendEvent = event;
			this.sendEventMachineId = machineId;
			return this;
		}

		/**
		 * Expect variable to exist in extended state variables.
		 *
		 * @param key the key
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectVariable(Object key) {
			this.expectVariableKeys.add(key);
			return this;
		}

		/**
		 * Expect variable to exist in extended state variables and match
		 * with the value.
		 *
		 * @param key the key
		 * @param value the value
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectVariable(Object key, Object value) {
			this.expectVariables.put(key, value);
			return this;
		}

		/**
		 * Expect state changed happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectStateChanged(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectStateChanged = count;
			return this;
		}

		/**
		 * Expect state enter happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectStateEntered(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectStateEntered = count;
			return this;
		}

		/**
		 * Expect state exit happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectStateExited(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectStateExited = count;
			return this;
		}

		/**
		 * Expect event not accepter happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectEventNotAccepted(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectEventNotAccepted = count;
			return this;
		}

		/**
		 * Expect transition happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectTransition(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectTransition = count;
			return this;
		}

		/**
		 * Expect transition start happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectTransitionStarted(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectTransitionStarted = count;
			return this;
		}

		/**
		 * Expect transition end happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectTransitionEnded(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectTransitionEnded = count;
			return this;
		}

		/**
		 * Expect state machine start happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectStateMachineStarted(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectStateMachineStarted = count;
			return this;
		}

		/**
		 * Expect state machine stop happening {@code count} times.
		 *
		 * @param count the count
		 * @return the state machine test plan step builder
		 */
		public StateMachineTestPlanStepBuilder expectStateMachineStopped(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("Expected count cannot be negative, was " + count);
			}
			this.expectStateMachineStopped = count;
			return this;
		}

		/**
		 * Add a new step and return {@link StateMachineTestPlanBuilder}
		 * for chaining.
		 *
		 * @return the state machine test plan builder for chaining
		 */
		public StateMachineTestPlanBuilder<S, E> and() {
			steps.add(new StateMachineTestPlanStep<S, E>(sendEvent, sendEventMachineId, sendEventToAll, expectStates,
					expectStateChanged, expectStateEntered, expectStateExited, expectEventNotAccepted,
					expectTransition, expectTransitionStarted, expectTransitionEnded, expectStateMachineStarted,
					expectStateMachineStopped, expectVariableKeys, expectVariables));
			return StateMachineTestPlanBuilder.this;
		}

	}

	static class StateMachineTestPlanStep<S, E> {
		E sendEvent;
		Object sendEventMachineId;
		boolean sendEventToAll = false;
		final Collection<S> expectStates;
		Integer expectStateChanged;
		Integer expectStateEntered;
		Integer expectStateExited;
		Integer expectEventNotAccepted;
		Integer expectTransition;
		Integer expectTransitionStarted;
		Integer expectTransitionEnded;
		Integer expectStateMachineStarted;
		Integer expectStateMachineStopped;
		final Collection<Object> expectVariableKeys;
		final Map<Object, Object> expectVariables;

		public StateMachineTestPlanStep(E sendEvent, Object sendEventMachineId, boolean sendEventToAll,
				Collection<S> expectStates, Integer expectStateChanged, Integer expectStateEntered,
				Integer expectStateExited, Integer expectEventNotAccepted, Integer expectTransition,
				Integer expectTransitionStarted, Integer expectTransitionEnded, Integer expectStateMachineStarted,
				Integer expectStateMachineStopped, Collection<Object> expectVariableKeys,
				Map<Object, Object> expectVariables) {
			this.sendEvent = sendEvent;
			this.sendEventMachineId = sendEventMachineId;
			this.sendEventToAll = sendEventToAll;
			this.expectStates = expectStates;
			this.expectStateChanged = expectStateChanged;
			this.expectStateEntered = expectStateEntered;
			this.expectStateExited = expectStateExited;
			this.expectEventNotAccepted = expectEventNotAccepted;
			this.expectTransition = expectTransition;
			this.expectTransitionStarted = expectTransitionStarted;
			this.expectTransitionEnded = expectTransitionEnded;
			this.expectStateMachineStarted = expectStateMachineStarted;
			this.expectStateMachineStopped = expectStateMachineStopped;
			this.expectVariableKeys = expectVariableKeys;
			this.expectVariables = expectVariables;
		}

	}

}
