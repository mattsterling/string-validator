package com.sterling.stringservice.validator;

import com.sterling.stringservice.model.ValidateResponse;

/**
 * High level interface for StringValidators.
 */
public interface StringValidator {

    ValidateResponse validate(final String input);

}
