import React, { useState } from 'react'
import { PATIENT_SIGNUP_URL } from '../../utils/urls'
import { sendAuthRequest } from '../../apis/api'
import { useNavigate } from 'react-router-dom'

function PatientSignUpPage() {
	const navigate = useNavigate()

	const [inputs, setInputs] = useState({
		name: '',
		email: '',
		password: '',
		role: 'PATIENT'
	})

	const handleChange = (e) => {
		const { name, value } = e.target
		setInputs(inputs => ({ ...inputs, [name]: value }))
	}

	const handleSubmit = (e) => {
		sendAuthRequest(PATIENT_SIGNUP_URL, inputs)
			.then(response => {
				console.log(response)
				navigate('/patient/login')
			})
			.catch(error => {
				console.log(error)
			})
		e.preventDefault()
	}

	return (
		<div>
			<section className="contact-form-wrap section">
				<div className="container">
					<div className="row justify-content-center">
						<div className="col-lg-6">
							<div className="section-title text-center">
								<h2 className="text-md mb-2">Sign Up as Patient</h2>
								<div className="divider mx-auto my-4"></div>
							</div>
						</div>
					</div>
					<div className="row">
						<div className="col-lg-12 col-md-12 col-sm-12">
							<form onSubmit={(e) => handleSubmit(e)}>
								<div className="row">
									<div className="col-lg-12">
										<div className="form-group">
											<label htmlFor='name'>Full Name</label>
											<input
												type="text"
												name="name"
												placeholder='Full Name'
												value={inputs.name}
												onChange={handleChange}
												className="form-control"
												required
											/>
										</div>
									</div>
								</div>
								<div className="row">
									<div className="col-lg-12">
										<div className="form-group">
											<label htmlFor='email'>Email Address</label>
											<input
												type="email"
												name="email"
												placeholder='Email Address'
												value={inputs.email}
												onChange={handleChange}
												className="form-control"
												required
											/>
										</div>
									</div>
								</div>
								<div className="row">
									<div className="col-lg-12">
										<div className="form-group">
											<label htmlFor='password'>Password</label>
											<input
												type="password"
												name="password1"
												placeholder='Password'
												value={inputs.password}
												onChange={handleChange}
												className="form-control"
												required
											/>
										</div>
									</div>
								</div>

								<div className="text-center">
									<button className="btn btn-main btn-round-full" type="submit">
										Sign Up as a Patient <i className="button__icon fa fa-user-plus"></i>
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
		</div>
	)
}

export default PatientSignUpPage