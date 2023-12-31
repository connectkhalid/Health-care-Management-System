package com.khalid.hms.DoctorProfile.service.serviceImplementation;

import com.khalid.hms.DoctorProfile.dto.*;
import com.khalid.hms.DoctorProfile.dto.request.ReviewRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.*;
import com.khalid.hms.DoctorProfile.dto.request.SlotRequestDTO;
import com.khalid.hms.DoctorProfile.dto.request.RegistrationRequestDTO;
import com.khalid.hms.DoctorProfile.dto.request.UpdateRequestDto;
import com.khalid.hms.DoctorProfile.entity.DoctorEntity;
import com.khalid.hms.DoctorProfile.entity.ReviewEntity;
import com.khalid.hms.DoctorProfile.entity.SlotEntity;
import com.khalid.hms.DoctorProfile.exceptions.*;
import com.khalid.hms.DoctorProfile.feignclient.UserClient;
import com.khalid.hms.DoctorProfile.repository.AppointmentRepository;
import com.khalid.hms.DoctorProfile.repository.DoctorRepository;
import com.khalid.hms.DoctorProfile.repository.ReviewRepository;
import com.khalid.hms.DoctorProfile.repository.SlotRepository;
import com.khalid.hms.DoctorProfile.service.DoctorService;

import jakarta.ws.rs.ForbiddenException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImplementation implements DoctorService, UserDetailsService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public RegistrationResponseDTO createDoctor(RegistrationRequestDTO doctor) {
        ModelMapper modelMapper = new ModelMapper();
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent())
            throw new AlreadyExistsException("Email already exists");
        DoctorEntity doctorProfile = new DoctorEntity();
        doctorProfile.setDoctor_id(UUID.randomUUID().toString());
        doctorProfile.setName(doctor.getName());
        doctorProfile.setEmail(doctor.getEmail());
        doctorProfile.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctorProfile.setImage(doctor.getImage());
        doctorProfile.setRole("DOCTOR");
        doctorProfile.setGender(doctor.getGender());
        doctorProfile.setDepartment(doctor.getDepartment());
        doctorProfile.setRegistration_number_BDMC(doctor.getRegistration_number_BDMC());
        doctorProfile.setAllocated_room(doctor.getAllocated_room());
        doctorProfile.setQualifications(doctor.getQualifications());
        doctorProfile.setIsValid(false);
        doctorProfile.setIsAvailable(false);
        doctorProfile.setCreated_at(LocalDate.now());
        DoctorEntity storedUserDetails = doctorRepository.save(doctorProfile);
        return modelMapper.map(storedUserDetails, RegistrationResponseDTO.class);
    }
    @Override
    public DoctorDto getDoctorByEmail(String email) throws EmailNotFoundException {
        if (doctorRepository.findByEmail(email).isEmpty())
            throw new EmailNotFoundException("No Doctor is found by this email");
        DoctorEntity doctorEntity = doctorRepository.findByEmail(email).get();
        DoctorDto returnValue = new DoctorDto();
        returnValue.setDoctor_id(doctorEntity.getDoctor_id());
        BeanUtils.copyProperties(doctorEntity, returnValue);
        return returnValue;
    }
    @Override
    public DoctorEntity readByEmail(String email) {
        return doctorRepository.findByEmail(email).get();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (doctorRepository.findByEmail(email).isEmpty())
            System.out.println("No user Found");
        DoctorEntity doctorEntity = doctorRepository.findByEmail(email).get();
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(doctorEntity.getRole()));
        System.out.println("Role: " + roles);
        if (doctorEntity == null) throw new UsernameNotFoundException(email);
        return new User(doctorEntity.getEmail(), doctorEntity.getPassword(),
                true, true, true, true,
                roles);
    }
    @Override
    public DoctorDto getUserProfileDataByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> user = doctorRepository.findByEmail(authentication.getName());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User profile not found for the authenticated user");
        } else {
            return new ModelMapper().map(user.get(), DoctorDto.class);
        }
    }
    @Override
    public List<DoctorDto> getAllDoctor() {
        List<DoctorEntity> doctorEntityList = doctorRepository.findAll();
        if (!doctorEntityList.isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            return doctorEntityList.stream()
                    .map(doctorEntity -> modelMapper.map(doctorEntity, DoctorDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Unable to Find Any Doctor");
        }
    }
    @Override
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto) {
        UserDto userDto = userClient.getPatientProfileByToken();
        //only the patient who have taken appointment, can give review.
        if (appointmentRepository.findByPatientIdAndDoctorIdAndStatus
                (userDto.getId(), reviewRequestDto.getDoctorId(), "COMPLETED") != null) {
            //if RequestDto and getByToken user mismatched
            if (Objects.equals(reviewRequestDto.getPatientId(), userDto.getId())) {
                if (reviewRepository.findByDoctorIdAndPatientId
                        (reviewRequestDto.getDoctorId(), userDto.getId()) == null) {
                    ReviewEntity review = new ReviewEntity();
                    review.setReviewId(UUID.randomUUID().toString());
                    review.setMessage(reviewRequestDto.getMessage());
                    review.setRating(reviewRequestDto.getRating());
                    review.setDoctorId(reviewRequestDto.getDoctorId());
                    review.setPatientId(reviewRequestDto.getPatientId());
                    review.setPatientName(userDto.getName());
                    review.setCreatedAt(LocalDateTime.now());
                     return new ModelMapper().map(review, ReviewResponseDto.class);
                } else {
                    throw new CustomException("You have already given a review to the doctor.");}
            } else {
                throw new ResourceNotFoundException("The given patientId does not match with your patientId");}
        } else {
            throw new CustomException("You have not allowed to review currently.");
        }
    }

    @Override
    public List<ReviewResponseDto> getAllReviewByDoctorId(String doctorId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByDoctorId(doctorId);
        ModelMapper modelMapper = new ModelMapper();
        return reviewEntityList.stream()
                .map(reviewEntity -> modelMapper.map(reviewEntity, ReviewResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorDataById(String doctor_id) {
        Optional<DoctorEntity> doctorProfile = doctorRepository.findById(doctor_id);
        if (doctorProfile.isEmpty()) {
            throw new ResourceNotFoundException("No doctor Found with the given id");
        } else {
            DoctorEntity doctorProfileEntity = doctorProfile.get();
            return new ModelMapper().map(doctorProfileEntity, DoctorDto.class);
        }
    }

    @Override
    public DoctorDto updateDoctor(UpdateRequestDto updateDoctor) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> user = doctorRepository.findByEmail(authentication.getName());
        if (user.isEmpty())
            throw new UsernameNotFoundException("No user found");
        String userId = user.get().getDoctor_id();

        DoctorEntity existingDoctorProfile = doctorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Profile Not Found For User ID: " + userId));
        existingDoctorProfile.setQualifications(existingDoctorProfile.getQualifications() + ", "+ updateDoctor.getQualifications());
        existingDoctorProfile.setName(updateDoctor.getName());
        existingDoctorProfile.setGender(updateDoctor.getGender());
        existingDoctorProfile.setDepartment(updateDoctor.getDepartment());
        existingDoctorProfile.setRegistration_number_BDMC(updateDoctor.getRegistration_number_BDMC());
        existingDoctorProfile.setImage(updateDoctor.getImage());
        doctorRepository.save(existingDoctorProfile);
        return new ModelMapper().map(existingDoctorProfile, DoctorDto.class);
    }

    @Override
    public ValidationResponseDTO verifyDoctor(String doctor_id) {
        Optional<DoctorEntity> doctorProfile = doctorRepository.findById(doctor_id);
        if (doctorProfile.isPresent()) {
            DoctorEntity doctorProfileEntity = doctorProfile.get();
            if (doctorProfileEntity.getIsValid())
                throw new AlreadyExistsException("The Doctor is Already Verified");
            doctorProfileEntity.setIsValid(true);
            doctorProfileEntity.setIsAvailable(true);
            doctorRepository.save(doctorProfileEntity);
            return new ValidationResponseDTO(doctorProfileEntity.getIsValid(), "The doctor is Verified");
        } else {
            throw new ResourceNotFoundException("Unable to verify doctor of doctor_id: "
                    + doctorProfile.get().getDoctor_id());
        }
    }

    @Override
    public ValidationResponseDTO disableDoctor(String doctorId) {
        Optional<DoctorEntity> doctorProfile = doctorRepository.findById(doctorId);
        if (doctorProfile.isPresent()) {
            DoctorEntity doctorProfileEntity = doctorProfile.get();
            if (!doctorProfileEntity.getIsValid())
                throw new AlreadyExistsException("The Doctor is Already been Disable");
            doctorProfileEntity.setIsValid(false);
            doctorProfileEntity.setIsAvailable(false);
            doctorRepository.save(doctorProfileEntity);
            return new ValidationResponseDTO(doctorProfileEntity.getIsValid(), "The doctor has been disabled");
        } else {
            throw new ResourceNotFoundException("Unable to disable doctor with  doctorId: " + doctorProfile.get().getDoctor_id());
        }
    }

    @Override
    public AvailabilityResponseDto changeStatus() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> doctor = doctorRepository.findByEmail(authentication.getName());
        if (doctor.isPresent()) {
            DoctorEntity doctorProfileEntity = doctor.get();
            if (doctorProfileEntity.getIsAvailable()) {
                doctorProfileEntity.setIsAvailable(false);
                doctorRepository.save(doctorProfileEntity);
                return new AvailabilityResponseDto("Doctor is Not Available Now",
                        doctorProfileEntity.getIsAvailable());
            } else {
                doctorProfileEntity.setIsAvailable(true);
                doctorRepository.save(doctorProfileEntity);
                return new AvailabilityResponseDto("Doctor is Available Now"
                        , doctorProfileEntity.getIsAvailable());
            }
        } else {
            throw new ResourceNotFoundException("Unable to change the status of the doctor");
        }
    }

    @Override
    public List<DoctorDto> getDoctorsByIsAvailable() {
        List<DoctorEntity> doctorEntityList = doctorRepository.findAllByIsAvailableIsTrue();
        if (doctorEntityList != null) {
            ModelMapper modelMapper = new ModelMapper();
            return doctorEntityList.stream()
                    .map(doctorEntity -> modelMapper.map(doctorEntity, DoctorDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Unable to Find Any Doctor");
        }
    }

    @Override
    public List<DoctorDto> getDoctorsByDepartment(String department) {
        List<DoctorEntity> doctorEntityList = doctorRepository.findAllByDepartment(department);
        if (doctorEntityList != null) {
            ModelMapper modelMapper = new ModelMapper();
            List<DoctorDto> doctorDtoList = doctorEntityList.stream()
                    .map(doctorEntity -> modelMapper.map(doctorEntity, DoctorDto.class))
                    .collect(Collectors.toList());
            return doctorDtoList;
        } else {
            throw new ResourceNotFoundException("Unable to Find Any Doctor");
        }
    }

    @Override
    public List<SlotResponseDTO> createSlotsFromDTO(SlotRequestDTO slotDTO) throws CustomException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> doctor = doctorRepository.findByEmail(authentication.getName());
        if (slotDTO.getSlotDate().isBefore(LocalDate.now()))
            throw new CustomException("Invalid Request! Can not create appointment on previous date");
        if (!doctor.get().getIsAvailable() || !doctor.get().getIsValid()) {
            throw new CustomException("Verification or Availability Issue");
        }
        List<SlotEntity> slots = slotRepository.findAvailableSlotsByDoctorIdAndDate(doctor.get().getDoctor_id(), slotDTO.getSlotDate());
        if (slots.isEmpty()) {
            LocalTime startTime = slotDTO.getStartTime();
            LocalTime finishTime = startTime.plusMinutes(slotDTO.getDuration());
            List<SlotResponseDTO> appointmentSlots = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            SlotEntity slot = new SlotEntity();
            // Creating slots with a fixed duration of 20 minutes
            while (startTime.isBefore(finishTime)) {
                slot.setSlotId(UUID.randomUUID().toString());
                slot.setDoctorId(doctor.get().getDoctor_id());
                slot.setStartTime(startTime);
                slot.setEndTime(startTime.plusMinutes(20));
                slot.setIsAvailable(true);
                slot.setSlotDate(slotDTO.getSlotDate());
                slotRepository.save(slot);
                appointmentSlots.add(modelMapper.map(slot, SlotResponseDTO.class));
                startTime = startTime.plusMinutes(20);
            }
            return appointmentSlots;
        } else {
            throw new CustomException("Doctor Appointment Slot can not be created on this date:" + slotDTO.getSlotDate() + ". \n Already existed slot for the date!");
        }
    }

    @Override
    public SlotResponseDTO isSlotBooked(String slotId) throws SlotIsBookedException {
        Optional<SlotEntity> checkSlotAvailability = slotRepository.findById(slotId);
        if (checkSlotAvailability.get().getIsAvailable())
            return new ModelMapper().map(
                    checkSlotAvailability.get(),
                    SlotResponseDTO.class);
        else throw new SlotIsBookedException("The slot is already been Booked");
    }

//    @Override
//    public SlotResponseDTO getSlotBySlot_id(String slot_id) {
//        SlotEntity slot = slotRepository.findById(slot_id).get();
//        if (slot.getIsAvailable()) {
//            return new ModelMapper().map(slot, SlotResponseDTO.class);
//        } else throw new CustomException("Slot is booked");
//    }

    @Override
    public List<SlotResponseDTO> getAllAvailableSlotsByDoctorId(String doctorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> user = doctorRepository.findByEmail(authentication.getName());
        if (user.isEmpty())
            throw new UsernameNotFoundException("No user found");
        if (!user.get().getDoctor_id().equals(doctorId))
            throw new ResourceNotFoundException("DoctorId mismatched!");
        List<SlotEntity> availableSlots = slotRepository
                .findAllByDoctorIdAndAndSlotDateIsGreaterThanEqualOrderByStartTime(doctorId, LocalDate.now());
        return getSlotResponseDTOS(availableSlots);
    }

    public List<SlotResponseDTO> getSlotsOfCurrentDateByDoctorId(String doctorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<DoctorEntity> user = doctorRepository.findByEmail(authentication.getName());
        if (user.isEmpty())
            throw new UsernameNotFoundException("No user found");
        if (!user.get().getDoctor_id().equals(doctorId))
            throw new ResourceNotFoundException("DoctorId mismatched!");
        List<SlotEntity> availableSlots = slotRepository
                .findAllByDoctorIdAndSlotDate(doctorId, LocalDate.now().plusDays(2));
        return getSlotResponseDTOS(availableSlots);
    }

    public List<SlotResponseDTO> getAppointmentSlotByDate(String doctorId, LocalDate slotDate) {
        List<SlotEntity> slots = slotRepository.findSlotEntitiesByDoctorIdAndSlotDate(doctorId, slotDate);
        return getSlotResponseDTOS(slots);
    }

    @Override
    public SlotResponseDTO bookSlot(String slotId) {
        UserDto user = userClient.getPatientProfileByToken();
        if (user == null) {
            throw new CustomException("You are not authorized to appoint");
        }
        String patientId = user.getId();
        Optional<SlotEntity> checkSlot = slotRepository.findById(slotId);
        if (checkSlot.isEmpty())
            throw new CustomException("Unable to find Slot!");
        else {
            SlotEntity slot = checkSlot.get();
            if (!slot.getIsAvailable())
                throw new CustomException("Slot has already been booked");
            slot.setIsAvailable(false);
            slot.setPatientId(patientId);
            slotRepository.save(slot);
            return new ModelMapper().map(slot, SlotResponseDTO.class);
        }
    }

    @Override
    public SlotResponseDTO CancelBookingSlot(String slotId) {
        Optional<SlotEntity> checkSlot = slotRepository.findById(slotId);
        if (checkSlot.isEmpty())
            throw new CustomException("Unable to find Slot!");
        else {
            SlotEntity slot = checkSlot.get();
            if (slot.getIsAvailable())
                throw new CustomException("Slot is still available");
            slot.setIsAvailable(true);
            slot.setPatientId(null);
            slotRepository.save(slot);
            return new ModelMapper().map(slot, SlotResponseDTO.class);
        }
    }

    private List<SlotResponseDTO> getSlotResponseDTOS(List<SlotEntity> slots) {
        ModelMapper mapper = new ModelMapper();
        List<SlotResponseDTO> dtoList = new ArrayList<>();
        for (SlotEntity slotEntity : slots) {
            SlotResponseDTO dto = mapper.map(slotEntity, SlotResponseDTO.class);
            dtoList.add(dto);
        }
        return dtoList;
    }
}