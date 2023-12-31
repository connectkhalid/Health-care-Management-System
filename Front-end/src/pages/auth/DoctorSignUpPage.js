import React, { useState } from 'react'
import { sendAuthRequest } from '../../apis/api'
import { DOCTOR_SIGNUP_URL } from '../../utils/urls'

function DoctorSignUpPage() {
	const [inputs, setInputs] = useState({
		name: '',
		email: '',
		password: '',
		gender: '',
		department: '',
		registration_number_BMDC: '',
		allocated_room: '',
		qualifications: [],
	})

	const handleChange = (e) => {
		const { name, value } = e.target
		if (name === 'qualifications') {
			const options = e.target.options;
			const value = [];
			for (let i = 0, l = options.length; i < l; i++) {
				if (options[i].selected) {
					value.push(options[i].value);
				}
			}
			setInputs(inputs => ({ ...inputs, [name]: value }))
		} else {
			setInputs(inputs => ({ ...inputs, [name]: value }))
		}
	}

	const handleSubmit = (e) => {
		sendAuthRequest(DOCTOR_SIGNUP_URL, inputs)
			.then(response => {
				console.log(response)
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
								<h2 className="text-md mb-2">Sign Up as Doctor</h2>
								<div className="divider mx-auto my-4"></div>
							</div>
						</div>
					</div>
					<div className="row">
						<div className="col-lg-12 col-md-12 col-sm-12">
							<form onSubmit={(e) => handleSubmit(e)}>
								<div className="row">
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor="name">
												Full Name
											</label>
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
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor="email">
												Email Address
											</label>
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
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor="password">
												Password
											</label>
											<input
												type="password"
												name="password"
												placeholder='Password'
												value={inputs.password}
												onChange={handleChange}
												className="form-control"
												required
											/>
										</div>
									</div>
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor='gender'>
												Gender
											</label>
											<select
												name='gender'
												onChange={handleChange}
												className="form-control"
												required
											>
												<option value=''>Select an option:</option>
												<option value="Male">Male</option>
												<option value="Female">Female</option>
											</select>
										</div>
									</div>
								</div>
								<div className="row">
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor='department'>
												Department
											</label>
											<select
												name='department'
												onChange={handleChange}
												className="form-control"
												required
											>
												<option value=''>Select an option:</option>
												<option value="Cardiology">Cardiology</option>
												<option value="Neurology">Neurology</option>
												<option value="Urology">Urology</option>
												<option value="Medicine">Medicine</option>
												<option value="Psychology">Psychology</option>
											</select>
										</div>
									</div>
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor="registration_number_BDMC">
												BMDC Registration Number
											</label>
											<input
												type="number"
												name="registration_number_BDMC"
												placeholder='BMDC Registration Number'
												onChange={handleChange}
												className="form-control"
												required
											/>
										</div>
									</div>
								</div>
								<div className="row">
									<div className="col-lg-6">
										<div className="form-group">
											<label htmlFor="allocated_room">
												Qualifications
											</label>
											<select
												name='qualifications'
												onChange={handleChange}
												className="form-control"
												style={{
													height: '100px'
												}}
												multiple
												required
											>
												<option value="MD">MD</option>
												<option value="MBBS">MBBS</option>
												<option value="MPHIL">Urology</option>
												<option value="Medicine">Medicine</option>
												<option value="Psychology">Psychology</option>
											</select>
											<small>
												Press Ctrl to select multiple options
											</small>
										</div>
									</div>
									<div className="col-lg-6">
									</div>
								</div>

								<div className="text-center">
									<button className="btn btn-main btn-round-full" type="submit">
										Sign Up as a Doctor <i className="button__icon fa fa-user-plus"></i>
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

export default DoctorSignUpPage