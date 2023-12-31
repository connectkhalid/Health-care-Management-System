import React, { useState } from 'react'
import { articlePlaceholder, userPlaceholder } from '../../utils/constants'
import { loadLocalStorage } from '../../utils/persistLocalStorage'

function DoctorProfilePage() {
	const token = loadLocalStorage('token')
	var user = loadLocalStorage('user')

	const [articles, setArticles] = useState([])
	const [completedAppointments, setCompletedAppointments] = useState([])

	return (
		<div>
			<section class="section doctor-single">
				<div class="container">
					<div class="row">
						<div class="col-lg-4 col-md-6">
							<div class="doctor-img-block">
								<img src={userPlaceholder} alt="{user?.name}" class="img-fluid w-100" height="350px"
									width="100px" />



								<div class="info-block mt-4">
									<p class="mb-1">
										<span class="h3">{user?.name}</span>
										{" "}
										<span class="h5">({user?.gender})</span>
									</p>

									<p class="h4">Department:
										<span className='text-color-2'>{user?.department}</span>
									</p>
									<p class="h5">Email:
										<span class="text-color-2"> {user?.email}</span>
									</p>

									<div class="text-center">
										<a href="{% url 'doctor-edit-profile' %}" class="btn btn-main-2 btn-round-full mt-3">
											<i class="far fa-edit"></i> Update Profile
										</a>
									</div>
								</div>
							</div>
						</div>

						<div class="col-lg-8 col-md-6">
							<div class="doctor-details mt-4 mt-lg-0">
								<h2 class="text-md">About myself</h2>
								<div class="divider my-4"></div>
								<p>
									Dr. {user?.name} is an accomplished medical professional specializing in {user?.department}. With over {Math.floor(Math.random() * 5) + 5} years of experience, Dr. {user?.name} has been providing comprehensive medical services to patients.
									<br />
									Throughout the career, Dr. {user?.name} has received many Awards and Recognitions for outstanding achievements. {user?.name} actively contributes to research and has published impactful papers in renowned journals.
									<br />
									With a dedication to excellence, Dr. {user?.name} combines knowledge, experience, and compassion to deliver outstanding medical services.
								</p>

								<hr />

								<h2 class="text-md">Stats</h2>
								<div class="divider my-4"></div>
								<div class="row">
									<div class="col-lg-12">
										<h4>
											<b>{completedAppointments.length}</b> Completed Appointment(s)
										</h4>
										<h4>
											<b>{articles.length}</b> Articles Posted
										</h4>
									</div>
								</div>

								<hr />
								<a href="/" class="btn btn-main btn-round-full mt-3">
									Make an Appointment With Me <i class="icofont-simple-right"></i>
								</a>

								<hr />
								{/* <button class="btn btn-main-2 btn-round-full mt-3" disabled>
									Make an Appointment With Me
									<i class="icofont-simple-right ml-2"></i>
								</button>
								<h6 class="text-danger m-2">You have a pending appointment with me</h6> */}
							</div>
						</div>
					</div>
					<hr />

					<h3 class="my-3">Articles</h3>

					<h4 class="my-3">No activities yet</h4>

					<div class="row">
						{
							articles.map((article) => {
								return (
									<div class="col-lg-6 col-md-6">
										<div class="doctor-img-block">
											<img src={articlePlaceholder} alt="{{ article.title }}" class="img-fluid w-100" />

											<div class="info-block mt-4">

												<div class="blog-item-meta mb-3 mt-4">

													<span class="text-black text-capitalize mr-3">
														<i class="icofont-calendar mr-1"></i> {article?.created_at}
													</span>

													<a href="{% url 'doctor-profile' article.author.id %}" class="text-capitalize mr-3">
														<i class="fa fa-user mr-2"></i> {article?.author?.name}
													</a>
												</div>

												<p class="mb-1">
													<span class="h3">{article?.title}</span>
												</p>

												<div class="text-center">
													<a class="btn btn-main btn-round-full my-3" href="{% url 'article-details' article.slug %}">
														Read More <i class="icofont-simple-right ml-2"></i>
													</a>
												</div>
											</div>
										</div>
									</div>
								)
							})}
					</div>

					<hr />

					<h3 class="">Completed Appointments</h3>

					<h3 class="mx-3">No Completed Appointments</h3>

					<div class="row">
						{
							completedAppointments.map((appointment) => {
								return (
									<div class="col-lg-4">
										<div class="card rounded border p-3">
											<h4>Patient: <span class="text-color-2">
												{appointment.patient.user.name}
											</span>
											</h4>
											<h5>Date <span class="text-color-2">{appointment?.date}</span></h5>
											<a href="{% url 'history-home' appointment.patient.user.id %}" class="btn btn-main my-2">View
												Patient's
												History</a>
										</div>
									</div>
								)
							})}
					</div>
				</div>
			</section >
		</div >
	)
}

export default DoctorProfilePage