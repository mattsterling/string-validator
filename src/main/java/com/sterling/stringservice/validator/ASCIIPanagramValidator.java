package com.sterling.stringservice.validator;

import com.sterling.stringservice.model.ValidateResponse;

import java.util.HashSet;
import java.util.Set;

import static com.sterling.stringservice.model.ValidateResponse.ValidationType.PANAGRAM;

public class ASCIIPanagramValidator implements StringValidator {

    private boolean isValid = false;

    private static final ValidateResponse.ValidationType TYPE = PANAGRAM ;

    private static int ASCII_ALPHABET_SIZE = 26;

    @Override
    public ValidateResponse validate(final String input) {

        // Must be non-empty, not null, and be larger than 26 chars
        if (null == input || input.isEmpty() || input.length() < ASCII_ALPHABET_SIZE) {
            return new ValidateResponse(input, isValid, TYPE);
        }

        this.isValid = isPanagram(input);

        return new ValidateResponse(input, isValid, TYPE);
    }


    /**
     * Creates an array of characters for the string and iterates making sure
     * we find all the characters for the ASCII Alphabet.
     *
     * This method does not a due a brute force check of the Alphabet against the string
     * to avoid an N^2 solution.
     * @param input
     * @return
     */
    private boolean isPanagram(final String input) {
        final Set<Character> seen = new HashSet<>();
        char[] chars = input.toCharArray();
        for(char c: chars) {
            if(isAsciiLowerCase(c)) {
                seen.add(c);
                continue;
            } else if(isAsciiUpperCase(c)) {
                seen.add(Character.toLowerCase(c)); // ensure a hash collision
                continue;
            }
        }

        // The set should have 26 characters
        return seen.size() == ASCII_ALPHABET_SIZE;
    }

    private boolean isAsciiUpperCase(final char c) {
        return c >= 'A' && c <= 'Z' ? true : false;
    }

    private boolean isAsciiLowerCase(final char c) {
        return c >= 'a' && c <= 'z' ? true : false;
    }
}
