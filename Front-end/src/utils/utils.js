import { deleteLocalStorage } from "./persistLocalStorage";

export const capitalize = (str) => {
	return str.charAt(0).toUpperCase() + str.slice(1);
};

export const formatDateTime = (dateTime) => {
	// July 16, 2023 at 12:00 PM
	const date = new Date(dateTime);
	const options = {
		year: "numeric",
		month: "long",
		day: "numeric",
		hour: "numeric",
		minute: "numeric",
		hour12: true,
	};
	return date.toLocaleString("en-US", options);
};

export const isAuthenticated = (user, tokens) => {
	if (user && tokens) {
		return true;
	}
	return false;
};

export const isAuthorized = (error) => {
	if (error.response.status === 401) {
		return false;
	}
	return true;
};

export const notFound = (error) => {
	if (error.response.status === 404) {
		return true;
	}
	return false;
};

export const logout = (navigate, currentUrl) => {
	deleteLocalStorage("user");
	deleteLocalStorage("tokens");
	navigate("/auth/login");
};
