package com.sterling.stringservice.validator;

import com.sterling.stringservice.model.ValidateResponse;

/**
 * Simple factory for creating validation "strategies" for a string based off the
 * the type of validation provided.
 */
public class StringValidatorFactory {


    /**
     * Returns a StringValidator for the given type. Currently only supported type is
     * PANAGRAM.
     * @param type ValidationType indicating which validator to create.
     * @return
     */
    public static StringValidator create(ValidateResponse.ValidationType type) {
        switch(type) {
            case PANAGRAM:
                return new ASCIIPanagramValidator();
            default:
                return null;
        }
    }
}
