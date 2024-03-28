package org.hits.backend.hackaton;

import org.hits.backend.hackaton.core.mail.EmailProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
@EnableConfigurationProperties({EmailProperties.class})
public class HackathonApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HackathonApplication.class)
                .beanNameGenerator(new FullyQualifiedAnnotationBeanNameGenerator())
                .run(args);
    }
}
