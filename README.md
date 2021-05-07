# InputValidatior

## Introduction

Implement validation for Android.  Developers should focus on their awesome code, and let the library do the boilerplate.  And what's more, this could help keep your layout file clean.
Provide a rich set of validators to cover most various types of Text field.
Develop and reuse your own validator easily.
## Steps

1. Declare validation style;
2. Add validations;
3. Set a point when to trigger validation.

## Sample code

```java
// Step 1: designate a style
InputValidator mInputValidator = new InputValidator(SIMPLE);
// or
InputValidator mInputValidator = new InputValidator(COLORED);
mInputValidator.setColor(Color.YELLOW);  // optional, default color is RED if not set
// or
InputValidator mInputValidator = new InputValidator(LABELED);
mInputValidator.setContext(this);  // mandatory for LABELED style
// setUnderlabelColor is optional for LABELED style, by default it's holo_red_light
mInputValidator.setUnderlabelColorByResource(android.R.color.holo_orange_light); // optional for LABELED style
mInputValidator.setUnderlabelColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark)); // optional for LABELED style
// or
InputValidator mInputValidator = new InputValidator(TEXT_INPUT_LAYOUT);
mInputValidator.setTextInputLayoutErrorTextAppearance(R.style.TextInputLayoutErrorStyle); // optional, default color is holo_red_light if not set
// by default, it automatically sets focus to the first failed input field after validation is triggered
// you can disable this behavior by
InputValidator.disableAutoFocusOnFirstFailure();

// Step 2: add validations
// support regex string, java.util.regex.Pattern and Guava#Range
// you can pass resource or string
mInputValidator.addValidation(activity, R.id.edt_name, "[a-zA-Z\\s]+", R.string.err_name);
mInputValidator.addValidation(activity, R.id.edt_tel, RegexTemplate.TELEPHONE, R.string.err_tel);
mInputValidator.addValidation(activity, R.id.edt_email, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
mInputValidator.addValidation(activity, R.id.edt_year, Range.closed(1900, Calendar.getInstance().get(Calendar.YEAR)), R.string.err_year);
mInputValidator.addValidation(activity, R.id.edt_height, Range.closed(0.0f, 2.72f), R.string.err_height);
// or
mInputValidator.addValidation(editText, "regex", "Error info");

// to validate TextInputLayout, pass the TextInputLayout, not the embedded EditText
InputValidator mInputValidator = new InputValidator(TEXT_INPUT_LAYOUT);
mInputValidator.addValidation(activity, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);

// to validate the confirmation of another field
String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
mInputValidator.addValidation(activity, R.id.edt_password, regexPassword, R.string.err_password);
// to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
mInputValidator.addValidation(activity, R.id.edt_password_confirmation, R.id.edt_password, R.string.err_password_confirmation);

// to validate with a simple custom validator function
mInputValidator.addValidation(activity, R.id.edt_birthday, new SimpleCustomValidation() {
    @Override
    public boolean compare(String input) {
        // check if the age is >= 18
        try {
            Calendar calendarBirthday = Calendar.getInstance();
            Calendar calendarToday = Calendar.getInstance();
            calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
            int yearOfToday = calendarToday.get(Calendar.YEAR);
            int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
            if (yearOfToday - yearOfBirthday > 18) {
                return true;
            } else if (yearOfToday - yearOfBirthday == 18) {
                int monthOfToday = calendarToday.get(Calendar.MONTH);
                int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                if (monthOfToday > monthOfBirthday) {
                    return true;
                } else if (monthOfToday == monthOfBirthday) {
                    if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                        return true;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}, R.string.err_birth);

// to validate with your own custom validator function, warn and clear the warning with your way
mInputValidator.addValidation(activity, R.id.spinner_tech_stacks, new CustomValidation() {
    @Override
    public boolean compare(ValidationHolder validationHolder) {
        if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("< Please select one >")) {
            return false;
        } else {
            return true;
        }
    }
}, new CustomValidationCallback() {
    @Override
    public void execute(ValidationHolder validationHolder) {
        TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
        textViewError.setError(validationHolder.getErrMsg());
        textViewError.setTextColor(Color.RED);
    }
}, new CustomErrorReset() {
    @Override
    public void reset(ValidationHolder validationHolder) {
        TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
        textViewError.setError(null);
        textViewError.setTextColor(Color.BLACK);
    }
}, R.string.err_tech_stacks);

// Step 3: set a trigger
findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mInputValidator.validate();
    }
});

// Optional: remove validation failure information
findViewById(R.id.btn_clr).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mInputValidator.clear();
    }
});
```

* It works perfectly with **Fragment**, but please pay attention to Fragment's lifecycle.  You should set the `validate()` inside Fragment's `onActivityCreated` instead of `onCreateView` or any other early stage.

* `UNDERLABEL` validation style doesn't support `ConstraintLayout` at the moment, please use other validation styles.

## Import as dependency

For Gradle it's easy - just add below to your module's `build.gradle`
```gradle
dependencies {
    implementation 'com.github.premsinghsodha7:AndroidInputValidatior:1.0.1'
}
```

Alternatively, it's also available on [JitPack](https://jitpack.io/):
* Add it in your root `build.gradle` at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```