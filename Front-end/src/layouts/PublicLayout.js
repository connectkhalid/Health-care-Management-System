import React, { useEffect } from 'react'
import { Outlet } from 'react-router-dom'
import Header from '../components/Header'
import Footer from '../components/Footer'
import { loadLocalStorage, saveLocalStorage } from '../utils/persistLocalStorage'
import { GET_DOCTOR_PROFILE_BY_TOKEN_URL } from '../utils/urls'
import { sendGetRequest } from '../apis/api'

function PublicLayout() {
  const token = loadLocalStorage('token')
  var user = loadLocalStorage('user')

  useEffect(() => {
    if (token) {
      if (!user) {
        getUserData();
      }
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
      <Header user={user} />
      <div className="hold-transition">
        <Outlet />
      </div>
      <Footer />
    </>
  )
}

export default PublicLayout