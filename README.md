# Health-care-Management-System
Documentation for Online Book Library Application

Contact: Mohammad Khalid Hasan
Email: connectkhalid404@gmail.com
Version: 1.0
## Introduction

HMS is a Healthcare Management System that streamlines healthcare services and the community effectively. It optimizes appointment scheduling and personalized patient health recommendations, enhancing overall community engagement effectively.

![image](https://github.com/connectkhalid/Health-care-Management-System/assets/116859905/c5666b87-4ff4-44a0-81b4-ea75ce7e3aa3)

## User Profile Service

1. **Register Patient**
   - Method: POST
   - Endpoint: `/patient/register`
   - Purpose: Registers a new patient.

2. **Update Patient Profile**
   - Method: PUT
   - Endpoint: `/patient/update-patient-profile`
   - Purpose: Updates the profile of a patient.

3. **Get Patient Profile by Token**
   - Method: GET
   - Endpoint: `/patient/get-patient-profile-by-token`
   - Purpose: Retrieves the patient profile using the authentication token.

4. **Get Patient Profile by ID**
   - Method: GET
   - Endpoint: `/patient/get-patient-profile-by-id/{userId}`
   - Purpose: Retrieves the patient profile by user ID.

5. **Get Patient by Email**
   - Method: GET
   - Endpoint: `/patient/get-patient-by-email/{email}`
   - Purpose: Retrieves patient information by email.

6. **Create Health Data**
   - Method: POST
   - Endpoint: `/patient/create-health-data`
   - Purpose: Creates health data for a patient.

7. **Get Health Data by Token**
   - Method: GET
   - Endpoint: `/patient/get-health-data-by-token`
   - Purpose: Retrieves health data for the authenticated patient.

8. **Update Patient Health Data**
   - Method: PUT
   - Endpoint: `/patient/update-patient-health-data`
   - Purpose: Updates health data for a patient.

9. **Send Health Data**
   - Method: GET
   - Endpoint: `/patient/health-data`
   - Purpose: Sends health data.

10. **Get All Health Data**
    - Method: GET
    - Endpoint: `/patient/get-all-health-data`
    - Purpose: Retrieves all health data.

## Doctor Service

1. **Register Doctor**
   - Method: POST
   - Endpoint: `/doctor/register`
   - Purpose: Registers a new doctor.

2. **Update Doctor Profile**
   - Method: PUT
   - Endpoint: `/doctor/update`
   - Purpose: Updates the profile of a doctor.

3. **Verify Doctor**
   - Method: POST
   - Endpoint: `/doctor/verify/{doctorId}`
   - Purpose: Verifies a doctor by their ID.

4. **Disable Doctor**
   - Method: POST
   - Endpoint: `/doctor/disable-doctor/{doctorId}`
   - Purpose: Disables a doctor by their ID.

5. **Change Availability Status**
   - Method: POST
   - Endpoint: `/doctor/change-availability-status`
   - Purpose: Changes the availability status of a doctor.

6. **Get Doctor by Email**
   - Method: GET
   - Endpoint: `/doctor/get-by-email/{email}`
   - Purpose: Retrieves doctor information by email.

7. **Get Doctor Profile by Token**
   - Method: GET
   - Endpoint: `/doctor/get-doctor-profile-by-token`
   - Purpose: Retrieves the profile of the authenticated doctor.

8. **Get Doctor Profile by ID**
   - Method: GET
   - Endpoint: `/doctor/get-doctor-profile-by-id/{doctorId}`
   - Purpose: Retrieves the profile of a doctor by ID.

9. **Get All Available Doctors**
   - Method: GET
   - Endpoint: `/doctor/get-all-available-doctor`
   - Purpose: Retrieves a list of all available doctors.

10. **Get All Doctors**
    - Method: GET
    - Endpoint: `/doctor/get-all-doctor`
    - Purpose: Retrieves a list of all doctors.

11. **Get All Doctors by Department**
    - Method: GET
    - Endpoint: `/doctor/get-all-doctor-by-department/{department}`
    - Purpose: Retrieves a list of doctors in the specified department.

12. **Create Appointment Slots**
    - Method: POST
    - Endpoint: `/doctor/create-slots`
    - Purpose: Creates appointment slots for a doctor.

13. **Get All Available Slots for Doctor**
    - Method: GET
    - Endpoint: `/doctor/get-all-available-slot-doctor/{doctorId}`
    - Purpose: Retrieves all available slots for a doctor.

14. **Get Current Slots for Doctor**
    - Method: GET
    - Endpoint: `/doctor/get-current-slot-doctor/{doctorId}`
    - Purpose: Retrieves slots for the current date for a doctor.

15. **Get Available Slots by Date**
    - Method: GET
    - Endpoint: `/doctor/get-available-slot-by-date/{doctorId}/{slotDate}`
    - Purpose: Retrieves available slots for a specific date for a doctor.

16. **Book Appointment Slot**
    - Method: POST
    - Endpoint: `/doctor/book-appointment-slot/{slotId}`
    - Purpose: Books an appointment slot by slot ID.

17. **Create Doctor Review**
    - Method: POST
    - Endpoint: `/doctor/create-doctor-review`
    - Purpose: Creates a review for a doctor.

18. **Get All Reviews for Doctor**
    - Method: GET
    - Endpoint: `/doctor/get-all-reviews-doctor/{doctorId}`
    - Purpose: Retrieves all reviews for a doctor.

## Appointment Service

1.  **Create Appointment**
    -   Method: POST
    -   Endpoint: **/appointment/create-appointment**
    -   Purpose: Creates a new appointment.

2.  **Cancel Appointment**
    -   Method: POST
    -   Endpoint: **/appointment/cancel/{appointmentId}**
    -   Purpose: Cancels an existing appointment.

3.  **Complete Appointment**
    -   Method: POST
    -   Endpoint: **/appointment/complete/{appointmentId}**
    -   Purpose: Marks an appointment as completed.

4.  **Get Appointment by Appointment ID**
    -   Method: GET
    -   Endpoint:
        **/appointment/get-appointment-by-appointmentId/{appointmentId}**
    -   Purpose: Retrieves appointment information by appointment ID.

5.  **Get Patient Current Appointments**
    -   Method: GET
    -   Endpoint: **/appointment/get-current-appointments-patient**
    -   Purpose: Retrieves current appointments for a patient.

6.  **Get Patient Upcoming Appointments**
    -   Method: GET
    -   Endpoint: **/appointment/get-upcoming-appointments-patient**
    -   Purpose: Retrieves upcoming appointments for a patient.

7.  **Get Patient Appointment History**
    -   Method: GET
    -   Endpoint: **/appointment/patient-appointment-history**
    -   Purpose: Retrieves the appointment history for a patient.

8.  **Get Doctor Current Appointments**
    -   Method: GET
    -   Endpoint: **/appointment/get-current-appointments-doctor**
    -   Purpose: Retrieves current appointments for a doctor.

9.  **Get Doctor Upcoming Appointments**
    -   Method GET
    -   Endpoint: **/appointment/get-upcoming-appointments-doctor**
    -   Purpose: Retrieves upcoming appointments for a doctor.

10. **Doctor Appointment History**
    -   Method: GET
    -   Endpoint: **/appointment/doctor-appointment-history**
    -   Purpose: Retrieves the appointment history for a doctor.

**Community Service:**

1.  **Create Comment**
    -   Method: POST
    -   Endpoint: **/comment/createComment**
    -   Purpose: Creates a new comment.

2.  **Get All Comments**
    -   Method: GET
    -   Endpoint: **/comment/getAllComments/{postId}**
    -   Purpose: Retrieves all comments for a post.

3.  **Create Post**
    -   Method: POST
    -   Endpoint: **/post/createPost**
    -   Purpose: Creates a new post.

4.  **Update Post**
    -   Method: PUT
    -   Endpoint: **/post/updatePost/{postId}**
    -   Purpose: Updates an existing post.

5.  **Get Post by ID**
    -   Method: GET
    -   Endpoint: **/post/{postId}**
    -   Purpose: Retrieves a post by its ID.

6.  **Get All Posts**
    -   Method: GET
    -   Endpoint: **/post/allPost**
    -   Purpose: Retrieves all posts.

7.  **Cast Vote**
    -   Method: POST
    -   Endpoint: **/vote/cast**
    -   Purpose: Casts a vote.

8.  **Count Votes**
    -   Method: GET
    -   Endpoint: **/vote/count/{postId}**
    -   Purpose: Counts votes for a post.

**Analytics and Research Service:**

1.  **Register Researcher**
    -   Method: POST
    -   Endpoint: **/researchers/register**
    -   Purpose: Registers a new researcher.

2.  **Get All Researchers for Applied**
    -   Method: GET
    -   Endpoint: **/researchers/applied**
    -   Purpose: Retrieves all researchers for applied research.

3.  **Give Validation**
    -   Method: PUT
    -   Endpoint: **/researchers/permission/{id}**
    -   Purpose: Grants validation permission to a researcher.

4.  **Get All Researchers**
    -   Method: GET
    -   Endpoint: **/researchers/all**
    -   Purpose: Retrieves a list of all researchers.

5.  **Get All Researchers (Taken)**
    -   Method: GET
    -   Endpoint: **/researchers/taken**
    -   Purpose: Retrieves a list of researchers who have taken
        permission.

6.  **Get All Researchers (Not Taken)*
    -   Method: GET
    -   Endpoint: **/researchers/notTaken**
    -   Purpose: Retrieves a list of researchers who have not taken
        permission.

7.  **Write User Health Data to CSV**
    -   Method: GET
    -   Endpoint: **/researchers/getCsv/{id}**
    -   Purpose: Writes user health data to a CSV file.

**Medicine Service:**

1.  **Add Medicine**
    -   Method: POST
    -   Endpoint: **/medicines/add**
    -   Purpose: Adds a new medicine.

2.  **Update Medicine**
    -   Method: PUT
    -   Endpoint: **/medicines/update**
    -   Purpose: Updates an existing medicine.

3.  **Delete Medicine**
    -   Method: DELETE
    -   Endpoint: **/medicines/delete/{medicineName}**
    -   Purpose: Deletes a medicine by name.

4.  **Get All Medicines**
    -   Method: GET
    -   Endpoint: **/medicines/all-medicine**
    -   Purpose: Retrieves a list of all medicines.

5.  **Get Medicine by ID**
    -   Method: GET
    -   Endpoint: **/medicines/medicine-by-id/{id}**
    -   Purpose: Retrieves a medicine by its ID.

6.  **Get All Available Medicines**
    -   Method: GET
    -   Endpoint: **/medicines/all-available-medicine**
    -   Purpose: Retrieves a list of all available medicines.
