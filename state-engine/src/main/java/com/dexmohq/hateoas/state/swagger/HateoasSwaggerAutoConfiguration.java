package com.dexmohq.hateoas.state.swagger;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = TransitionParameterPlugin.class)
public class HateoasSwaggerAutoConfiguration {
}
