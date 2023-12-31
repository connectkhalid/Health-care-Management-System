import React, { useEffect, useState } from 'react'
import { loadLocalStorage, saveLocalStorage } from '../../utils/persistLocalStorage';
import { sendGetRequest } from '../../apis/api';
import { GET_ALL_CANCELED_APPOINTMENT_PATIENT_URL, GET_ALL_COMPLETED_APPOINTMENT_PATIENT_URL, GET_CURRENT_APPOINTMENT_OF_PATIENT_URL, GET_PATIENT_PROFILE_BY_TOKEN_URL, GET_UPCOMING_APPOINTMENT_OF_PATIENT_URL } from '../../utils/urls';

function PatientAppointmentPage() {
	const token = loadLocalStorage("token");
	var user = loadLocalStorage("user");

	const [todaysAppointments, setTodaysAppointments] = useState([]);
	const [upcomingAppointments, setUpcomingAppointments] = useState([]);
	const [cancelledAppointments, setCancelledAppointments] = useState([]);
	const [completedAppointments, setCompletedAppointments] = useState([]);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		if (!token) {
			window.location.href = "/";
		}
		if (!user) {
			getUserData();
		}
		getCurrentAppointments();
		getUpcomgingAppointments();
		getCancelledAppointments();
		getCompletedAppointments();
		setIsLoading(false);
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

	const getUpcomgingAppointments = () => {
		sendGetRequest(GET_UPCOMING_APPOINTMENT_OF_PATIENT_URL, token)
			.then(response => {
				console.log(response)
				setUpcomingAppointments(response.data)
			})
			.catch(error => {
				console.log(error)
			})
	};

	const getCancelledAppointments = () => {
		sendGetRequest(GET_ALL_CANCELED_APPOINTMENT_PATIENT_URL, token)
			.then(response => {
				console.log(response)
				setCancelledAppointments(response.data)
			})
			.catch(error => {
				console.log(error)
			})

	}

	const getCompletedAppointments = () => {
		sendGetRequest(GET_ALL_COMPLETED_APPOINTMENT_PATIENT_URL, token)
			.then(response => {
				console.log(response)
				setCompletedAppointments(response.data)
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
								<h1 class="text-capitalize mb-5 text-lg">Appointments</h1>
							</div>
						</div>
					</div>
				</div>
			</section>

			<section class="section">
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
			</section>

			<section>
				<div class="row my-3">
					<div class="col-lg-4"></div>
					<div class="col-lg-4 text-center">
						<a href="doctor-list" class="btn btn-main-2 btn-icon btn-round-full">
							Make New Appointment <i class="icofont-simple-right ml-2"></i>
						</a>
					</div>
					<div class="col-lg-4"></div>
				</div>
			</section>

			<section class="section">
				<div className="container">
					<h2 className="">Upcoming Appointments</h2>

					{upcomingAppointments.length > 0 ? (
						<div className="row">
							{upcomingAppointments.map((appointment) => {
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
						<h3 className="mx-3">No Upcoming Appointments</h3>
					)}
				</div>
			</section>

			<section class="section"><div className="container">
				<h2 className="">Completed Appointments</h2>

				{completedAppointments.length > 0 ? (
					<div className="row">
						{completedAppointments.map((appointment) => {
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
											See More
										</a>
									</div>
								</div>
							);
						})}
					</div>
				) : (
					<h3 className="mx-3">No Completed Appointments</h3>
				)}
			</div>
			</section>

			<section class="section"><div className="container">
				<h2 className="">Cancelled  Appointments</h2>

				{cancelledAppointments.length > 0 ? (
					<div className="row">
						{cancelledAppointments.map((appointment) => {
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
											See More
										</a>
									</div>
								</div>
							);
						})}
					</div>
				) : (
					<h3 className="mx-3">No Cancelled Appointments</h3>
				)}
			</div>
			</section>

			<section class="section cta-page">
				<div class="container">
					<div class="row">
						<div class="col-lg-7">
							<div class="cta-content">
								<div class="divider mb-4"></div>
								<h2 class="mb-5 text-lg">We are pleased to offer you the <span class="title-color">chance to have a
									healthy life</span>
								</h2>
								<a href="/doctor-list" class="btn btn-main-2 btn-round-full">
									Get appointment<i class="icofont-simple-right ml-2"></i>
								</a>
							</div>
						</div>
					</div>
				</div>
			</section>

		</div>
	)
}

export default PatientAppointmentPage