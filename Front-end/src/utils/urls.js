const PATIENT_SERVICE = "http://localhost:8030/";
export const PATIENT_LOGIN_URL = PATIENT_SERVICE + "patient/login";
export const PATIENT_SIGNUP_URL = PATIENT_SERVICE + "patient/register";
export const PATIENT_CREATE_PROFILE_URL =
  PATIENT_SERVICE + "patient/create-profile";
export const GET_PATIENT_PROFILE_BY_ID_URL =
  PATIENT_SERVICE + "patient/get-patient-profile-by-id";
export const GET_PATIENT_PROFILE_BY_EMAIL_URL =
  PATIENT_SERVICE + "patient/get-patient-by-email";
export const GET_PATIENT_PROFILE_BY_TOKEN_URL =
  PATIENT_SERVICE + "patient/get-patient-profile-by-token";

const DOCTOR_SERVICE = "http://localhost:8040/";
export const DOCTOR_LOGIN_URL = DOCTOR_SERVICE + "doctor/login";
export const DOCTOR_SIGNUP_URL = DOCTOR_SERVICE + "doctor/register";
export const DOCTOR_VERIFY_URL = DOCTOR_SERVICE + "doctor/verify";
export const DISABLE_DOCTOR_PROFILE_URL =
  DOCTOR_SERVICE + "doctor/disable-doctor";
export const TOGGLE_AVAILABILITY_URL =
  DOCTOR_SERVICE + "doctor/change-availability-status";
export const UPDATE_DOCTOR_PROFILE_URL = DOCTOR_SERVICE + "doctor/update";
export const GET_DOCTOR_PROFILE_BY_ID_URL =
  DOCTOR_SERVICE + "doctor/get-doctor-profile-by-id";
export const GET_DOCTOR_PROFILE_BY_EMAIL_URL =
  DOCTOR_SERVICE + "doctor/get-by-email";
export const GET_DOCTOR_PROFILE_BY_TOKEN_URL =
  DOCTOR_SERVICE + "doctor/get-doctor-profile-by-token";
export const GET_ALL_AVAILABLE_DOCTOR_URL =
  DOCTOR_SERVICE + "doctor/get-all-available-doctor";
export const GET_ALL_DOCTOR_BY_DEPARTMENT_URL =
  DOCTOR_SERVICE + "doctor/get-all-doctor-by-department";

export const CREATE_SLOTS_URL = DOCTOR_SERVICE + "doctor/create-slots";
export const GET_TODAYS_SLOTS_URL =
  DOCTOR_SERVICE + "doctor/get-available-slot-by-date";

export const POST_CREATE_APPOINTMENT =
  DOCTOR_SERVICE + "appointment/create-appointment";
export const GET_CURRENT_APPOINTMENTS_BY_DOCTOR_ID_URL =
  DOCTOR_SERVICE + "appointment/get-current-appointments-doctor";
export const GET_UPCOMING_APPOINTMENT_OF_DOCTOR_URL =
  DOCTOR_SERVICE + "appointment/get-upcoming-appointments-doctor";
export const GET_ALL_CANCELED_APPOINTMENT_DOCTOR_URL = 
DOCTOR_SERVICE + "appointment/all-cancelled-appointment-doctor";
export const GET_ALL_COMPLETED_APPOINTMENT_DOCTOR_URL = 
DOCTOR_SERVICE + "appointment/all-completed-appointment-doctor";
  
export const GET_APPOINTMENT_BY_APPOINTMENT_ID =
  DOCTOR_SERVICE + "appointment/get-appointment-by-appointmentId";
export const CANCEL_APPOINTMENT_BY_DOCTOR =
  DOCTOR_SERVICE + "appointment/cancel";
export const COMPLETE_APPOINTMENT_BY_DOCTOR =
  DOCTOR_SERVICE + "appointment/complete";


export const GET_CURRENT_APPOINTMENT_OF_PATIENT_URL =
  DOCTOR_SERVICE + "appointment/get-current-appointments-patient";
export const GET_UPCOMING_APPOINTMENT_OF_PATIENT_URL =
  DOCTOR_SERVICE + "appointment/get-upcoming-appointments-patient";
export const GET_ALL_CANCELED_APPOINTMENT_PATIENT_URL = 
DOCTOR_SERVICE + "appointment/all-cancelled-appointment-patient";
export const GET_ALL_COMPLETED_APPOINTMENT_PATIENT_URL = 
DOCTOR_SERVICE + "appointment/all-completed-appointment-patient";
  


export const CHANGE_STATUS_OF_DOCTOR_URL =
  DOCTOR_SERVICE + "doctor/change-availability-status";

const COMMUNITY_SERVICE = "http://localhost:8060/";
export const CREATE_POST_BY_PATIENT = COMMUNITY_SERVICE + "post/createPost";
export const GET_POST_BY_POST_ID = COMMUNITY_SERVICE + "post";
export const GET_ALL_POST = COMMUNITY_SERVICE + "post/allPost";
export const GET_ALL_COMMENT_BY_POST_ID =
  COMMUNITY_SERVICE + "comment/getAllComments";
export const CREATE_COMMENT = COMMUNITY_SERVICE + "comment/createComment";
const PHARMACY_SERVICE = "http://localhost:8070/";
export const CREATE_MEDICINE = PHARMACY_SERVICE + "medicines/add";
export const GET_ALL_AVAILABLE_MEDICINE =
  PHARMACY_SERVICE + "medicines/all-available-medicine";
