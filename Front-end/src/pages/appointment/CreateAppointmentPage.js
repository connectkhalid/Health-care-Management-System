import React, { useEffect, useState } from "react";
import { sendGetRequest, sendPostRequest } from "../../apis/api";
import {
  GET_DOCTOR_PROFILE_BY_EMAIL_URL,
  GET_DOCTOR_PROFILE_BY_ID_URL,
  GET_TODAYS_SLOTS_URL,
  POST_CREATE_APPOINTMENT,
} from "../../utils/urls";
import { loadLocalStorage } from "../../utils/persistLocalStorage";
import { useNavigate } from "react-router-dom";

function CreateAppointmentPage() {
  // last part of the url is the doctor id
  const doctorId = window.location.pathname.split("/").pop();
  console.log(doctorId);

  const token = loadLocalStorage("token");
  var user = loadLocalStorage("user");

  const navigate = useNavigate();

  const [selectedDate, setSelectedDate] = useState(
    new Date().toISOString().slice(0, 10)
  );
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [slotList, setSlotList] = useState([]);
  const [selectedSlot, setSelectedSlot] = useState(null);
  const [appoinmentType, setAppoinmentType] = useState("OFFLINE");
  const [isError, setIsError] = useState(false);
  const [errormessage, setErrorMessage] = useState("");

  useEffect(() => {
    fetchDoctorDetails();
  }, []);

  useEffect(() => {
    fetchSlots();
  }, [selectedDate]);

  const fetchDoctorDetails = () => {
    sendGetRequest(GET_DOCTOR_PROFILE_BY_ID_URL + "/" + doctorId, token)
      .then((response) => {
        console.log(response);
        setSelectedDoctor(response.data);
      })
      .catch((error) => {
        console.log(error);
        // if failed to fetch doctor details, redirect to doctor list page
        window.location.href = "/doctor-list";
      });
  };

  const fetchSlots = () => {
    sendGetRequest(
      GET_TODAYS_SLOTS_URL + "/" + doctorId + "/" + selectedDate,
      token
    )
      .then((response) => {
        console.log(response);
        setSlotList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleSlotSelection = (slot) => {
    console.log(slot);
    if (!slot.isAvailable) {
      return;
    }
    if (selectedSlot && selectedSlot.slotId === slot.slotId) {
      setSelectedSlot(null);
    } else {
      setSelectedSlot(slot);
    }
  };

  const handleBookAppointment = () => {
    // check if there is already an appointment for the patient with the doctor
    // if yes, show error message
    // else, navigate to all appointments page
    setIsError(false);
    setErrorMessage("");
    if (selectedSlot === null || selectedSlot === undefined) {
      setIsError(true);
      setErrorMessage("Please Select A Slot");
      return;
    }
    const appointmentData = {
      doctorId: doctorId,
      slotId: selectedSlot?.slotId,
      appointmentType: appoinmentType,
      conferenceLink: "",
      startTime: selectedSlot.startTime,
      appointmentDate: selectedDate,
    };
    console.log(appointmentData);

    sendPostRequest(POST_CREATE_APPOINTMENT, appointmentData, token)
      .then((res) => {
        console.log(res);
        alert("Appointment created successfully")
        navigate('/patient-appointments')
      })
      .catch((err) => {
        console.log(err);
        setIsError(true)
        setErrorMessage(err?.response?.data)
      });
  };

  return (
    <>
      <section class="appoinment section">
        <div class="container">
          <div class="row">
            <div class="col-lg-4">
              <div class="mt-3">
                <div class="feature-icon mb-3">
                  <i class="icofont-support text-lg"></i>
                </div>
                <span class="h3">Call for an Emergency Service!</span>
                <h2 class="text-color mt-3">+84 789 1256 </h2>
              </div>
            </div>

            <div class="col-lg-8">
              <div class="appoinment-wrap mt-5 mt-lg-0 pl-lg-5">
                <h2 class="mb-2 title-color">Book an Appointment</h2>
                <h3 class="mb-4">
                  Please Confirm the Appointment Details and Proceed
                </h3>

                <div class="row">
                  <div class="col-lg-4">
                    <div class="form-group">
                      <label>Doctor</label>
                      <h3>{selectedDoctor?.name}</h3>
                    </div>
                  </div>
                  <div class="col-lg-4">
                    <div class="form-group">
                      <label>Department</label>
                      <h3>{selectedDoctor?.department}</h3>
                    </div>
                  </div>

                  <div class="col-lg-4">
                    <div class="form-group">
                      <label>Email Address</label>
                      <h3>{selectedDoctor?.email}</h3>
                    </div>
                  </div>
                </div>
                <hr />

                <div class="row">
                  <div class="col-lg-12">
                    <h3>Confirm Appointment Details</h3>
                  </div>
                </div>

                <div class="row">
                  <div class="col-lg-12">
                    <div class="form-group">
                      <label>Patient Name</label>
                      <input
                        type="text"
                        class="form-control"
                        placeholder="Full Name"
                        required
                        disabled
                        value={user?.name}
                      />
                    </div>
                  </div>

                  <div class="col-lg-6">
                    <div class="form-group">
                      <label>Appointment Date</label>
                      <input
                        type="date"
                        class="form-control"
                        required
                        name="date"
                        id="date"
                        min={new Date().toISOString().split("T")[0]}
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                      />
                    </div>
                  </div>

                  <div class="col-lg-6">
                    <div class="form-group">
                      <label>Appointment Type</label>
                      <select
                        className="form-control"
                        value={appoinmentType}
                        onChange={(e) => setAppoinmentType(e.target.value)}
                      >
                        <option value="OFFLINE">Offline</option>
                        <option value="ONLINE">Online</option>
                      </select>
                    </div>
                  </div>
                </div>

                <h3 className="">Available Slots</h3>
                <div className="row">
                  {slotList.length > 0 ? (
                    slotList.map((slot, index) => {
                      return (
                        <div
                          className="col-lg-4 col-md-6 col-sm-12"
                          key={index}
                        >
                          <div
                            className="card rounded border m-2 p-3"
                            onClick={() => handleSlotSelection(slot)}
                            style={{
                              cursor: slot.isAvailable
                                ? "pointer"
                                : "not-allowed",
                              backgroundColor:
                                selectedSlot &&
                                  selectedSlot.slotId === slot.slotId
                                  ? "lightblue"
                                  : "white",
                            }}
                          >
                            <h3 className="text-center">
                              <span
                                className="text-color-2"
                                style={{
                                  fontSize: "1rem",
                                }}
                              >
                                {slot?.startTime} - {slot?.endTime}
                              </span>
                              <br />
                              <span
                                style={{
                                  fontSize: "0.7rem",
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
                    })
                  ) : (
                    <h3 className="mx-3">
                      No Slots Created for {selectedDate}
                    </h3>
                  )}
                </div>


                {
                  isError &&
                  <div className="row">
                    <div className="col-lg-12">
                      <div className="alert alert-danger" role="alert">
                        {errormessage}
                      </div>
                    </div>
                  </div>
                }
                <button
                  class="btn btn-main btn-round-full"
                  onClick={(e) => handleBookAppointment(e)}
                >
                  Request Appointment <i class="icofont-simple-right ml-2"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}

export default CreateAppointmentPage;
