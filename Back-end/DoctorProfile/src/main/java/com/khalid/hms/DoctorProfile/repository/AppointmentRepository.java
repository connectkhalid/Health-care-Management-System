package com.khalid.hms.DoctorProfile.repository;

import com.khalid.hms.DoctorProfile.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, String> {
    //find appointments with same date time of patient
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patientId = :patientId " +
            "AND a.appointmentDate = :appointmentDate AND a.startTime = :startTime")
    Optional<AppointmentEntity> findByPatientIdAndAppointmentDateAndStartTime(
            @Param("patientId") String patientId,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("startTime") LocalTime startTime
    );
    AppointmentEntity findAppointmentEntityByPatientIdAndAppointmentDateAndStartTime(String patientId, LocalDate appointmentDate, LocalTime startTime);

    //for current appointment of patient
    Optional<List<AppointmentEntity> > findAllByPatientIdAndAppointmentDate(String patientId, LocalDate appointmentDate);
    //for upcoming appointments of patient
    List<AppointmentEntity> findByPatientIdAndAppointmentDateAfterOrderByAppointmentDateAscStartTimeAsc(String patientId, LocalDate currentDate);
    List<AppointmentEntity> findAllByPatientId(String patientId);
    List<AppointmentEntity> findAllByDoctorId(String doctorId);
    //for current appointment of patient
    List<AppointmentEntity> findAllByDoctorIdAndAndAppointmentDate(String doctorId, LocalDate appointmentDate);
    List<AppointmentEntity> findByDoctorIdAndAppointmentDateAfterOrderByAppointmentDateAscStartTimeAsc(String doctorId, LocalDate currentDate);
    AppointmentEntity findDistinctByPatientIdAndDoctorIdAndAppointmentDate(String patientId, String doctorId, LocalDate appointmentDate);
    //TO CHECK WEATHER THE PATIENT HAS COMPLETED AN APPOINTMENT WITH A PARTICULAR DOCTOR
//    @Query("SELECT a FROM AppointmentEntity a WHERE a.patientId = :patientId AND a.doctorId = :doctorId AND a.Status = 'COMPLETED'")
//    Optional<AppointmentEntity> findCompletedAppointmentByPatientAndDoctor(
//            @Param("patientId") String patientId,
//            @Param("doctorId") String doctorId
//    );
    AppointmentEntity findByPatientIdAndDoctorIdAndStatus(String patientId, String doctorId, String status);
    List<AppointmentEntity> findAllByPatientIdAndStatus(String patientId, String status);
    List<AppointmentEntity> findAllByDoctorIdAndStatus(String doctorId, String status);
}
