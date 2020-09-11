package com.dexmohq.hateoas.swagger;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.Map;

public class StateEngineBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        // can't do anything yet
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked","java:S3740"})
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        final Map<String, StateEngineDefinition> stateEngineDefinitions = configurableListableBeanFactory.getBeansOfType(StateEngineDefinition.class);
        for (final Map.Entry<String, StateEngineDefinition> entry : stateEngineDefinitions.entrySet()) {
            final StateEngineDefinition def = entry.getValue();
            final StateEngineImpl<?, ?> stateEngine = new StateEngineImpl<>(def, configurableListableBeanFactory);
            configurableListableBeanFactory.registerSingleton(def.getEngineName(), stateEngine);

        }
    }
}
