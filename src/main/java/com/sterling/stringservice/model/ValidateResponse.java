package com.sterling.stringservice.model;

public class ValidateResponse {

    private String string;

    private boolean isValid;

    private ValidationType type;

    public ValidateResponse(final String string,
                            final boolean isValid,
                            final ValidationType type) {
        this.string = string;
        this.isValid = isValid;
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public ValidationType getType() {
        return type;
    }

    public void setType(ValidationType type) {
        this.type = type;
    }

    public enum ValidationType {
        PANAGRAM
    }



}
