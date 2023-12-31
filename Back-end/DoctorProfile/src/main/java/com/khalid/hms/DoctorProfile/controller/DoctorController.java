package com.khalid.hms.DoctorProfile.controller;

import com.khalid.hms.DoctorProfile.dto.DoctorDto;
import com.khalid.hms.DoctorProfile.dto.request.RegistrationRequestDTO;
import com.khalid.hms.DoctorProfile.dto.request.ReviewRequestDto;
import com.khalid.hms.DoctorProfile.dto.request.SlotRequestDTO;
import com.khalid.hms.DoctorProfile.dto.request.UpdateRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.RegistrationResponseDTO;
import com.khalid.hms.DoctorProfile.dto.response.SlotResponseDTO;
import com.khalid.hms.DoctorProfile.exceptions.EmailNotFoundException;
import com.khalid.hms.DoctorProfile.service.BindingService;
import com.khalid.hms.DoctorProfile.service.serviceImplementation.DoctorServiceImplementation;
import com.khalid.hms.DoctorProfile.exceptions.AlreadyExistsException;
import com.khalid.hms.DoctorProfile.exceptions.AuthenticationExceptionFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorServiceImplementation doctorService;
    @Autowired
    private BindingService bindingService;

    /**
     * Register a new doctor.
     *
     * @param registerRequest The registration request data.
     * @param bindingResult   Binding result for validation.
     * @return ResponseEntity containing the registration response or validation errors.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegistrationRequestDTO registerRequest,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = bindingService.getBindingError(bindingResult);
        if (errors != null) return errors;
        try {
            RegistrationResponseDTO registerResponse = doctorService.createDoctor(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    /**
     * Update the profile of a doctor.
     *
     * @param doctorProfileDto The updated profile data.
     * @param bindingResult    Binding result for validation.
     * @return ResponseEntity containing the updated profile or validation errors.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateDoctorProfile
    (@RequestBody UpdateRequestDto doctorProfileDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = bindingService.getBindingError(bindingResult);
        if (errors != null) return errors;
        try {
            DoctorDto userProfile = doctorService.updateDoctor(doctorProfileDto);
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    /**
     * Verify a doctor by their ID.
     *
     * @param doctorId The ID of the doctor to be verified.
     * @return ResponseEntity containing the verification status or conflict message.
     */
    @PostMapping("/verify/{doctorId}")
    public ResponseEntity<?> doctorVerification
    (@PathVariable("doctorId") String doctorId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService
                    .verifyDoctor(doctorId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    /**
     * Disable a doctor by their ID.
     *
     * @param doctorId The ID of the doctor to be disabled.
     * @return ResponseEntity containing the disable status or conflict message.
     */
    @PostMapping("/disable-doctor/{doctorId}")
    public ResponseEntity<?> disableDoctor
    (@PathVariable("doctorId") String doctorId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(doctorService.disableDoctor(doctorId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/change-availability-status")
    public ResponseEntity<Object> changeStatus() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.changeStatus());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
    /**
     * Get a doctor by email.
     *
     * @param email The email of the doctor.
     * @return ResponseEntity containing the doctor data or not found message.
     */
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<Object> getDoctorByEmail(@PathVariable("email") String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorByEmail(email));
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    /**
     * Get the profile of the currently authenticated doctor using the token.
     *
     * @return ResponseEntity containing the doctor profile or unauthorized message.
     */
    @GetMapping("/get-doctor-profile-by-token")
    public ResponseEntity<Object> getDoctorProfileByToken() {
        try {
            DoctorDto userProfile = doctorService.getUserProfileDataByToken();
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/get-doctor-profile-by-id/{doctorId}")
    public ResponseEntity<Object> getProfileById(@PathVariable("doctorId") String doctorId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.getDoctorDataById(doctorId));
        }catch (AuthenticationExceptionFound e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    /**
     * Get all available doctors.
     *
     * @return ResponseEntity containing a list of available doctors or unauthorized message.
     */
    @GetMapping("/get-all-available-doctor")
    public ResponseEntity<Object> getDoctorByAvailability() {
        try {
            List<DoctorDto> userProfiles = doctorService.getDoctorsByIsAvailable();
            return ResponseEntity.status(HttpStatus.OK).body(userProfiles);
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    /**
     * Get all doctors.
     *
     * @return ResponseEntity containing a list of all doctors or unauthorized message.
     */
    @GetMapping("/get-all-doctor")
    public ResponseEntity<Object> getAllDoctor() {
        try {
            List<DoctorDto> userProfiles = doctorService.getAllDoctor();
            return ResponseEntity.status(HttpStatus.OK).body(userProfiles);
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    /**
     * Get all doctors by department.
     *
     * @param department The department of the doctors.
     * @return ResponseEntity containing a list of doctors in the specified department or unauthorized message.
     */
    @GetMapping("/get-all-doctor-by-department/{department}")
    public ResponseEntity<Object> getDoctorByDepartment(@PathVariable("department") String department) {
        try {
            List<DoctorDto> userProfile = doctorService.getDoctorsByDepartment(department);
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    /**
     * Create appointment slots for a doctor.
     *
     * @param slotRequestDTO The slot request data.
     * @param bindingResult   Binding result for validation.
     * @return ResponseEntity containing the created slots or validation errors.
     */
    @PostMapping("/create-slots")
    public ResponseEntity<Object> createAppointmentSlot(@RequestBody SlotRequestDTO slotRequestDTO, BindingResult bindingResult) {
        ResponseEntity<Object> errors = bindingService.getBindingError(bindingResult);
        if (errors != null) return errors;
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.createSlotsFromDTO(slotRequestDTO));
    }
    /**
     * Get all available slots for a doctor.
     *
     * @param doctorId The request data for available slots.
     * @return ResponseEntity containing a list of available slots or unauthorized message.
     */
    @GetMapping("/get-all-available-slot-doctor/{doctorId}")
    public ResponseEntity<Object> getAllAppointmentSlotById(@PathVariable("doctorId")String doctorId){
        try {
            List<SlotResponseDTO> responseDTOS = doctorService.getAllAvailableSlotsByDoctorId(doctorId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
        }catch (AuthenticationExceptionFound e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/get-current-slot-doctor/{doctorId}")
    public ResponseEntity<Object> getCurrentAppointmentSlotById(@PathVariable("doctorId")String doctorId){
        try {
            List<SlotResponseDTO> responseDTOS = doctorService.getSlotsOfCurrentDateByDoctorId(doctorId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
        }catch (AuthenticationExceptionFound e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/get-available-slot-by-date/{doctorId}/{slotDate}")
    public ResponseEntity<Object> getAppointmentSlotByDate(@PathVariable("doctorId")String doctorId,  @PathVariable LocalDate slotDate){
        try {
            List<SlotResponseDTO> responseDTOS = doctorService.getAppointmentSlotByDate(doctorId, slotDate);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTOS);
        }catch (AuthenticationExceptionFound e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    /**
     * Book an appointment slot by slot ID.
     *
     * @param slotId The ID of the slot to be booked.
     * @return ResponseEntity containing the booking status or not implemented message.
     */
    @PostMapping("/book-appointment-slot/{slotId}")
    public ResponseEntity<?> bookSlot(@PathVariable("slotId") String slotId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.bookSlot(slotId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
    @PostMapping("/create-doctor-review")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.createReview(reviewRequestDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
    @GetMapping("/get-all-reviews-doctor/{doctorId}")
    public ResponseEntity<?> getAllReview(@PathVariable("doctorId") String doctorId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAllReviewByDoctorId(doctorId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(e.getMessage());
        }
    }
}