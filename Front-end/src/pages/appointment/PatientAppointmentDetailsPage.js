import React, { useEffect, useState } from 'react'
import { loadLocalStorage, saveLocalStorage } from '../../utils/persistLocalStorage';
import { sendGetRequest } from '../../apis/api';
import { GET_APPOINTMENT_BY_APPOINTMENT_ID } from '../../utils/urls';

function PatientAppointmentDetailsPage() {
    const appointmentId = window.location.pathname.split("/")[2];

    const token = loadLocalStorage("token");
    var user = loadLocalStorage("user");

    const [appointment, setAppointment] = useState({});
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (!token) {
            window.location.href = "/";
        }
        getAppointmentDetails();
        setIsLoading(false);
    }, []);

    const getAppointmentDetails = () => {
        sendGetRequest(GET_APPOINTMENT_BY_APPOINTMENT_ID + "/" + appointmentId, token)
            .then(response => {
                console.log(response)
                setAppointment(response.data)
            })
            .catch(error => {
                console.log(error)
            })
    }

    return (
        <div>
            <section class="page-title bg-1">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="block text-center">
                                <h1 class="text-capitalize mb-5 text-lg">Appointment Details</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="section">
                <div class="container">
                    <div class="row mb-3">
                        <div class="col-lg-12">
                            <h2>Appointment Status: <span class="text-success">{appointment?.status}</span>
                            </h2>
                            <hr />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-6">
                            <h3>Appointment Details</h3>
                                    <h3>Doctor:{" "}
                                        <a href="/" class="text-color-2">
                                            {appointment?.doctorName}
                                        </a>
                                    </h3>
                            <h3 class="my-3">Date: <span class="text-color-2">{appointment?.appointmentDate}</span></h3>
                            <h3 class="my-3">Time: <span class="text-color-2">
                                {appointment?.startTime} - {appointment?.endTime}
                            </span>
                            </h3>
                            {appointment?.conferenceLink !== "" && (
                                <h3 class="my-3">Conference Link:{" "}
                                    <a href={appointment?.conferenceLink} target="_blank" class="text-color-2">
                                        {appointment?.conferenceLink}
                                    </a>
                                </h3>
                            )
                            }
                        </div>
                        <div class="col-lg-6">
                            {appointment?.appointmentType === "ONLINE" && (
                                <a className='btn btn-main btn-round-full mt-3' href='/telemedicine/12345'>
                                    Join Meeting
                                </a>
                            )
                            }
                        </div>
                    </div>
                    <hr />
                </div>
            </section>
        </div>
    )
}

export default PatientAppointmentDetailsPage