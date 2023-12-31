package com.khalid.hms.DoctorProfile.service;

import com.khalid.hms.DoctorProfile.dto.request.*;
import com.khalid.hms.DoctorProfile.dto.response.*;
import com.khalid.hms.DoctorProfile.entity.DoctorEntity;
import com.khalid.hms.DoctorProfile.exceptions.CustomException;
import com.khalid.hms.DoctorProfile.exceptions.EmailNotFoundException;
import com.khalid.hms.DoctorProfile.dto.DoctorDto;
import com.khalid.hms.DoctorProfile.exceptions.SlotIsBookedException;

import java.util.List;

public interface DoctorService {
    RegistrationResponseDTO createDoctor(RegistrationRequestDTO user) throws Exception;
    DoctorDto updateDoctor(UpdateRequestDto updateDoctor);
    DoctorDto getDoctorByEmail(String email) throws EmailNotFoundException;
    DoctorDto getDoctorDataById(String id);
    DoctorEntity readByEmail(String email);
    ValidationResponseDTO verifyDoctor(String doctor_id);
    ValidationResponseDTO disableDoctor(String doctorId);
    AvailabilityResponseDto changeStatus();
    List<DoctorDto> getDoctorsByIsAvailable();
    List<DoctorDto> getDoctorsByDepartment(String department);
    List<SlotResponseDTO> createSlotsFromDTO(SlotRequestDTO slotDTO) throws CustomException;
    SlotResponseDTO isSlotBooked(String slotId) throws SlotIsBookedException;
    List<SlotResponseDTO> getAllAvailableSlotsByDoctorId(String doctorId);
    SlotResponseDTO bookSlot(String slotId);
    SlotResponseDTO CancelBookingSlot(String slotId);
//    SlotResponseDTO getSlotBySlot_id(String slot_id);
    DoctorDto getUserProfileDataByToken();

    List<DoctorDto> getAllDoctor();

    ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto);
    List<ReviewResponseDto> getAllReviewByDoctorId(String doctorId);
}
