package com.sterling.stringservice.validator;

import com.sterling.stringservice.model.ValidateResponse;

public interface StringValidator {

    ValidateResponse validate(final String input);

}
