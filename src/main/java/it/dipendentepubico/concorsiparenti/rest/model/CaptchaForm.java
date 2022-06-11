package it.dipendentepubico.concorsiparenti.rest.model;

import it.dipendentepubico.concorsiparenti.rest.controller.AbstractControllerImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Le form di inserimento/aggiornamento devono utilizzare request che estendono questa classe.
 * I Controller che prendono in input le request devono richiamare {@link it.dipendentepubico.concorsiparenti.rest.controller.AbstractControllerImpl#checkCaptcha(HttpServletRequest, String)}
 */
public abstract class CaptchaForm {
    private String userEnteredCaptchaCode;

    public String getUserEnteredCaptchaCode() {
        return userEnteredCaptchaCode;
    }

    public void setUserEnteredCaptchaCode(String userEnteredCaptchaCode) {
        this.userEnteredCaptchaCode = userEnteredCaptchaCode;
    }
}
