package com.sterling.stringservice.validator;

import com.sterling.stringservice.model.ValidateResponse;

public class StringValidatorFactory {


    public static StringValidator create(ValidateResponse.ValidationType type) {
        switch(type) {
            case PANAGRAM:
                return new ASCIIPanagramValidator();
            default:
                return null;
        }
    }
}
