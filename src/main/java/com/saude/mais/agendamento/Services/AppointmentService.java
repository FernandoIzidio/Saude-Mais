package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.AppointmentEntity;
import com.saude.mais.agendamento.Repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    public AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public ResponseEntity<?> findAllAppointments() {
        return ResponseEntity.ok(appointmentRepository.findAll());
    }

    public AppointmentEntity findByCustomerId(Long id) {
        return appointmentRepository.findByCustomer_Id(id);
    }


    public ResponseEntity<?> findAppointmentById(Long id) {
        return ResponseEntity.ok(appointmentRepository.findById(id).orElse(null));
    }

    public void save(AppointmentEntity appointmentEntity){
        appointmentRepository.save(appointmentEntity);
    }

    public void delete(Long id){
        appointmentRepository.deleteById(id);
    }




}
