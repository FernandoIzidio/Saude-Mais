package com.saude.mais.agendamento.Services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;

@Service
public class PhoneValidatorService {

    public PhoneValidatorService() {}

    public boolean isValidPhoneNumber(String phoneNumber, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneNumberUtil.parse(phoneNumber, regionCode);
            return phoneNumberUtil.isValidNumber(numberProto) &&
                    phoneNumberUtil.getRegionCodeForNumber(numberProto).equals(regionCode);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            return false;
        }
    }
}
