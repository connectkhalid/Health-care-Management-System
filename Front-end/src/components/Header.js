import React from 'react'
import Navbar from './Navbar'

function Header({ user }) {
	return (
		<header>
			<div className="header-top-bar">
				<div className="container">
					<div className="row align-items-center">
						<div className="col-lg-6">
							<ul className="top-bar-info list-inline-item pl-0 mb-0">
								<li className="list-inline-item"><a href="mailto:amic@gmail.com"><i
									className="icofont-support-faq mr-2"></i>careandcure@gmail.com</a></li>
								<li className="list-inline-item">
									<i className="icofont-location-pin mr-2"></i>Dhaka, Bangladesh
								</li>
							</ul>
						</div>
						<div className="col-lg-6">
							<div className="text-lg-right top-right-bar mt-2 mt-lg-0">
								<a href="tel:+8801812345678">
									<span>Call Now : </span>
									<span className="h4">+880 1812345678</span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>

			<Navbar user={user} />

		</header>
	)
}

export default Header