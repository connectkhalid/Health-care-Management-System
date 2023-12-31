import React from 'react'
import { aboutImages } from '../../utils/constants'

function HomePage() {
	return (
		<>
			<section className="banner">
				<div className="container">
					<div className="row">
						<div className="col-lg-6 col-md-12 col-xl-7">
							<div className="block">
								<div className="divider mb-3"></div>
								<span className="text-uppercase text-sm letter-spacing ">Total Health care solution</span>
								<h1 className="mb-3 mt-3">Your most trusted health partner</h1>

								<p className="mb-4 pr-5">The technology to connect, the expertise doctor you can trust,<br /> and the
									power to
									improve health.</p>
								{/* <div className="btn-container ">
									<a href="/" className="btn btn-main-2 btn-icon btn-round-full">
										Make an Appointment <i className="icofont-simple-right ml-2"></i></a>
								</div> */}
							</div>
						</div>
					</div>
				</div>
			</section>

			<section className="features">
				<div className="container">
					<div className="row">
						<div className="col-lg-12">
							<div className="feature-block d-lg-flex">
								<div className="feature-item mb-5 mb-lg-0">
									<div className="feature-icon mb-4">
										<i className="icofont-surgeon-alt"></i>
									</div>
									<span>24 Hours Service</span>
									<h4 className="mb-3">Online Appoinment</h4>
									<p className="mb-4">Get All time support for emergency.We have introduced the principle of family
										medicine.</p>
									{/* <a href="/" className="btn btn-main btn-round-full">Make an
										appoinment</a> */}
								</div>

								<div className="feature-item mb-5 mb-lg-0">
									<div className="feature-icon mb-4">
										<i className="icofont-ui-clock"></i>
									</div>
									<span>Timing schedule</span>
									<h4 className="mb-3">Working Hours</h4>
									<h4>24 Hours a Day</h4>
									<h4>7 days a Week</h4>
								</div>

								<div className="feature-item mb-5 mb-lg-0">
									<div className="feature-icon mb-4">
										<i className="icofont-support"></i>
									</div>
									<span>Emergency Cases</span>
									<h4 className="mb-3">1-800-700-6200</h4>
									<p>Get All time support for emergency.We have introduced the principle of family medicine.Get
										Conneted with us for any urgency .</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>

			<section className="section about">
				<div className="container">
					<div className="row align-items-center">
						<div className="col-lg-4 col-sm-6">
							<div className="about-img">
								<img src={aboutImages[0]} alt="" className="img-fluid" />
								<img src={aboutImages[1]} alt="" className="img-fluid mt-4" />
							</div>
						</div>
						<div className="col-lg-4 col-sm-6">
							<div className="about-img mt-4 mt-lg-0">
								<img src={aboutImages[2]} alt="" className="img-fluid" />
							</div>
						</div>
						<div className="col-lg-4">
							<div className="about-content pl-4 mt-4 mt-lg-0">
								<h2 className="title-color">Personal care <br />& healthy living</h2>
								<p className="mt-4 mb-5" style={{
									textAlign: 'justify',
								}}>We offer a wide range of medical services,
									including primary care, preventive
									care, acute care, chronic care, and
									specialized medical services.</p>
							</div>
						</div>
					</div>
				</div>
			</section>
		</>
	)
}

export default HomePage