import React from 'react'
import { logoImg } from '../utils/constants'

function Footer() {
	return (
		<footer className="footer section gray-bg">
			<div className="container">
				<div className="row">
					<div className="col-lg-4 mr-auto col-sm-6">
						<div className="widget mb-5 mb-lg-0">
							<div className="logo mb-4">
								<h2><img src={logoImg} style={{
									height: '50px',
								}} alt="logo"
									className="img-fluid" />&nbsp;Care & Cure</h2>
							</div>
							<p>The technology to connect, the expertise doctor you can trust,
								and the power to improve health.</p>

							<ul className="list-inline footer-socials mt-4">
								<li className="list-inline-item"><a href="#"><i className="icofont-facebook"></i></a></li>
								<li className="list-inline-item"><a href="#"><i className="icofont-twitter"></i></a></li>
								<li className="list-inline-item"><a href="#"><i className="icofont-linkedin"></i></a></li>
							</ul>
						</div>
					</div>

					<div className="col-lg-2 col-md-6 col-sm-6">
						<div className="widget mb-5 mb-lg-0">
							<h4 className="text-capitalize mb-3">Services</h4>
							<div className="divider mb-4"></div>

							<ul className="list-unstyled footer-menu lh-35">
								<li>
									<a href="/community">Community</a>
								</li>
								<li>
									<a href="/pharmacy">Pharmacy</a>
								</li>
							</ul>
						</div>
					</div>

					<div className="col-lg-2 col-md-6 col-sm-6">
						<div className="widget mb-5 mb-lg-0">
							<h4 className="text-capitalize mb-3">Support</h4>
							<div className="divider mb-4"></div>

							<ul className="list-unstyled footer-menu lh-35">
								<li><a href="/">Terms & Conditions</a></li>
								<li><a href="/">Privacy Policy</a></li>
								<li><a href="/about">About Us </a></li>
							</ul>
						</div>
					</div>

					<div className="col-lg-3 col-md-6 col-sm-6">
						<div className="widget widget-contact mb-5 mb-lg-0">
							<h4 className="text-capitalize mb-3">Get in Touch</h4>
							<div className="divider mb-4"></div>

							<div className="footer-contact-block mb-4">
								<div className="icon d-flex align-items-center">
									<i className="icofont-email mr-3"></i>
									<span className="h6 mb-0">Support Available 24/7</span>
								</div>
								<h4 className="mt-2"><a href="mail:support@email.com">support@email.com</a></h4>
								<h4 className="mt-2"><a href="tel:+23-345-67890">+23-456-6588</a></h4>
							</div>
							<hr />

							<div className="footer-contact-block">
								<h4 className="mt-2"><a href="/contact">SEND US A MESSAGE</a></h4>
							</div>
						</div>
					</div>
				</div>

				<div className="footer-btm py-4 mt-5">
					<div className="row">
						<div className="col-lg-4">
							<a className="backtop js-scroll-trigger" href="#top">
								<i className="icofont-long-arrow-up"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
		</footer>
	)
}

export default Footer
