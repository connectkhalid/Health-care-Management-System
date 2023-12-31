import React, { useEffect, useState } from "react";
import {
  loadLocalStorage,
  saveLocalStorage,
} from "../../utils/persistLocalStorage";
import { sendGetRequest } from "../../apis/api";
import { GET_CURRENT_APPOINTMENT_OF_PATIENT_URL, GET_PATIENT_PROFILE_BY_TOKEN_URL } from "../../utils/urls";
import { userPlaceholder } from "../../utils/constants";

function PatientDashboardPage() {
  const token = loadLocalStorage("token");
  var user = loadLocalStorage("user");

  const [todaysAppointments, setTodaysAppointments] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!token) {
      window.location.href = "/";
    }
    if (!user) {
      getUserData();
    }
    setIsLoading(false);
    getCurrentAppointments();
  }, []);

  const getUserData = () => {
    sendGetRequest(GET_PATIENT_PROFILE_BY_TOKEN_URL, token)
      .then((response) => {
        console.log(response);
        user = response.data;
        saveLocalStorage("user", user);
        setIsLoading(false);
      })
      .catch((error) => {
        console.log(error);
        setIsLoading(false);
      });
  };

  const getCurrentAppointments = () => {
    sendGetRequest(GET_CURRENT_APPOINTMENT_OF_PATIENT_URL, token)
      .then(response => {
        console.log(response)
        setTodaysAppointments(response.data)
      })
      .catch(error => {
        console.log(error)
      })
  };

  return (
    <section className="section">
      {isLoading ? (
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-lg-6">
              <div className="section-title text-center">
                <h2 className="text-md mb-2">Loading...</h2>
                <div className="divider mx-auto my-4"></div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div className="container">
          <div className="row">
            <div className="col-lg-4 col-md-6">
              <div className="doctor-img-block">
                <img
                  src={userPlaceholder}
                  alt={user?.name}
                  className="img-fluid"
                  style={{
                    height: "60%",
                    width: "60%",
                    objectFit: "cover",
                  }}
                />
              </div>
            </div>
            <div className="col-lg-4 col-md-6">
              <div className="doctor-img-block">
                <div className="info-block">
                  <p className="mb-1">
                    <span className="h3">{user?.name}</span>
                    <span className="h5">({user?.gender})</span>
                  </p>
                  <p className="h4 text-color-2">{user?.email}</p>
                  {user?.age && (
                    <p className="h4 text-color-2">{user?.age} years old</p>
                  )}
                  {user?.contactInfo && (
                    <p className="h4 text-color-2">
                      Phone: {user?.contactInfo}
                    </p>
                  )}
                  <a href="/patient-profile" className="btn btn-main">
                    View Profile <i className="icofont-rounded-right"></i>
                  </a>
                </div>
              </div>
            </div>
            <div className="col-lg-4 col-md-6">
              <div className="text-center"></div>
            </div>
          </div>
          <hr className="mt-5 mb-5" />

          <div class="container">
            <div class="row">
              <div class="col-lg-8">
                <div class="cta-content">
                  <div class="divider mb-4"></div>
                  <h2 class="mb-5 text-lg">
                    We are pleased to offer you the{" "}
                    <span class="title-color">
                      chance to have a healthy life
                    </span>
                  </h2>
                </div>
              </div>
              <div class="col-lg-4">
                <div
                  class="cta-content text-center"
                  style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    height: "100%",
                  }}
                >
                  <a href="/doctor-list" class="btn btn-main-2 btn-round-full">
                    Book an appointment<i class="icofont-simple-right ml-2"></i>
                  </a>
                </div>
              </div>
            </div>
          </div>
          <hr className="mt-5 mb-5" />

          {/* Appointments */}
          <div className="container">
            <h2 className="">Todays Appointments</h2>

            {todaysAppointments.length > 0 ? (
              <div className="row">
                {todaysAppointments.map((appointment) => {
                  return (
                    <div className="col-lg-4">
                      <div className="card rounded border p-2">
                        <h3>
                          Appointment with{" "}
                          <span className="text-color-2">
                            {appointment?.doctorName}
                          </span>
                        </h3>

                        <h4>
                          On{" "}
                          <span className="text-color-2">
                            {appointment?.appointmentDate}
                          </span>
                        </h4>
                        <h4>
                          From{" "}
                          <span className="text-color-2">
                            {appointment.startTime}
                          </span>
                          {" To "}
                          <span className="text-color-2">
                            {appointment.endTime}
                          </span>
                        </h4>
                        <a
                          href={`patient-appointment-details/${appointment.appointmentId}`}
                          className="btn btn-main my-2"
                        >
                          See Details
                        </a>
                      </div>
                    </div>
                  );
                })}
              </div>
            ) : (
              <h3 className="mx-3">No Appointments Today</h3>
            )}
          </div>
          <hr className="mt-5 mb-5" />
        </div>
      )}
    </section>
  );
}

export default PatientDashboardPage;
