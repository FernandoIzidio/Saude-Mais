package com.saude.mais.agendamento.Controllers;

import com.saude.mais.agendamento.Dtos.*;
import com.saude.mais.agendamento.Entities.AppointmentEntity;
import com.saude.mais.agendamento.Entities.CustomerEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import com.saude.mais.agendamento.Entities.WorkerEntity;
import com.saude.mais.agendamento.Services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//validações de seguranças das subrotas no spring security

@RestController
@RequestMapping("/api/v1/hospitals")
public class HospitalsController {


    public HospitalService hospitalService;
    public CustomerService customerService;
    public WorkerService workerService;
    public UserService userService;
    public OfficeHoursService officeHoursService;
    public ExpertisesService expertisesService;
    public AppointmentService appointmentService;

    @Autowired
    public HospitalsController(CustomerService customerService, WorkerService workerService, UserService userService, OfficeHoursService officeHoursService, ExpertisesService expertisesService, AppointmentService appointmentService, HospitalService hospitalService) {
        this.customerService = customerService;
        this.userService = userService;
        this.workerService = workerService;
        this.officeHoursService = officeHoursService;
        this.expertisesService = expertisesService;
        this.appointmentService = appointmentService;
        this.hospitalService = hospitalService;
    }


    @GetMapping
    public ResponseEntity<?> findAllHospitals() {
        return ResponseEntity.ok(hospitalService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> registerHospital(@RequestBody @Valid HospitalRegisterDto hospital, BindingResult bindingResult) {
        UserDto userDto = hospital.userDto().cleanData();
        HospitalDto hospitalDto = hospital.hospitalDto().cleanData();
        AddressDto address = hospitalDto.address().cleanData();

        userService.validate(userDto, bindingResult);
        hospitalService.validate(hospitalDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        HospitalEntity hospitalEntity = hospitalDto.toHospitalEntity();
        hospitalEntity.setAddress(address.toAddressEntity());
        UserEntity userEntity = userDto.toUserEntity();

        hospitalService.save(hospitalEntity, userEntity, hospital.platform());
        return ResponseEntity.ok("Hospital registrado com sucesso");
    }


    //    @GetMapping("/customers")
//    public List<CustomerEntity> findAllCustomers(){
//        UserEntity user = userService.getAuthenticatedUser();
//        return customerService.findAllByHospitalId(user.getHospital().getId());
//    }

    @GetMapping("/appointments")
    public ResponseEntity<?> listAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @PostMapping("/appointments")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentDto appointment) {
        CustomerEntity customer = customerService.findById(appointment.customerId());
        List<WorkerEntity> workers = workerService.findAllById(appointment.workersId());


        AppointmentEntity appointmentEntity = new AppointmentEntity(appointment.date(), customer, workers, appointment.room(), appointment.type());

        appointmentService.save(appointmentEntity);
        return ResponseEntity.ok(appointmentEntity);
    }



    @GetMapping("/workers")
    public ResponseEntity<?> findAllWorkers() {

        return ResponseEntity.ok(workerService.findAllWorkers());
    }

    @GetMapping("/workers/{id}")
    public ResponseEntity<?> findWorkerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(workerService.findWorkerById(id));
    }

    @PostMapping("/workers")
    public ResponseEntity<?> save(@RequestBody @Valid WorkerDto worker, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (worker.user().userRole() != UserRole.WORKER){
            return ResponseEntity.badRequest().body("Role invalido.");
        }

        UserEntity authenticatedUser = userService.getAuthenticatedUser();

        WorkerEntity workerEntity = worker.toWorkerEntity();;
//        workerEntity.getUser().getHospitals().add(authenticatedUser.getHospitals());
        userService.save(workerEntity.getUser());
        officeHoursService.save(workerEntity.getOfficeHours());
        expertisesService.saveAll(workerEntity.getExpertises());
        workerService.save(workerEntity);
        return ResponseEntity.ok(workerEntity);
    }

    @DeleteMapping("/workers/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id) {
        workerService.deleteWorkerById(id);
        return ResponseEntity.ok().build();
    }



//        UserEntity user = userService.getAuthenticatedUser();
//        return ResponseEntity.ok().body(workerService.findAllByHospital(user.getHospitals()
//.getId()));
}
