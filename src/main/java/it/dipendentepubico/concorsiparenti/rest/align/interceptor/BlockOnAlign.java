package it.dipendentepubico.concorsiparenti.rest.align.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * I metodi annotati vengono bloccati da {@link AlignInterceptor} ne caso in cui sia in corso un allineamento.
 * L'annotation va utilizzata tipicamente sui metodi dei controller che si occupano di Create Update Delete, non su Read.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BlockOnAlign {
}
