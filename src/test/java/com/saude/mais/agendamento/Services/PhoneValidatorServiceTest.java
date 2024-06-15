package com.saude.mais.agendamento.Services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneValidatorServiceTest {

    private PhoneValidatorService phoneValidatorService;

    @Autowired
    PhoneValidatorServiceTest(PhoneValidatorService phoneValidatorService) {
        this.phoneValidatorService = phoneValidatorService;
    }

    @Test
    void testValidPhoneNumber() {
        String phoneNumber = "+5511999998888";
        String regionCode = "BR";

        boolean isValid = phoneValidatorService.isValidPhoneNumber(phoneNumber, regionCode);
        assertTrue(isValid, "Phone number should be valid");
    }

    @Test
    void testInvalidPhoneNumber() {
        String phoneNumber = "+551199999";
        String regionCode = "BR";

        boolean isValid = phoneValidatorService.isValidPhoneNumber(phoneNumber, regionCode);
        assertFalse(isValid, "Phone number should be invalid");
    }

    @Test
    void testPhoneNumberWithWrongRegion() {
        String phoneNumber = "+5511999998888";
        String regionCode = "US";

        boolean isValid = phoneValidatorService.isValidPhoneNumber(phoneNumber, regionCode);
        assertFalse(isValid, "Phone number should be invalid for the wrong region");
    }
}