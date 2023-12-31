package com.khalid.hms.DoctorProfile;

import com.khalid.hms.DoctorProfile.controller.DoctorController;
import com.khalid.hms.DoctorProfile.dto.request.RegistrationRequestDTO;
import com.khalid.hms.DoctorProfile.dto.response.RegistrationResponseDTO;
import com.khalid.hms.DoctorProfile.exceptions.AlreadyExistsException;
import com.khalid.hms.DoctorProfile.service.BindingService;
import com.khalid.hms.DoctorProfile.service.DoctorService;
import com.khalid.hms.DoctorProfile.service.serviceImplementation.DoctorServiceImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DoctorControllerTest {

    @Mock
    private DoctorServiceImplementation doctorService;

    @Mock
    private BindingService bindingService;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Arrange
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(/* provide necessary data */);
        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO(/* provide expected data */);

        when(bindingService.getBindingError(any())).thenReturn(null);
        when(doctorService.createDoctor(any())).thenReturn(responseDTO);

        // Act
        ResponseEntity<Object> response = doctorController.registerUser(requestDTO, null);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());

        // Verify that service method was called with the correct argument
        verify(doctorService, times(1)).createDoctor(requestDTO);
    }

    @Test
    public void testRegisterUser_AlreadyExistsException() throws Exception {
        // Arrange
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(/* provide necessary data */);

        when(bindingService.getBindingError(any())).thenReturn(null);
        when(doctorService.createDoctor(any())).thenThrow(new AlreadyExistsException("Email already exists"));

        // Act
        ResponseEntity<Object> response = doctorController.registerUser(requestDTO, null);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());

        // Verify that service method was called with the correct argument
        verify(doctorService, times(1)).createDoctor(requestDTO);
    }

    @Test
    public void testRegisterUser_BindingError() throws AlreadyExistsException {
        // Arrange
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(/* provide necessary data */);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingService.getBindingError(any())).thenReturn(ResponseEntity.badRequest().body("Binding error"));

        // Act
        ResponseEntity<Object> response = doctorController.registerUser(requestDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Binding error", response.getBody());

        // Verify that service method was not called when there's a binding error
        verifyZeroInteractions(doctorService);
    }

    private void verifyZeroInteractions(DoctorService doctorService) {
    }


}
