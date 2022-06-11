package it.dipendentepubico.concorsiparenti.rest.align.model;

/**
 * Utilizzato solo dal controller usato per testare websocket
 */
public class AlignInMessage {
    private String code;
    private String message;

    public AlignInMessage(String code, String message) {
        this.code = code;
        this.message = message;
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


}
