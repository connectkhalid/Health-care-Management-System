import React, { useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import Footer from '../components/Footer'
import Header from '../components/Header'
import { loadLocalStorage, saveLocalStorage } from '../utils/persistLocalStorage'
import { sendGetRequest } from '../apis/api'
import { GET_DOCTOR_PROFILE_BY_TOKEN_URL } from '../utils/urls'

function AuthLayout() {
	const token = loadLocalStorage('token')
	var user = loadLocalStorage('user')

	useEffect(() => {
		if (token) {
			if (!user) {
				getUserData();
			}
			window.location.href = '/'
		}
	}, [])

	const getUserData = () => {
		sendGetRequest(GET_DOCTOR_PROFILE_BY_TOKEN_URL, token)
			.then(response => {
				console.log(response)
				user = response.data
				saveLocalStorage('user', user)
			})
			.catch(error => {
				console.log(error)
			})
	}

	return (
		<>
			<Header />
			<div className="hold-transition login-page">
				<Outlet />
			</div>
			<Footer />
		</>
	)
}

export default AuthLayout