package it.dipendentepubico.concorsiparenti.rest.align.interceptor;

import it.dipendentepubico.concorsiparenti.usecase.SettingsUseCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Interceptor che non permette l'esecuzione dei Controller se sono annotati con {@link BlockOnAlign}
 * ed è in corso un allineamento massivo.
 * Serve a non consentire l'inserimento o aggiornamento di record in caso di allineamento da file csv.
 */
@Component
public class AlignInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AlignInterceptor.class);

    @Autowired
    private SettingsUseCase settingsUseCase;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Pre-handle di verifica allineamento");
        HandlerMethod hm;
        try {
            hm = (HandlerMethod) handler;
        } catch (ClassCastException e) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        Method method = hm.getMethod();
        // Si tratta di un metodo di controller
        // ed è annotato con @BlockOnAlign
        // e allineamento in corso
        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)
                && method.isAnnotationPresent(BlockOnAlign.class)
                && settingsUseCase.isAlignRunning()
            ) {
             //allora blocco esecuzione
              logger.info("Allineamento in corso: non chiamo controller");
              return false;
        }
        return true;
    }
}
