import axios from "axios";

export const sendAuthRequest = (url, data) => {
	return axios({
		method: 'POST',
		url: url,
		data: data,
		headers: {
			'Content-Type': 'application/json',
		}
	});
};

export const sendGetRequest = (url, accessToken) => {
	return axios({
		method: 'GET',
		url: url,
		headers: {
			'Content-Type': 'application/json',
			Authorization: accessToken
		}
	});
};
export const sendGetRequestWithData = (url, data, accessToken) => {
	console.log(data);
	return axios({
		method: 'GET',
		url: url,
		data: data,
		maxBodyLength: 5000,
		headers: {
			'Content-Type': 'application/json',
			Authorization: accessToken
		},
	});
};

export const sendPostRequest = (url, data, accessToken, hasFile = false) => {
	return axios({
		method: 'POST',
		url: url,
		data: data,
		headers: {
			'Content-Type': hasFile ? 'multipart/form-data' : 'application/json',
			Authorization: accessToken
		}
	});
};

export const sendPatchRequest = (url, data, accessToken, hasFile = false) => {
	return axios({
		method: 'PATCH',
		url: url,
		data: data,
		headers: {
			'Content-Type': hasFile ? 'multipart/form-data' : 'application/json',
			Authorization: `Bearer ${accessToken}`
		}
	});
};

export const sendDeleteRequest = (url, accessToken) => {
	return axios({
		method: 'DELETE',
		url: url,
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${accessToken}`
		}
	});
};
