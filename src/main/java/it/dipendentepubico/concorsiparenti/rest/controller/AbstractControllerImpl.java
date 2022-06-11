package it.dipendentepubico.concorsiparenti.rest.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static it.dipendentepubico.concorsiparenti.rest.controller.CaptchaController.ATTRIBUTE_CAPTCHA;

public abstract class AbstractControllerImpl {
    @Autowired
    protected ModelMapper mapper;

    /**
     * Metodo a fattor comune per la verifica del capcha delle form di inserimento o aggiornamento dati.
     * @param request
     * @param verCode
     * @return
     */
    protected boolean checkCaptcha(HttpServletRequest request, String verCode){
        // Get the verification code in the session
        String sessionCode = (String) request.getSession().getAttribute(ATTRIBUTE_CAPTCHA);
        // Judge verification code
        return sessionCode != null && sessionCode.equals(verCode.trim().toLowerCase());
    }

}
