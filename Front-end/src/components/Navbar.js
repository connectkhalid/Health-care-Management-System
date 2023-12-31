import React from 'react'
import { logoImg } from '../utils/constants'

function Navbar({ user }) {
	return (
		<nav className="navbar navbar-expand-lg navigation" id="navbar">
			<div className="container">
				<a className="navbar-brand" href="/">
					<h1><img src={logoImg} style={{
						height: '50px',
					}} alt="" className="img-fluid" />&nbsp;Care &
						Cure
					</h1>
				</a>

				<button className="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarmain"
					aria-controls="navbarmain" aria-expanded="false" aria-label="Toggle navigation">
					<span className="icofont-navigation-menu"></span>
				</button>

				<div className="collapse navbar-collapse" id="navbarmain">
					<ul className="navbar-nav ml-auto">
						<li className="nav-item active">
							{
								user?.role === "DOCTOR" ? (
									<a className="nav-link" href="/doctor-dashboard">Dashboard</a>
								) : user?.role === "PATIENT" ? (
									<a className="nav-link" href="/patient-dashboard">Dashboard</a>
								) : (
									<a className="nav-link" href="/">Home</a>
								)
							}
						</li>
						<li className="nav-item">
							<a className="nav-link" href="/community">Community</a>
						</li>

						{user &&
							user?.role === "DOCTOR" ? (
							<a className="nav-link" href="/doctor-appointments">Appointments</a>
						) : user?.role === "PATIENT" ? (
							<a className="nav-link" href="/patient-appointments">Appointments</a>
						) : null
						}

						<li className="nav-item">
							<a className="nav-link" href="/pharmacy">Pharmacy</a>
						</li>

						{user ?
							<li className="nav-item dropdown">
								<a className="nav-link dropdown-toggle" href="#" id="dropdown03" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">{user?.name} <i
										className="icofont-thin-down"></i></a>
								<ul className="dropdown-menu" aria-labelledby="dropdown03">
									{
										user?.role === "DOCTOR" ? (
											<li>
												<a className="dropdown-item" href="/doctor-profile">
													<i className="fas fa-user-md"></i> Profile</a>
											</li>
										) : (
											<li>
												<a className="dropdown-item" href="/patient-profile">
													<i className="fas fa-user-injured"></i> Profile</a>
											</li>
										)
									}
									<li><a className="dropdown-item" href="/auth/logout">
										<i className="fas fa-sign-out-alt"></i> Logout</a></li>
								</ul>
							</li>
							: (
								<>
									<li className="nav-item dropdown">
										<a className="nav-link dropdown-toggle" href="#" id="dropdown05" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">Log In <i className="icofont-thin-down"></i></a>
										<ul className="dropdown-menu" aria-labelledby="dropdown05">
											<li><a className="dropdown-item" href="/auth/doctor-log-in">
												<i className="fas fa-user-md"></i> Doctor Log In</a></li>
											<li><a className="dropdown-item" href="/auth/patient-log-in">
												<i className="fas fa-user-injured"></i> Patient Log In</a></li>
										</ul>
									</li>
									<li className="nav-item dropdown">
										<a className="nav-link dropdown-toggle" href="#" id="dropdown05" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">Sign Up <i className="icofont-thin-down"></i></a>
										<ul className="dropdown-menu" aria-labelledby="dropdown05">
											<li><a className="dropdown-item" href="/auth/doctor-sign-up">
												<i className="fas fa-user-md"></i> Doctor Sign Up</a></li>
											<li><a className="dropdown-item" href="/auth/patient-sign-up">
												<i className="fas fa-user-injured"></i> Patient Sign Up</a></li>
										</ul>
									</li>
								</>
							)
						}
					</ul>
				</div>
			</div>
		</nav>
	)
}

export default Navbar
