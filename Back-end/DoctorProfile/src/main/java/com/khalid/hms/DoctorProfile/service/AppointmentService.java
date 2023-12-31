package com.khalid.hms.DoctorProfile.service;

import com.khalid.hms.DoctorProfile.dto.request.AppointmentRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.AppointmentResponseDto;
import com.khalid.hms.DoctorProfile.exceptions.CustomException;
import com.khalid.hms.DoctorProfile.exceptions.SlotIsBookedException;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto requestDto)
            throws SlotIsBookedException, Exception;
    AppointmentResponseDto cancelSlotBooking(String appointmentId)
            throws CustomException;
    AppointmentResponseDto completeAppointment(String appointmentId);
    List<AppointmentResponseDto> getPatientCurrentAppointments();
    List<AppointmentResponseDto> alUpcomingPatientAppointments();
    List<AppointmentResponseDto> patientAppointmentHistory();
    AppointmentResponseDto getAppointmentById(String appointmentId);

    List<AppointmentResponseDto> doctorAppointmentHistory();

    List<AppointmentResponseDto> getDoctorUpcomingAppointments();

    List<AppointmentResponseDto> getDoctorCurrentAppointment();

    List<AppointmentResponseDto> doctorCompletedAppointment();

    List<AppointmentResponseDto> doctorCancelledAppointment();

    List<AppointmentResponseDto> patientCompletedAppointment();

    List<AppointmentResponseDto> patientCancelledAppointment();
}
