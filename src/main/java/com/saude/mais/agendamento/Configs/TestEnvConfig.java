package com.saude.mais.agendamento.Configs;

import com.github.javafaker.Faker;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Repositories.AddressRepository;
import com.saude.mais.agendamento.Repositories.HospitalRepository;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

@Configuration
@Profile("test")
public class TestEnvConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private  HospitalRepository hospitalRepository;
    private  AddressRepository addressRepository;

    @Autowired
    public TestEnvConfig(UserRepository userRepository, HospitalRepository hospitalRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
        this.addressRepository = addressRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();




        List<AddressEntity> addressEntities = new ArrayList<>();
        List<HospitalEntity> hospitalEntities = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AddressEntity address = new AddressEntity(faker.address().streetName(), faker.address().city(), faker.address().state(), faker.address().zipCode());
            addressEntities.add(address);

            HospitalEntity hospital = new HospitalEntity(faker.medical().hospitalName(), generateValidCnpj(), address, generateCellPhoneNumber(), generateCellPhoneNumber(), faker.internet().safeEmailAddress(), faker.number().numberBetween(10, 50),  faker.number().numberBetween(10, 25), faker.number().numberBetween(5, 20), String.valueOf(faker.idNumber()),  faker.date().birthday().toInstant());
            hospitalEntities.add(hospital);
        }

        addressRepository.saveAll(addressEntities);
        hospitalRepository.saveAll(hospitalEntities);
    }

    public String generateRandomCPF() {
        Faker faker = new Faker();
        String cpf = faker.numerify("###########");

        int sum = 0;
        int digit;
        for (int i = 0; i < 9; i++) {
            sum += (10 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        digit = 11 - (sum % 11);
        cpf += (digit == 10 || digit == 11) ? "0" : Integer.toString(digit);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (11 - i) * Character.getNumericValue(cpf.charAt(i));
        }
        digit = 11 - (sum % 11);
        cpf += (digit == 10 || digit == 11) ? "0" : Integer.toString(digit);

        return cpf;
    }

    public static String generateValidCnpj() {
        Random random = new Random();

        int[] cnpjArray = new int[14];

        for (int i = 0; i < 12; i++) {
            cnpjArray[i] = random.nextInt(10);
        }

        cnpjArray[12] = calculateVerifier(cnpjArray, 5);
        cnpjArray[13] = calculateVerifier(cnpjArray, 6);

        StringBuilder sb = new StringBuilder();
        for (int digit : cnpjArray) {
            sb.append(digit);
        }

        return sb.toString();
    }

    private static int calculateVerifier(int[] cnpjArray, int multiplier) {
        int sum = 0;
        int length = cnpjArray.length - 1;
        int verifier;
        for (int i = 0; i < length; i++) {
            sum += cnpjArray[i] * multiplier--;
            if (multiplier < 2) {
                multiplier = 9;
            }
        }
        verifier = 11 - (sum % 11);
        return verifier > 9 ? 0 : verifier;
    }

    public static String generateCellPhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("9");

        for (int i = 1; i < 11; i++) {
            phoneNumber.append(random.nextInt(10)); // Gera dígitos de 0 a 9
        }
        return phoneNumber.toString();
    }

}

//            String role = roles[faker.random().nextInt(0, 2)];
//            UserRole userRole =  role.equals("admin") ? UserRole.ADMIN : role.equals("worker") ? UserRole.WORKER : UserRole.CUSTOMER;
//            do {
//                cpf = generateRandomCPF();
//                existingUser = userRepository.findByCpf(cpf);
//            } while (existingUser != null);
//
//            UserEntity userEntity = new UserEntity(null, faker.name().firstName(), faker.name().lastName(), faker.name().username(), faker.internet().password(), faker.internet().safeEmailAddress(), faker.phoneNumber().cellPhone(), cpf, userRole, faker.date().birthday().toInstant());
//            users.add(userEntity);
