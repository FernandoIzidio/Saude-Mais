package com.saude.mais.agendamento;

import com.saude.mais.agendamento.Services.PhoneValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class AgendamentoApplicationTests {

    private PhoneValidatorService phoneValidatorService;

    @Autowired
    public AgendamentoApplicationTests( PhoneValidatorService phoneValidatorService ){
        this.phoneValidatorService = phoneValidatorService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void phoneValidatorServiceLoads() {

        assertNotNull(phoneValidatorService, "PhoneValidatorService should be loaded in the context");
    }


}
