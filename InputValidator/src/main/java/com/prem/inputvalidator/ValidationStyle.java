package com.prem.inputvalidator;

public enum ValidationStyle {

    BASIC(0),
    COLORATION(1),
    UNDERLABEL(2),
    TEXT_INPUT_LAYOUT(3);

    private final int mValue;

    ValidationStyle(int value) {
        mValue = value;
    }

    public int value() {
        return mValue;
    }

    public static ValidationStyle fromValue(int value) {
        switch (value) {
            case 0:
                return ValidationStyle.BASIC;
            case 1:
                return ValidationStyle.COLORATION;
            case 2:
                return ValidationStyle.UNDERLABEL;
            case 3:
                return ValidationStyle.TEXT_INPUT_LAYOUT;
            default:
                throw new IllegalArgumentException("Unknown ValidationStyle value.");
        }
    }

}