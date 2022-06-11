package it.dipendentepubico.concorsiparenti.rest.align.model;

import java.util.Date;

/**
 * Messaggio di risponsa con info su allineamento usato per i test
 */
public class AlignOutMessage {
    private String code;
    private String message;
    private Date date;

    /**
     * Necessario per serializzazione JSON
     */
    public AlignOutMessage() {
    }

    public AlignOutMessage(String code, String message, Date date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
