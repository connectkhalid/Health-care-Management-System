import React, { useEffect, useState } from "react";
import {
  loadLocalStorage,
  saveLocalStorage,
} from "../../utils/persistLocalStorage";
import { sendGetRequest, sendPostRequest } from "../../apis/api";
import {
  CHANGE_STATUS_OF_DOCTOR_URL,
  CREATE_SLOTS_URL,
  GET_CURRENT_APPOINTMENTS_BY_DOCTOR_ID_URL,
  GET_DOCTOR_PROFILE_BY_TOKEN_URL,
  GET_TODAYS_SLOTS_URL,
} from "../../utils/urls";
import { userPlaceholder } from "../../utils/constants";

function DoctorDashboardPage() {
  const token = loadLocalStorage("token");
  var user = loadLocalStorage("user");

  const [todaysAppointments, setTodaysAppointments] = useState([]);
  const [todaysSlots, setTodaysSlots] = useState([]);
  const [slotInputs, setSlotInputs] = useState({
    slotDate: "",
    duration: "",
    startTime: "",
  });
  const [slotCreationSuccess, setSlotCreationSuccess] = useState(null)
  const [slotCreationError, setSlotCreationError] = useState(null);
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
    getTodaysSlots();
  }, []);

  const handleInputChange = (e) => {
    setSlotInputs({
      ...slotInputs,
      [e.target.name]: e.target.value,
    });
  };

  const getUserData = () => {
    sendGetRequest(GET_DOCTOR_PROFILE_BY_TOKEN_URL, token)
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

  const handleToggleAvailability = (e) => {
    sendPostRequest(CHANGE_STATUS_OF_DOCTOR_URL , {}, token)
    .then((response) => {
      console.log(response);
      getUserData();
    })
    .catch((error) => {
      console.log(error);
    });
  }

  const getCurrentAppointments = () => {
    sendGetRequest(GET_CURRENT_APPOINTMENTS_BY_DOCTOR_ID_URL, token)
      .then((response) => {
        console.log(response);
        setTodaysAppointments(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const getTodaysSlots = () => {
    sendGetRequest(
      GET_TODAYS_SLOTS_URL +
      "/" +
      user?.doctor_id,
      token
    )
      .then((response) => {
        console.log(response);
        setTodaysSlots(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleCreateSlots = (e) => {
    e.preventDefault();

    setSlotCreationError("");

    sendPostRequest(CREATE_SLOTS_URL, slotInputs, token)
      .then((response) => {
        console.log(response);
        setSlotInputs({
          slotDate: "",
          duration: "",
          startTime: "",
        });
        setSlotCreationSuccess("Slots have been created successfully.")
      })
      .catch((error) => {
        console.log(error);
        setSlotCreationError(error?.response?.data);
      });
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
                  <p className="h4 text-color-2">
                    Department: {user?.department}
                  </p>
                  <p className="h4 text-color">
                    Allocated Room: {user?.allocated_room}
                  </p>
                  <a href="/doctor-profile" className="btn btn-main">
                    View Profile <i className="icofont-rounded-right"></i>
                  </a>
                </div>
              </div>
            </div>
            <div className="col-lg-4 col-md-6">
              <div className="text-center">
                <h3>
                  Status:{" "}
                  {user?.isAvailable ? (
                    <span className="text-success">Available</span>
                  ) : (
                    <span className="text-danger">Unavailable</span>
                  )}
                </h3>
              </div>
              <div className="text-center">
                {user?.isAvailable ? (
                  <button className="btn btn-danger"
                    onClick={(e) => handleToggleAvailability(e)}
                  >Make Unavailable</button>
                ) : (
                  <button className="btn btn-success"
                    onClick={(e) => handleToggleAvailability(e)}
                  >Make Available</button>
                )}
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
                            {appointment?.patientName}
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
                          href={`doctor-appointment-details/${appointment.appointmentId}`}
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

          {/* Create Slots */}
          <form className="container mb-5" onSubmit={handleCreateSlots}>
            <h2 className="">Slots</h2>
            <h4>Create Slots</h4>

            <div className="row">
              <div className="col-lg-4">
                <div className="form-group">
                  <label htmlFor="date">Date</label>
                  <input
                    type="date"
                    className="form-control"
                    id="slotDate"
                    name="slotDate"
                    value={slotInputs.slotDate}
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label htmlFor="date">Total Duration</label>
                  <input
                    type="number"
                    className="form-control"
                    id="duration"
                    name="duration"
                    value={slotInputs.duration}
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>
              <div className="col-lg-4">
                <div className="form-group">
                  <label htmlFor="date">Start Time</label>
                  <input
                    type="time"
                    className="form-control"
                    id="startTime"
                    name="startTime"
                    value={slotInputs.startTime}
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>
            </div>
            
            {
              slotCreationError &&
              <div className="row">
                <div className="col-lg-12">
                  <div className="alert alert-danger" role="alert">
                    {slotCreationError}
                  </div>
                </div>
              </div>
            }
            {slotCreationSuccess &&
              <div className="row">
                <div className="col-lg-12">
                  <div className="alert alert-success" role="alert">
                    {slotCreationSuccess}
                  </div>
                </div>
              </div>
            }
            
            <div className="text-center">
              <button className="btn btn-main" type="submit">
                Create Slots
              </button>
              {slotCreationError && (
                <p className="text-danger">{slotCreationError}</p>
              )}
            </div>
          </form>
          <hr className="mt-5 mb-5" />

          {/* Slots */}
          <div className="container">
            <h2 className="">
              Todays Slots{" "}
              <span className="text-color-2">({todaysSlots.length})</span>
            </h2>

            {todaysSlots.length > 0 ? (
              <div className="row">
                {todaysSlots.map((slot) => {
                  return (
                    <div className="col-lg-4 col-md-6 col-sm-12">
                      <div className="card rounded border m-2 p-3">
                        <h3 className="text-center">
                          <span className="text-color-2">
                            {slot?.startTime} - {slot?.endTime}
                          </span>
                          <br />
                          <span
                            style={{
                              fontSize: "1rem",
                            }}
                          >
                            {slot.isAvailable ? (
                              <span className="p-1 rounded badge-success">
                                Available
                              </span>
                            ) : (
                              <span className="p-1 rounded badge-secondary">
                                Unavailable
                              </span>
                            )}
                          </span>
                        </h3>
                      </div>
                    </div>
                  );
                })}
              </div>
            ) : (
              <h3 className="mx-3">No Slots Created for Today</h3>
            )}
          </div>
          <hr className="mt-5 mb-5" />
        </div>
      )}
    </section>
  );
}

export default DoctorDashboardPage;
