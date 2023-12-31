import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import AuthLayout from "./layouts/AuthLayout";
import HomePage from "./pages/main/HomePage";
import PatientSignUpPage from "./pages/auth/PatientSignUpPage";
import DoctorSignUpPage from "./pages/auth/DoctorSignUpPage";
import PatientLoginPage from "./pages/auth/PatientLoginPage";
import DoctorLoginPage from "./pages/auth/DoctorLoginPage";
import DoctorDashboardPage from "./pages/doctor/DoctorDashboardPage";
import DoctorProfilePage from "./pages/doctor/DoctorProfilePage";
import PatientDashboardPage from "./pages/patient/PatientDashboardPage";
import PatientProfilePage from "./pages/patient/PatientProfilePage";
import CommunityPage from "./pages/community/CommunityPage";
import CommunityPostCreatePage from "./pages/community/CommunityPostCreatePage";
import CommunityPostDetailsPage from "./pages/community/CommunityPostDetailsPage";
import PublicLayout from "./layouts/PublicLayout";
import PharmacyPage from "./pages/pharmacy/PharmacyPage";
import DoctorUpdateProfilePage from "./pages/doctor/DoctorUpdateProfilePage";
import PatientUpdateProfilePage from "./pages/patient/PatientUpdateProfilePage";
import LogoutPage from "./pages/auth/LogoutPage";
import DoctorListPage from "./pages/appointment/DoctorListPage";
import CreateAppointmentPage from "./pages/appointment/CreateAppointmentPage";
import PatientAppointmentPage from "./pages/appointment/PatientAppointmentPage";
import DoctorAppointmentPage from "./pages/appointment/DoctorAppointmentPage";
import PatientAppointmentDetailsPage from "./pages/appointment/PatientAppointmentDetailsPage";
import DoctorLayout from "./layouts/DoctorLayout";
import PatientLayout from "./layouts/PatientLayout";
import DoctorAppointmentDetailsPage from "./pages/appointment/DoctorAppointmentDetailsPage";
import Telemedicine from "./pages/appointment/Telemedicine";

function App() {
  return (
    <div className="wrapper">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<PublicLayout />}>
            <Route path="/" element={<HomePage />} />

            <Route path="/community" element={<CommunityPage />} />
            <Route
              path="/community/:id"
              element={<CommunityPostDetailsPage />}
            />

            <Route path="/pharmacy" element={<PharmacyPage />} />
          </Route>

          <Route path="/" element={<DoctorLayout />}>
            <Route path="/doctor-dashboard" element={<DoctorDashboardPage />} />
            <Route path="/doctor-profile" element={<DoctorProfilePage />} />
            <Route
              path="/doctor-profile/update"
              element={<DoctorUpdateProfilePage />}
            />

            <Route
            path="/doctor-appointments"
            element={<DoctorAppointmentPage/>}
            />
            <Route
              path="/doctor-appointment-details/:appointment_id"
              element={<DoctorAppointmentDetailsPage />}
            />
          </Route>
          
          <Route path="/" element={<PatientLayout />}>
            <Route
              path="/patient-dashboard"
              element={<PatientDashboardPage />}
            />
            <Route path="/patient-profile" element={<PatientProfilePage />} />
            <Route
              path="/patient-profile/update"
              element={<PatientUpdateProfilePage />}
            />

            <Route
              path="/community/create"
              element={<CommunityPostCreatePage />}
            />
            <Route path="/doctor-list" element={<DoctorListPage />} />
            <Route
              path="/create-appointment/:doctor_id"
              element={<CreateAppointmentPage />}
            />
            <Route
            path="/patient-appointments"
            element={<PatientAppointmentPage/>}
            />
            <Route
              path="/patient-appointment-details/:appointment_id"
              element={<PatientAppointmentDetailsPage />}
            />
          </Route>

          <Route path="/telemedicine/:room_id" element={<Telemedicine />} />

          {/* Auth */}
          <Route path="/auth" element={<AuthLayout />}>
            <Route
              path="/auth/patient-sign-up"
              element={<PatientSignUpPage />}
            />
            <Route path="/auth/doctor-sign-up" element={<DoctorSignUpPage />} />
            <Route path="/auth/patient-log-in" element={<PatientLoginPage />} />
            <Route path="/auth/doctor-log-in" element={<DoctorLoginPage />} />
            <Route path="/auth/logout" element={<LogoutPage />} />
          </Route>
            <Route path="/*" element={<div>
              <h1>
                404 Not Found
              </h1>
            </div>} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
