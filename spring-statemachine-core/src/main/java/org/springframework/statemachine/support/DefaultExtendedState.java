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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.statemachine.ExtendedState;

/**
 * Default implementation of a {@link ExtendedState}.
 *
 * @author Janne Valkealahti
 *
 */
public class DefaultExtendedState implements ExtendedState {

	private final Map<Object, Object> variables;

	/**
	 * Instantiates a new default extended state.
	 */
	public DefaultExtendedState() {
		this.variables = new ConcurrentHashMap<Object, Object>();
	}

	/**
	 * Instantiates a new default extended state.
	 *
	 * @param variables the variables
	 */
	public DefaultExtendedState(Map<Object, Object> variables) {
		this.variables = variables;
	}

	@Override
	public Map<Object, Object> getVariables() {
		return variables;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = this.variables.get(key);
		if (value == null) {
			return null;
		}
		if (!type.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("Incorrect type specified for variable '" +
					key + "'. Expected [" + type + "] but actual type is [" + value.getClass() + "]");
		}
		return (T) value;
	}

	@Override
	public String toString() {
		return "DefaultExtendedState [variables=" + variables + "]";
	}

}
