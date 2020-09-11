package com.dexmohq.hateoas.swagger;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
public class RepositoryPersistence implements StateEnginePersistence<Object>, ApplicationContextAware, InitializingBean {

    @Setter
    private ApplicationContext applicationContext;
    private Repositories repositories;

    @Override
    @SuppressWarnings({"rawtypes"})
    public Object persist(Object entity) {
        final CrudRepository repository = (CrudRepository) repositories.getRepositoryFor(entity.getClass()).orElseThrow(() -> new IllegalStateException("No repository found for domain class: " + entity.getClass()));
        return repository.save(entity);
    }

    @Override
    public void afterPropertiesSet() {
        repositories = new Repositories(applicationContext);
    }
}
