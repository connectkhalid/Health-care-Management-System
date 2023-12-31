package com.khalid.hms.UserProfile.service.serviceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.khalid.hms.UserProfile.dto.*;
import com.khalid.hms.UserProfile.dto.RequestDto.HealthDataRequestDto;
import com.khalid.hms.UserProfile.dto.RequestDto.UserRequestDto;
import com.khalid.hms.UserProfile.dto.ResponseDto.UserResponseDto;
import com.khalid.hms.UserProfile.exceptions.CustomException;
import com.khalid.hms.UserProfile.exceptions.ResourceNotFoundException;
import com.khalid.hms.UserProfile.entity.HealthDataEntity;
import com.khalid.hms.UserProfile.entity.UserEntity;
import com.khalid.hms.UserProfile.exceptions.AlreadyExistsException;
import com.khalid.hms.UserProfile.repository.HealthDataRepository;
import com.khalid.hms.UserProfile.repository.UserRepository;
import com.khalid.hms.UserProfile.service.UserService;
import jakarta.ws.rs.ForbiddenException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
    @Autowired
    private HealthDataRepository healthDataRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CDSS_Service cdss_service;

    /**
     * Creates a new user in the system.
     *
     * @param user The UserDto containing user details.
     * @return A UserResponseDto with the registered user's details.
     * @throws AlreadyExistsException Thrown if the email already exists in the repository.
     */
    @Override
    public UserResponseDto createUser(UserRequestDto user) {
        ModelMapper modelMapper = new ModelMapper();
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new AlreadyExistsException("Email already exists");
        UserEntity userEntity;
        userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setIsVerified(true);
        userEntity.setCreatedAt(LocalDate.now());
        //always encrypt the password and assign id after mapping
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserEntity storedUserDetails = userRepository.save(userEntity);
        return modelMapper.map(storedUserDetails, UserResponseDto.class);
    }

    /**
     * Retrieves user details based on the provided email.
     *
     * @param email The email of the user.
     * @return A UserDto containing the user's details.
     * @throws UsernameNotFoundException Thrown if no user is found with the given email.
     */
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        if (userEntity == null) throw new UsernameNotFoundException("No record found");
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    /**
     * Implements the UserDetailsService interface method to load user details by email.
     *
     * @param email The email of the user.
     * @return A UserDetails object representing the user's details.
     * @throws UsernameNotFoundException Thrown if no user is found with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userEntity.getRole()));
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true,
                roles);
    }

    /**
     * Retrieves the user profile data for the authenticated user based on the authentication token.
     * No param
     *
     * @return UserProfileDto with the user profile data.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     */
    @Override
    public UserResponseDto getUserProfileDataByToken() {
        UserEntity user = isUserAuthenticated();
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    /**
     * Retrieves the user profile data for the specified user ID.
     *
     * @param userId The ID of the user.
     * @return A UserProfileDto with the user profile data.
     * @throws ResourceNotFoundException Thrown if the user profile is not found for the given user ID.
     */
    @Override
    public UserResponseDto getUserProfileDataById(String userId) throws IllegalAccessException {
        UserEntity loggedInPatient = isUserAuthenticated();
        Optional<UserEntity> userProfileOptional = userRepository.findById(userId);
        if (userProfileOptional.isPresent()) {
            if (!loggedInPatient.getId().equals(userProfileOptional.get().getId()))
                throw new CustomException("UserID doesn't match with given userID");
            return new ModelMapper().map(userProfileOptional.get(), UserResponseDto.class);
        } else {
            throw new ResourceNotFoundException("User profile not found by id");
        }
    }

    /**
     * Updates the user profile for the authenticated user.
     *
     * @param updateRequestDto The UpdateProfileRequestDto containing updated user profile details.
     * @return A UserProfileDto with the updated user profile details.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     */
    @Override
    public UserResponseDto updateUserProfile(UpdateProfileRequestDto updateRequestDto) {
        UserEntity user = isUserAuthenticated();
        String userId = user.getId();
        // Retrieve existing user profile
        Optional<UserEntity> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            if (!existingUserOptional.get().getIsVerified())
                throw new ForbiddenException("Patient is not verified");
            ModelMapper modelMapper = new ModelMapper();
            LocalDate createdAt = existingUserOptional.get().getCreatedAt();
            UserEntity existingUserProfile = existingUserOptional.get();
            existingUserProfile = getUserEntity(updateRequestDto, existingUserOptional, userId);
            existingUserProfile.setCreatedAt(createdAt);
            UserEntity updatedUserProfile = userRepository.save(existingUserProfile);
            return modelMapper.map(updatedUserProfile, UserResponseDto.class);
        } else {
            throw new ResourceNotFoundException("User profile not found for user ID: " + userId);
        }
    }

    /**
     * Creates health data for the authenticated user.
     *
     * @param healthDataRequestDtoDto The HealthDataRequestDto containing health data details.
     * @return A HealthDataDto with the created health data details.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     * @throws AlreadyExistsException    Thrown if health data already exists for the authenticated user.
     * @throws RuntimeException          Thrown if an error occurs during health data creation.
     * @throws JsonProcessingException   Thrown if an error occurs during converting dot to string.
     */
    @Override
    public HealthDataDto createHealthData(HealthDataRequestDto healthDataRequestDtoDto)
            throws JsonProcessingException {
        try {
            UserEntity user = isUserAuthenticated();
            String userId = user.getId();
            if (healthDataRepository.findById(userId).isPresent()) {
                throw new AlreadyExistsException("This user already exists in the database");
            }
            ModelMapper modelMapper = new ModelMapper();
            HealthDataEntity healthDataEntity = modelMapper.map(healthDataRequestDtoDto, HealthDataEntity.class);
            // Calculate BMI
            double bmi = healthDataRequestDtoDto.getWeightKg() / (
                    (healthDataRequestDtoDto.getHeightCm() / 100) * (healthDataRequestDtoDto.getHeightCm() / 100));
            healthDataEntity.setBmi(bmi);
            healthDataEntity.setUserId(userId);
            healthDataEntity.setCreatedAt(LocalDate.now());
            healthDataRepository.save(healthDataEntity);

            return modelMapper.map(healthDataEntity, HealthDataDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating health data", e);
        } finally {
            cdss_service.callGptWithPatientData();
        }
    }
    /**
     * Updates health data for the authenticated user.
     *
     * @param healthDataDto The HealthDataRequestDto containing updated health data details.
     * @return A HealthDataDto with the updated health data details.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     */
    @Override
    public HealthDataDto updateUserHealthData(HealthDataRequestDto healthDataDto)
            throws JsonProcessingException {
        try {
            UserEntity user = isUserAuthenticated();
            ModelMapper mapper = new ModelMapper();
            Optional<HealthDataEntity> existingHealthDataOptional = healthDataRepository
                    .findById(user.getId());
            if (existingHealthDataOptional.isPresent()) {
                HealthDataEntity existingHealthData;
                existingHealthData = mapper.map(healthDataDto, HealthDataEntity.class);
                existingHealthData.setUserId(user.getId());
                double bmi = healthDataDto.getWeightKg() / ((healthDataDto.getHeightCm() / 100)
                        * (healthDataDto.getHeightCm() / 100));
                existingHealthData.setBmi(bmi);
                healthDataRepository.save(existingHealthData);
                return mapper.map(existingHealthData, HealthDataDto.class);
            }
            else {
                throw new ResourceNotFoundException("No data found with the user id." +
                        " Please crate your health data");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating health data", e);
        } finally {
            cdss_service.callGptWithPatientData();
        }
    }
    /**
     * Retrieves health data for the authenticated user and sends it in the form of a SendHealthDataDto.
     *
     * @return A SendHealthDataDto with the health data details.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     */
    @Override
    public SendHealthDataDto sendHealthData() {
        UserEntity user = isUserAuthenticated();
        String userId = user.getId();
        HealthDataEntity healthDataEntity = healthDataRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Health data not found"));
        ModelMapper modelMapper = new ModelMapper();
        SendHealthDataDto sendHealthDataDto = modelMapper.map(healthDataEntity, SendHealthDataDto.class);

        sendHealthDataDto.setUserId(userId);
        sendHealthDataDto.setAge(user.getAge());
        return sendHealthDataDto;
    }
    /**
     * Retrieves the UserEntity based on the provided email.
     *
     * @param email The email of the user.
     * @return The UserEntity corresponding to the email.
     */
    @Override
    public UserEntity readByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
    /**
     * Retrieves health data for the authenticated user.
     *
     * @return A HealthDataDto with the health data details.
     * @throws ResourceNotFoundException Thrown if the authenticated user is not found.
     */
    @Override
    public HealthDataDto getUserHealthDataById() {
        UserEntity user = isUserAuthenticated();
        String userId = user.getId();
        Optional<HealthDataEntity> healthDataOptional = healthDataRepository.findById(userId);
        if (healthDataOptional.isPresent()) {
            HealthDataEntity healthDataEntity = healthDataOptional.get();
            return new ModelMapper().map(healthDataEntity, HealthDataDto.class);
        } else {
            throw new ResourceNotFoundException("User profile not found for the authenticated user");
        }
    }
    /**
     * Retrieves user details based on the provided email.
     *
     * @param email The email of the user.
     * @return A UserDto containing the user's details.
     */
    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        return new ModelMapper().map(userEntity, UserResponseDto.class);
    }
    @Override
    public List<HealthDataDto> getAllUserHealthData() {
        List<HealthDataEntity> healthdataList = healthDataRepository.findAll();
        if (healthdataList.isEmpty())
            return null;
        else {
            List<HealthDataDto> healthDataDtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            for (HealthDataEntity healthDataEntity : healthdataList) {
                HealthDataDto responseDto = modelMapper.map(healthDataEntity, HealthDataDto.class);
                healthDataDtos.add(responseDto);
            }
            return healthDataDtos;
        }
    }
    private UserEntity isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        if (user.isEmpty())
            throw new UsernameNotFoundException("No Patient found with the given id");
        return user.get();
    }
    private static UserEntity getUserEntity(UpdateProfileRequestDto updateRequestDto
            , Optional<UserEntity> existingUserOptional, String userId) {
        UserEntity existingUserProfile = existingUserOptional.get();
        existingUserProfile.setId(userId);
        existingUserProfile.setName(updateRequestDto.getName());
        existingUserProfile.setGender(updateRequestDto.getGender());
        existingUserProfile.setAge(updateRequestDto.getAge());
        existingUserProfile.setAddress(updateRequestDto.getAddress());
        existingUserProfile.setImage(updateRequestDto.getImage());
        existingUserProfile.setContactInfo(updateRequestDto.getContactInfo());
        existingUserProfile.setIsVerified(true);
        return existingUserProfile;
    }
}