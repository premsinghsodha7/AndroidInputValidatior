package com.prem.inputvalidator;

public enum ValidationStyle {

    SIMPLE(0),
    COLORED(1),
    LABELED(2),
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
                return ValidationStyle.SIMPLE;
            case 1:
                return ValidationStyle.COLORED;
            case 2:
                return ValidationStyle.LABELED;
            case 3:
                return ValidationStyle.TEXT_INPUT_LAYOUT;
            default:
                throw new IllegalArgumentException("Unknown ValidationStyle value.");
        }
    }

}