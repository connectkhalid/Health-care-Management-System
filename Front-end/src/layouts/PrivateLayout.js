import React, { useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import { loadLocalStorage, saveLocalStorage } from '../utils/persistLocalStorage'
import { sendGetRequest } from '../apis/api'
import { GET_DOCTOR_PROFILE_BY_TOKEN_URL, GET_PATIENT_PROFILE_BY_TOKEN_URL } from '../utils/urls'

function PrivateLayout() {
  const token = loadLocalStorage('token')
  var user = loadLocalStorage('user')

  useEffect(() => {
    if (!token) {
      window.location.href = '/'
    } else {
      if (!user) {
        getDoctorProfileData();
        getPatientProfileData();
      }
    }
  }, [])

  const getDoctorProfileData = () => {
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

  const getPatientProfileData = () => {
    sendGetRequest(GET_PATIENT_PROFILE_BY_TOKEN_URL, token)
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
      <Header user={user} />
      <div className="hold-transition login-page">
        <Outlet />
      </div>
      <Footer />
    </>
  )
}

export default PrivateLayout