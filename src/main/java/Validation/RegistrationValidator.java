package Validation;

import DTO.RegistrationDTO;

public class RegistrationValidator {

    private String validationErrors;

    private final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$";

    private final int PASSWORD_MIN_LENGH = 6;

    private final String PHONE_NUMBER_REGEX = "\\d{3}[\\-]\\d{3}[\\-]\\d{4}";

    public RegistrationValidator() {
        validationErrors = "";
    }

    public boolean isValid(RegistrationDTO user){

        validationErrors = "";

        if(!user.getEmail().matches(EMAIL_REGEX))
            validationErrors+= "Invalid email format!;";

        if(user.getPassword().length()<PASSWORD_MIN_LENGH)
            validationErrors+= "Password length should be at least 6 characters!;";

        if(!user.getPassword().equals(user.getConfirmPassword()))
            validationErrors+="Passwords does not match!;";

        if(!user.getPhoneNumber().matches(PHONE_NUMBER_REGEX))
            validationErrors+= "Invalid phone number format!;";

        return validationErrors.isEmpty();
    }

    public String[] getValidationErrors(){
        return validationErrors.split(";");
    }
}
