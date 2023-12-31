import React, { useEffect } from 'react'

function LogoutPage() {
	useEffect(() => {
		localStorage.removeItem('token')
		localStorage.removeItem('user')
		window.location.href = '/'
	}, [])

	return (
		<div>
			<h1>
				Logging out...
			</h1>
		</div>
	)
}

export default LogoutPage