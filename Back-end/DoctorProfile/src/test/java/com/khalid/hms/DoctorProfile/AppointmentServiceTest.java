package com.khalid.hms.DoctorProfile;

import com.khalid.hms.DoctorProfile.dto.request.AppointmentRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.AppointmentResponseDto;
import com.khalid.hms.DoctorProfile.entity.AppointmentEntity;
import com.khalid.hms.DoctorProfile.exceptions.CustomException;
import com.khalid.hms.DoctorProfile.exceptions.SlotIsBookedException;
import com.khalid.hms.DoctorProfile.feignclient.UserClient;
import com.khalid.hms.DoctorProfile.repository.AppointmentRepository;
import com.khalid.hms.DoctorProfile.service.AppointmentService;
import com.khalid.hms.DoctorProfile.service.DoctorService;
import com.khalid.hms.DoctorProfile.service.serviceImplementation.AppointmentServiceImpl;
import com.khalid.hms.DoctorProfile.service.serviceImplementation.DoctorServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {
    @Mock
    private DoctorServiceImplementation doctorService;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserClient userClient;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createAppointment_Success() throws Exception, SlotIsBookedException {
        // Arrange
        AppointmentRequestDto requestDto = new AppointmentRequestDto();
        // populate requestDto with valid data
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        // populate appointmentEntity with valid data
        AppointmentResponseDto expectedResponse = new AppointmentResponseDto();
        // populate expectedResponse with valid data


        when(appointmentRepository.findByPatientIdAndDoctorIdAndAppointmentDate(anyString(), anyString(), any()))
                .thenReturn(null);
        when(appointmentRepository.findAppointmentEntityByPatientIdAndAppointmentDateAndStartTime(anyString(), any(), any()))
                .thenReturn(null);
        when(modelMapper.map(any(), eq(AppointmentResponseDto.class))).thenReturn(expectedResponse);

        // Assume slot booking and other dependencies are handled correctly

        // Act
        AppointmentResponseDto actualResponse = appointmentService.createAppointment(requestDto);

        // Assert
        assertNotNull(actualResponse);
        // Perform other necessary assertions
    }
    @Test
    void createAppointment_SlotBookedException() {
        // Arrange
        AppointmentRequestDto requestDto = new AppointmentRequestDto();
        // populate requestDto with valid data

        when(appointmentRepository.findByPatientIdAndDoctorIdAndAppointmentDate(anyString(), anyString(), any()))
                .thenReturn(null);
        when(appointmentRepository.findAppointmentEntityByPatientIdAndAppointmentDateAndStartTime(anyString(), any(), any()))
                .thenReturn(null);

        // Assume slot booking service throws SlotIsBookedException
        // Mock this behavior

        // Act & Assert
        assertThrows(SlotIsBookedException.class, () -> {
            appointmentService.createAppointment(requestDto);
        });
    }
    @Test
    void createAppointment_ExistingAppointmentException() {
        // Arrange
        AppointmentRequestDto requestDto = new AppointmentRequestDto();
        // populate requestDto with valid data
        AppointmentEntity existingAppointment = new AppointmentEntity();
        // populate existingAppointment with valid data

        when(appointmentRepository.findByPatientIdAndDoctorIdAndAppointmentDate(anyString(), anyString(), any()))
                .thenReturn(existingAppointment);

        // Act & Assert
        assertThrows(CustomException.class, () -> {
            appointmentService.createAppointment(requestDto);
        });
    }
}