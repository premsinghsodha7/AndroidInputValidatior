package com.prem.inputvalidator;

import android.app.Activity;
import android.content.Context;
import android.util.Range;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.prem.inputvalidator.utils.NumericRange;
import com.prem.inputvalidator.utils.customutils.CustomErrorReset;
import com.prem.inputvalidator.utils.customutils.CustomValidation;
import com.prem.inputvalidator.utils.customutils.CustomValidationCallback;
import com.prem.inputvalidator.utils.customutils.SimpleCustomValidation;
import com.prem.inputvalidator.validator.LabeledValidator;
import com.prem.inputvalidator.validator.SimpleValidator;
import com.prem.inputvalidator.validator.ColoredValidator;
import com.prem.inputvalidator.validator.TextInputLayoutValidator;
import com.prem.inputvalidator.validator.Validator;

import java.util.regex.Pattern;

public class InputValidator {

    private Validator mValidator = null;

    private static boolean autoFocusOnFirstFailure = true;

    public InputValidator(ValidationStyle style) {
        switch (style) {
            case SIMPLE:
                if (mValidator == null || !(mValidator instanceof SimpleValidator)) {
                    mValidator = new SimpleValidator();
                }
                return;
            case COLORED:
                if (mValidator == null || !(mValidator instanceof ColoredValidator)) {
                    mValidator = new ColoredValidator();
                }
                return;
            case LABELED:
                if (mValidator == null || !(mValidator instanceof LabeledValidator)) {
                    mValidator = new LabeledValidator();
                }
                return;
            case TEXT_INPUT_LAYOUT:
                if (mValidator == null || !(mValidator instanceof TextInputLayoutValidator)) {
                    mValidator = new TextInputLayoutValidator();
                }
                return;
            default:
        }
    }

    public static boolean isAutoFocusOnFirstFailureEnabled() {
        return autoFocusOnFirstFailure;
    }

    public static void disableAutoFocusOnFirstFailure() {
        autoFocusOnFirstFailure = false;
    }

    private void checkIsColorationValidator() {
        if (!(mValidator instanceof ColoredValidator)) {
            throw new UnsupportedOperationException("Only supported by ColoredValidator.");
        }
    }

    private void checkIsUnderlabelValidator() {
        if (!(mValidator instanceof LabeledValidator)) {
            throw new UnsupportedOperationException("Only supported by LabeledValidator.");
        }
    }

    private void checkIsTextInputLayoutValidator() {
        if (!(mValidator instanceof TextInputLayoutValidator)) {
            throw new UnsupportedOperationException("Only supported by TextInputLayoutValidator.");
        }
    }

    private void checkIsNotTextInputLayoutValidator() {
        if (mValidator instanceof TextInputLayoutValidator) {
            throw new UnsupportedOperationException("Not supported by TextInputLayoutValidator.");
        }
    }

    public void setContext(Context context) {
        checkIsUnderlabelValidator();
        ((LabeledValidator) mValidator).setContext(context);
    }

    public void setColor(int color) {
        checkIsColorationValidator();
        ((ColoredValidator) mValidator).setColor(color);
    }

    public void setUnderlabelColor(int colorValue) {
        checkIsUnderlabelValidator();
        ((LabeledValidator) mValidator).setColor(colorValue);
    }

    public void setUnderlabelColorByResource(int colorResId) {
        checkIsUnderlabelValidator();
        ((LabeledValidator) mValidator).setColorByResource(colorResId);
    }

    public void setTextInputLayoutErrorTextAppearance(int styleId) {
        checkIsTextInputLayoutValidator();
        ((TextInputLayoutValidator) mValidator).setErrorTextAppearance(styleId);
    }

    public void addValidation(EditText editText, String regex, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, regex, errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, String regex, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, regex, errMsg);
    }

    public void addValidation(Activity activity, int viewId, String regex, int errMsgId) {
        mValidator.set(activity, viewId, regex, errMsgId);
    }

    public void addValidation(EditText editText, Pattern pattern, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, pattern, errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, Pattern pattern, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, pattern, errMsg);
    }

    public void addValidation(Activity activity, int viewId, Pattern pattern, int errMsgId) {
        mValidator.set(activity, viewId, pattern, errMsgId);
    }

    public void addValidation(EditText editText, Range range, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, new NumericRange(range), errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, Range range, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, new NumericRange(range), errMsg);
    }

    public void addValidation(Activity activity, int viewId, Range range, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(range), errMsgId);
    }

    public void addValidation(EditText confirmationEditText, EditText editText, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(confirmationEditText, editText, errMsg);
    }

    public void addValidation(TextInputLayout confirmationTextInputLayout, TextInputLayout textInputLayout, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(confirmationTextInputLayout, textInputLayout, errMsg);
    }

    public void addValidation(Activity activity, int confirmationViewId, int viewId, int errMsgId) {
        mValidator.set(activity, confirmationViewId, viewId, errMsgId);
    }

    public void addValidation(EditText editText, SimpleCustomValidation simpleCustomValidation, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, simpleCustomValidation, errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, SimpleCustomValidation simpleCustomValidation, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, simpleCustomValidation, errMsg);
    }

    public void addValidation(Activity activity, int viewId, SimpleCustomValidation simpleCustomValidation, int errMsgId) {
        mValidator.set(activity, viewId, simpleCustomValidation, errMsgId);
    }

    public void addValidation(View view, CustomValidation customValidation, CustomValidationCallback customValidationCallback, CustomErrorReset customErrorReset, String errMsg) {
        mValidator.set(view, customValidation, customValidationCallback, customErrorReset, errMsg);
    }

    public void addValidation(Activity activity, int viewId, CustomValidation customValidation, CustomValidationCallback customValidationCallback, CustomErrorReset customErrorReset, int errMsgId) {
        mValidator.set(activity, viewId, customValidation, customValidationCallback, customErrorReset, errMsgId);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

    public void clear() {
        mValidator.halt();
    }

}
