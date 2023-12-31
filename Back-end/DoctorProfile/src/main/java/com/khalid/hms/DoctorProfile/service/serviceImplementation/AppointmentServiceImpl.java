package com.khalid.hms.DoctorProfile.service.serviceImplementation;

import com.khalid.hms.DoctorProfile.dto.DoctorDto;
import com.khalid.hms.DoctorProfile.exceptions.ResourceNotFoundException;
import com.khalid.hms.DoctorProfile.service.AppointmentService;
import com.khalid.hms.DoctorProfile.dto.UserDto;
import com.khalid.hms.DoctorProfile.dto.request.AppointmentRequestDto;
import com.khalid.hms.DoctorProfile.dto.response.AppointmentResponseDto;
import com.khalid.hms.DoctorProfile.dto.response.SlotResponseDTO;
import com.khalid.hms.DoctorProfile.entity.AppointmentEntity;
import com.khalid.hms.DoctorProfile.entity.SlotEntity;
import com.khalid.hms.DoctorProfile.exceptions.CustomException;
import com.khalid.hms.DoctorProfile.exceptions.SlotIsBookedException;
import com.khalid.hms.DoctorProfile.feignclient.UserClient;
import com.khalid.hms.DoctorProfile.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private DoctorServiceImplementation doctorService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserClient userClient;

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto requestDto)
            throws SlotIsBookedException, Exception {
        //if any appointment present with the date, time and patientId
        UserDto user = userClient.getPatientProfileByToken();
        DoctorDto doctor = doctorService.getDoctorDataById(requestDto.getDoctorId());
        String patientId = user.getId();
        //check weather the patient has already an appointment with the doctor
        if (appointmentRepository.findDistinctByPatientIdAndDoctorIdAndAppointmentDate
                (patientId, doctor.getDoctor_id(), requestDto.getAppointmentDate()) == null) {
            //TAKING THE APPOINTMENT DATE TO CHECK WHETHER THERE IS ANY
            // EXISTING APPOINTMENT ON THAT APPOINTMENT TIME
            if (appointmentRepository.findAppointmentEntityByPatientIdAndAppointmentDateAndStartTime(
                    user.getId(), requestDto.getAppointmentDate(),
                    requestDto.getStartTime()) == null) {
                //if the slot has been booked already
                SlotResponseDTO slotResponseDTO = doctorService.isSlotBooked(requestDto.getSlotId());
                if (slotResponseDTO != null) {
                    ModelMapper mapper = new ModelMapper();
                    SlotEntity slot = mapper.map(slotResponseDTO, SlotEntity.class);
                    doctorService.bookSlot(slot.getSlotId());
                    AppointmentEntity appointment = new AppointmentEntity();
                    appointment.setAppointmentId(UUID.randomUUID().toString());
                    appointment.setPatientName(user.getName());
                    appointment.setDoctorName(doctor.getName());
                    appointment.setPatientId(patientId);
                    appointment.setDoctorId(requestDto.getDoctorId());
                    appointment.setSlotId(slot.getSlotId());
                    appointment.setAppointmentType(requestDto.getAppointmentType());
                    if (!Objects.equals(requestDto.getAppointmentType(), "OFFLINE")) {
                        appointment.setConferenceLink(requestDto.getConferenceLink());
                    } else {
                        appointment.setConferenceLink(null);
                    }
                    appointment.setAppointmentDate(slotResponseDTO.getSlotDate());
                    appointment.setStartTime(slot.getStartTime());
                    appointment.setEndTime(slot.getEndTime());
                    appointment.setStatus("UPCOMING");
                    appointment.setCreatedAt(LocalDateTime.now());
                    appointmentRepository.save(appointment);
                    return mapper.map(appointment, AppointmentResponseDto.class);
                } else {
                    throw new Exception("Slot is not available.");
                }
            } else {
                throw new CustomException("Unable to create Appointment. " +
                        "Already have an appointment with date and time");
            }
        } else {
            throw new CustomException("you have already an appointment with the doctor on "
                    + requestDto.getAppointmentDate());
        }
    }

    @Override
    public AppointmentResponseDto cancelSlotBooking(String appointmentId) throws CustomException {
        DoctorDto currentDoctorDto = doctorService.getUserProfileDataByToken();
        Optional<AppointmentEntity> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            if (appointment.get().getAppointmentDate().isEqual(LocalDate.now())
                    && appointment.get().getStartTime().isBefore(LocalTime.now()))
                throw new CustomException("You can not decline the appointment! Time exceed!");
            else if (!appointment.get().getDoctorId().equals(currentDoctorDto.getDoctor_id())) {
                throw new CustomException("You are not allowed to cancel the appointment!");
            } else {
                appointment.get().setStatus("CANCELLED");
                doctorService.CancelBookingSlot(appointment.get().getSlotId());
                appointmentRepository.save(appointment.get());
                return new ModelMapper().map(appointment, AppointmentResponseDto.class);
            }
        } else throw new CustomException("Unable to cancel the appointment");

    }

    @Override
    public AppointmentResponseDto completeAppointment(String appointmentId) {
        DoctorDto currentDoctorDto = doctorService.getUserProfileDataByToken();
        Optional<AppointmentEntity> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            if (appointment.get().getAppointmentDate().isEqual(LocalDate.now())
                    && appointment.get().getStartTime().isBefore(LocalTime.now())) {
                throw new CustomException(
                        "You can not decline the appointment! date time exceed!");
            } else if (!appointment.get().getDoctorId().equals(currentDoctorDto.getDoctor_id())) {
                throw new CustomException(
                        "You are not allowed to change the status of the appointment!");
            } else {
                if (!appointment.get().getStatus().equals("CANCELLED")) {
                    appointment.get().setStatus("COMPLETED");
                    appointmentRepository.save(appointment.get());
                    return new ModelMapper().map(appointment, AppointmentResponseDto.class);
                } else
                    throw new CustomException(
                            "Appointment is failed to be complete. Possible reason status is null or CANCELLED");
            }
        } else throw new CustomException("Unable to cancel+ the appointment");
    }

    @Override
    public List<AppointmentResponseDto> getPatientCurrentAppointments() {
        UserDto user = userClient.getPatientProfileByToken();
        LocalDate currentDate = LocalDate.now();
        Optional<List<AppointmentEntity>> createAppointments = appointmentRepository
                .findAllByPatientIdAndAppointmentDate(user.getId(), currentDate);
        if (createAppointments.isPresent()) {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : createAppointments.get()) {
                if (appointmentEntity.getStatus().equals("UPCOMING")) {
                    AppointmentResponseDto responseDto = modelMapper
                            .map(appointmentEntity, AppointmentResponseDto.class);
                    appointmentResponseDtos.add(responseDto);
                }
            }
            return appointmentResponseDtos;
        } else {
            throw new CustomException("No appointment found on the current date");
        }
    }

    @Override
    public List<AppointmentResponseDto> alUpcomingPatientAppointments() {
        UserDto user = userClient.getPatientProfileByToken();
        LocalDate currentDate = LocalDate.now();
        List<AppointmentEntity> upcomingAppointments = appointmentRepository
                .findByPatientIdAndAppointmentDateAfterOrderByAppointmentDateAscStartTimeAsc(user.getId(), currentDate);
        if (upcomingAppointments.isEmpty())
            throw new CustomException("No upcoming appointment is present.");
        else {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : upcomingAppointments) {
                if (appointmentEntity.getStatus().equals("UPCOMING")) {
                    AppointmentResponseDto responseDto = modelMapper.map(appointmentEntity, AppointmentResponseDto.class);
                    appointmentResponseDtos.add(responseDto);
                }
            }
            return appointmentResponseDtos;
        }
    }

    @Override
    public List<AppointmentResponseDto> patientAppointmentHistory() {
        UserDto user = userClient.getPatientProfileByToken();
        List<AppointmentEntity> appointmentHistory = appointmentRepository
                .findAllByPatientId(user.getId());
        if (appointmentHistory == null)
            throw new CustomException("No Patient History is present.");
        else {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : appointmentHistory) {
                AppointmentResponseDto responseDto = modelMapper.map(appointmentEntity, AppointmentResponseDto.class);
                appointmentResponseDtos.add(responseDto);
            }
            return appointmentResponseDtos;
        }
    }

    @Override
    public AppointmentResponseDto getAppointmentById(String appointmentId) {
        Optional<AppointmentEntity> optAppointment = appointmentRepository.findById(appointmentId);
        if (optAppointment.isPresent())
            return new ModelMapper().map(optAppointment.get(), AppointmentResponseDto.class);
        else throw new RuntimeException("Appointment Not found");
    }

    @Override
    public List<AppointmentResponseDto> doctorAppointmentHistory() {
        DoctorDto doctorDto = doctorService.getUserProfileDataByToken();
        if (doctorDto == null) {
            throw new ResourceNotFoundException("User profile not found for the authenticated user");
        }
        if (!Objects.equals(doctorDto.getDoctor_id(), doctorDto.getDoctor_id()))
            throw new ResourceNotFoundException("DoctorId Does Not Match");
        List<AppointmentEntity> appointmentHistory = appointmentRepository
                .findAllByDoctorId(doctorDto.getDoctor_id());
        if (appointmentHistory == null)
            throw new CustomException("No Patient History is present.");
        else {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : appointmentHistory) {
                AppointmentResponseDto responseDto = modelMapper.map(appointmentEntity, AppointmentResponseDto.class);
                appointmentResponseDtos.add(responseDto);
            }
            return appointmentResponseDtos;
        }
    }

    @Override
    public List<AppointmentResponseDto> getDoctorUpcomingAppointments() {
        DoctorDto doctorDto = doctorService.getUserProfileDataByToken();
        if (doctorDto == null) {
            throw new ResourceNotFoundException("User profile not found for the authenticated user");
        }
        if (!Objects.equals(doctorDto.getDoctor_id(), doctorDto.getDoctor_id()))
            throw new ResourceNotFoundException("DoctorId Does Not Match");
        LocalDate currentDate = LocalDate.now();
        List<AppointmentEntity> upcomingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateAfterOrderByAppointmentDateAscStartTimeAsc(doctorDto.getDoctor_id(), currentDate);
        if (upcomingAppointments.isEmpty())
            throw new CustomException("No upcoming appointment is present.");
        else {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : upcomingAppointments) {
                if (appointmentEntity.getStatus().equals("UPCOMING")) {
                    AppointmentResponseDto responseDto = modelMapper.map(appointmentEntity, AppointmentResponseDto.class);
                    appointmentResponseDtos.add(responseDto);
                }
            }
            return appointmentResponseDtos;
        }
    }

    @Override
    public List<AppointmentResponseDto> getDoctorCurrentAppointment() {
        DoctorDto doctorDto = doctorService.getUserProfileDataByToken();
        if (doctorDto == null) {
            throw new ResourceNotFoundException("User profile not found for the authenticated user");
        }
        if (!Objects.equals(doctorDto.getDoctor_id(), doctorDto.getDoctor_id()))
            throw new ResourceNotFoundException("DoctorId Does Not Match");
        LocalDate currentDate = LocalDate.now();
        List<AppointmentEntity> createAppointments = appointmentRepository
                .findAllByDoctorIdAndAndAppointmentDate(doctorDto.getDoctor_id(), currentDate);
        if (createAppointments != null) {
            List<AppointmentResponseDto> appointmentResponseDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (AppointmentEntity appointmentEntity : createAppointments) {
                if (appointmentEntity.getStatus().equals("UPCOMING")) {
                    AppointmentResponseDto responseDto = modelMapper
                            .map(appointmentEntity, AppointmentResponseDto.class);
                    appointmentResponseDtos.add(responseDto);
                }
            }
            return appointmentResponseDtos;
        } else {
            throw new CustomException("No appointment found on the current date");
        }
    }
    @Override
    public List<AppointmentResponseDto> doctorCompletedAppointment() {
        DoctorDto doctorDto = doctorService.getUserProfileDataByToken();
        System.out.println("DoctorID: " + doctorDto.getDoctor_id());
        List<AppointmentEntity> completedAppointments = appointmentRepository
                .findAllByDoctorIdAndStatus(doctorDto.getDoctor_id(), "COMPLETED");
        return convertListOfAppointmentEntityToResponseDto(completedAppointments);
    }
    @Override
    public List<AppointmentResponseDto> doctorCancelledAppointment() {
        DoctorDto doctorDto = doctorService.getUserProfileDataByToken();
        List<AppointmentEntity> cancelledAppointment = appointmentRepository
                .findAllByDoctorIdAndStatus(doctorDto.getDoctor_id(), "CANCELLED");
        return convertListOfAppointmentEntityToResponseDto(cancelledAppointment);
    }
    @Override
    public List<AppointmentResponseDto> patientCompletedAppointment() {
        UserDto patient = userClient.getPatientProfileByToken();
        List<AppointmentEntity> completedAppointments = appointmentRepository
                .findAllByPatientIdAndStatus(patient.getId(), "COMPLETED");
        return convertListOfAppointmentEntityToResponseDto(completedAppointments);
    }
    @Override
    public List<AppointmentResponseDto> patientCancelledAppointment() {
        UserDto patient = userClient.getPatientProfileByToken();
        List<AppointmentEntity> cancelledAppointments = appointmentRepository
                .findAllByPatientIdAndStatus(patient.getId(), "CANCELLED");
        return convertListOfAppointmentEntityToResponseDto(cancelledAppointments);
    }
    private List<AppointmentResponseDto> convertListOfAppointmentEntityToResponseDto(List<AppointmentEntity> appointmentEntities){
        ModelMapper modelMapper = new ModelMapper();
        if (appointmentEntities != null) {
            List<AppointmentResponseDto> completedAppointmentDto = new ArrayList<>();
            for (AppointmentEntity appointmentEntity : appointmentEntities) {
                AppointmentResponseDto responseDto = modelMapper
                        .map(appointmentEntity, AppointmentResponseDto.class);
                completedAppointmentDto.add(responseDto);
            }
            return completedAppointmentDto;
        }
        return null;
    }
}