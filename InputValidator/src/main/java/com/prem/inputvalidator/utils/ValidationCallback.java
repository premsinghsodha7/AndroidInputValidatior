package com.prem.inputvalidator.utils;

import com.prem.inputvalidator.ValidationHolder;

import java.util.regex.Matcher;

public interface ValidationCallback {
    void execute(ValidationHolder validationHolder, Matcher matcher);
}