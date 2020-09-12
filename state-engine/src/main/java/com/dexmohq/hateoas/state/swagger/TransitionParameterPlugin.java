package com.dexmohq.hateoas.state.swagger;

import com.dexmohq.hateoas.state.StateEngineController;
import com.dexmohq.hateoas.state.StateTransition;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
@CommonsLog
public class TransitionParameterPlugin implements ParameterBuilderPlugin, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public void apply(ParameterContext parameterContext) {
        if (!parameterContext.resolvedMethodParameter().hasParameterAnnotation(TransitionName.class)) {
            return;
        }
        final TransitionName a = parameterContext.resolvedMethodParameter().findAnnotation(TransitionName.class).orElseThrow();
        final DocumentationContext documentationContext = parameterContext.getOperationContext().getDocumentationContext();
        final Optional<Class<? extends StateEngineController<?, ?, ?>>> controllerClass = findControllerClass(documentationContext, a);
        if (controllerClass.isEmpty()) {
            log.warn(String.format("Controller not found for parameter '%s' on operation '%s'", parameterContext.resolvedMethodParameter().defaultName().orElse("<unknown>"), parameterContext.getOperationContext().getName()));
            return;
        }
        final StateEngineController<?, ?, ?> controller = applicationContext.getBean(controllerClass.get());
        final Set<String> transitions = controller.getStateEngine().getTransitions().stream()
                .map(StateTransition::getName)
                .collect(Collectors.toSet());
        parameterContext.requestParameterBuilder().in(ParameterType.PATH)
                .name(parameterContext.resolvedMethodParameter().defaultName().orElse("transition")) // when is the name empty?
                .query(q -> q.enumerationFacet(e -> e.allowedValues(transitions)));
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    private Optional<Class<? extends StateEngineController<?, ?, ?>>> findControllerClass(DocumentationContext documentationContext, TransitionName a) {
        for (final RequestHandler requestHandler : documentationContext.getRequestHandlers()) {
            if (requestHandler.getParameters().stream().anyMatch(p -> p.findAnnotation(TransitionName.class).equals(Optional.of(a)))) {
                final Class<?> controllerClass = ((WebMvcRequestHandler) requestHandler).declaringClass();
                if (StateEngineController.class.isAssignableFrom(controllerClass)) {
                    return Optional.of((Class<? extends StateEngineController<?, ?, ?>>) controllerClass);
                }
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

}
