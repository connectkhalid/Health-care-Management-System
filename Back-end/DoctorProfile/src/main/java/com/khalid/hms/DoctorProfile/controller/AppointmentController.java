package com.khalid.hms.DoctorProfile.controller;

import com.khalid.hms.DoctorProfile.dto.request.AppointmentRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.AppointmentResponseDto;
import com.khalid.hms.DoctorProfile.exceptions.SlotIsBookedException;
import com.khalid.hms.DoctorProfile.service.BindingService;
import com.khalid.hms.DoctorProfile.service.serviceImplementation.AppointmentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentServiceImpl appointmentService;
    @Autowired
    private BindingService bindingService;
    @PostMapping("/create-appointment")
    public ResponseEntity<Object> createAppointment(@Valid @RequestBody AppointmentRequestDto requestDto, BindingResult bindingResult) throws SlotIsBookedException, Exception {
        ResponseEntity<Object> errors = bindingService.getBindingError(bindingResult);
        if (errors != null) return errors;
        AppointmentResponseDto responseDto = appointmentService.createAppointment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PostMapping("/cancel/{appointmentId}")
    public ResponseEntity<Object> cancelAppointment(@PathVariable String appointmentId) throws SlotIsBookedException, Exception {

        AppointmentResponseDto responseDto = appointmentService.cancelSlotBooking(appointmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PostMapping("/complete/{appointmentId}")
    public ResponseEntity<Object> completeAppointment(@PathVariable String appointmentId){
        AppointmentResponseDto responseDto = appointmentService.completeAppointment(appointmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/get-appointment-by-appointmentId/{appointmentId}")
    public ResponseEntity<Object> getAppointmentsByAppointmentId(@PathVariable String appointmentId){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointmentById(appointmentId));
    }
    //Patient appointments get calls
    @GetMapping("/get-current-appointments-patient")
    public ResponseEntity<Object> getPatientCurrentAppointment(){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getPatientCurrentAppointments());
    }
    @GetMapping("/get-upcoming-appointments-patient")
    public ResponseEntity<Object> getPatientUpcomingAppointments(){
        List<AppointmentResponseDto> responses = appointmentService
                .alUpcomingPatientAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/patient-appointment-history")
    public ResponseEntity<Object> patientAppointmentHistory(){
        List<AppointmentResponseDto> responses = appointmentService.patientAppointmentHistory();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    //Doctor appointments get calls
    @GetMapping("/get-current-appointments-doctor")
    public ResponseEntity<Object> getDoctorCurrentAppointments(){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getDoctorCurrentAppointment());
    }
    @GetMapping("/get-upcoming-appointments-doctor")
    public ResponseEntity<Object> getDoctorUpcomingAppointments(){
        List<AppointmentResponseDto> responses = appointmentService.getDoctorUpcomingAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/doctor-appointment-history")
    public ResponseEntity<Object> doctorAppointmentHistory(){
        List<AppointmentResponseDto> responses = appointmentService.doctorAppointmentHistory();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/all-completed-appointment-doctor")
    public ResponseEntity<Object> doctorCompletedAppointments(){
        List<AppointmentResponseDto> responses = appointmentService.doctorCompletedAppointment();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/all-cancelled-appointment-doctor")
    public ResponseEntity<Object> doctorCancelledAppointment(){
        List<AppointmentResponseDto> responses = appointmentService.doctorCancelledAppointment();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/all-completed-appointment-patient")
    public ResponseEntity<Object> patientCompletedAppointments(){
        List<AppointmentResponseDto> responses = appointmentService.patientCompletedAppointment();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping("/all-cancelled-appointment-patient")
    public ResponseEntity<Object> patientCancelledAppointment(){
        List<AppointmentResponseDto> responses = appointmentService.patientCancelledAppointment();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}