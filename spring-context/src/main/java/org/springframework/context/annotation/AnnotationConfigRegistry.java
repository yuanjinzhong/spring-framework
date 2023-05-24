/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

/**
 *
 *  这个接口仅仅是规范了， 注册 和扫描 这2个方法
 *
 *
 * Common interface for annotation config application contexts,
 * defining {@link #register} and {@link #scan} methods.
 *
 * @author Juergen Hoeller
 * @since 4.1
 */
public interface AnnotationConfigRegistry {

	/** 一个组件被注册多次没有影响。
	 *
	 * 注册组件，包括 {@link Configuration @Configuration}
	 *
	 * 最后还是委托 {@link AnnotatedBeanDefinitionReader#register(Class[])}
	 *
	 * Register one or more component classes to be processed.
	 * <p>Calls to {@code register} are idempotent; adding the same
	 * component class more than once has no additional effect.
	 * @param componentClasses one or more component classes,
	 * e.g. {@link Configuration @Configuration} classes
	 */
	void register(Class<?>... componentClasses);

	/**
	 *  最后还是委托{@link ClassPathBeanDefinitionScanner#scan(String...)}
	 *
	 * Perform a scan within the specified base packages.
	 * @param basePackages the packages to scan for component classes
	 */
	void scan(String... basePackages);

}
