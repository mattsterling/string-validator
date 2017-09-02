package com.sterling.stringservice.validator;


import com.sterling.stringservice.model.ValidateResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ASCIIPanagramValidatorTest {

    ASCIIPanagramValidator validator;

    @Before
    public void setUp() {
        validator = new ASCIIPanagramValidator();
    }


    @Test
    public void testEmptyString_NotValid() {
        ValidateResponse resp = validator.validate("");
        assertFalse(resp.isValid());
        assertEquals("", resp.getString());
    }


    @Test
    public void testNullString_NotValid() {
        ValidateResponse resp = validator.validate(null);
        assertFalse(resp.isValid());
        assertEquals(null, resp.getString());
    }

    @Test
    public void testShortString_NotValid() {
        ValidateResponse resp = validator.validate("abcd");
        assertFalse(resp.isValid());
        assertEquals("abcd", resp.getString());
    }

    @Test
    public void testStringWithDuplicates_NotValid() {
        ValidateResponse resp = validator.validate("abBBDDDderaeeafadfasdfcZZZZd");
        assertFalse(resp.isValid());
        assertEquals("abBBDDDderaeeafadfasdfcZZZZd", resp.getString());
    }

    @Test
    public void testStringWithDuplicates_Valid() {
        ValidateResponse resp = validator.validate("abcdEfghIjkLmnoPqRsTuVWXyzzzAaaa");
        assertTrue(resp.isValid());
        assertEquals("abcdEfghIjkLmnoPqRsTuVWXyzzzAaaa", resp.getString());
    }

    @Test
    public void testStringWithNumbers_Valid() {
        ValidateResponse resp = validator.validate("abcdEfghIj123kLmnoPqRsTuVWXyzzzAaaa8879973");
        assertTrue(resp.isValid());
        assertEquals("abcdEfghIj123kLmnoPqRsTuVWXyzzzAaaa8879973", resp.getString());
    }

    @Test
    public void testStringWithNumbers_NotValid() {
        ValidateResponse resp = validator.validate("fghIj123kLmnoPqRsTuVWXyzzzAaaa8879973");
        assertFalse(resp.isValid());
        assertEquals("fghIj123kLmnoPqRsTuVWXyzzzAaaa8879973", resp.getString());
    }

    @Test
    public void testStringWithSpaces_Valid() {
        ValidateResponse resp = validator.validate("abcdEfghIj123k LmnoPqRsTuVWXyzzzAaa a8879973");
        assertTrue(resp.isValid());
        assertEquals("abcdEfghIj123k LmnoPqRsTuVWXyzzzAaa a8879973", resp.getString());
    }

    @Test
    public void testStringWithSpaces_NotValid() {
        ValidateResponse resp = validator.validate("abEfghIj123k LmnoPqRsTuVWXyzzzAaa a8879973");
        assertFalse(resp.isValid());
        assertEquals("abEfghIj123k LmnoPqRsTuVWXyzzzAaa a8879973", resp.getString());
    }

}
