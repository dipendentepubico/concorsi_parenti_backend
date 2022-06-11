package it.dipendentepubico.concorsiparenti.spring;

import it.dipendentepubico.concorsiparenti.rest.align.interceptor.AlignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorsConfiguration implements WebMvcConfigurer {
    @Autowired
    private AlignInterceptor alignInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(alignInterceptor);
    }
}