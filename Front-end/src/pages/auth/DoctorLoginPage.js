import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { DOCTOR_LOGIN_URL } from '../../utils/urls'
import { sendAuthRequest } from '../../apis/api'
import { saveLocalStorage } from '../../utils/persistLocalStorage'

function DoctorLoginPage() {
	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')
	const [loading, setLoading] = useState(false)
	const [isError, setIsError] = useState(false)
	const [errorMessage, setErrorMessage] = useState('')

	const navigate = useNavigate();

	const handleSubmit = (e) => {
		e.preventDefault()

		setIsError(false)
		setErrorMessage('')
		setLoading(true)

		const user = {
			email: email,
			password: password
		}

		sendAuthRequest(DOCTOR_LOGIN_URL, user)
			.then(res => {
				console.log(res);
				setLoading(false)
				setEmail('')
				setPassword('')
				saveLocalStorage("token", res.data['Authorization']);
				navigate("/doctor-dashboard");
			})
			.catch(err => {
				setLoading(false)
				console.log(err?.response);
				console.log(err?.response?.status);
				setErrorMessage(err?.response?.data?.detail)
				setIsError(true)
			});
	}

	return (
		<section className="contact-form-wrap section">
			<div className="container">
				<div className="row justify-content-center">
					<div className="col-lg-6">
						<div className="section-title text-center">
							<h2 className="text-md mb-2">Welcome back, Doctor!</h2>
							<div className="divider mx-auto my-4"></div>
							<p className="mb-5">Login to your account..</p>
						</div>
					</div>
				</div>
				<div className="row">
					<div className="col-lg-12 col-md-12 col-sm-12">
						<form onSubmit={handleSubmit}>

							<div className="row">
								<div className="col-lg-12 col-md-12 col-sm-12">
									<div className="form-group">
										<label>Email Address</label>
										<input type="text" className="form-control" placeholder="Email Address" value={email} onChange={(e) => setEmail(e.target.value)} required />
									</div>
								</div>
							</div>

							<div className="row">
								<div className="col-lg-12 col-md-12 col-sm-12">
									<div className="form-group">
										<label>Password</label>
										<input type="password" className="form-control" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
									</div>
								</div>
							</div>

							<div className="row">
								<div className="col-lg-12 col-md-12 col-sm-12">
									{isError && <span className="text-danger">{errorMessage}</span>}
								</div>
							</div>

							<div className="text-center">
								<button
									className="btn btn-main btn-round-full"
									type="submit"
									disabled={loading}
								>
									Log In <i className="fas fa-sign-in-alt"></i>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	)
}

export default DoctorLoginPage