package it.dipendentepubico.concorsiparenti.rest.auth.model;

public enum Role {
    ADMIN("Administrator"),
    MODERATOR("Moderator"),
    USER("Simple user");

    Role(String code){
        this.code=code;
    }

    private String code;

    public String getCode() {
        return code;
    }

}
