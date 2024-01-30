import authService from "$lib/service/auth-service.js";

const API_SERVER = import.meta.env.VITE_API_SERVER;

function handleResponse(response) {
    const authorization = response.headers.get('Authorization');
    if (authorization) {
        authService.saveAccessToken(authorization)
    }
    if (response.ok) {
        if (isJson(response)) {
            return response.json()
        } else {
            return response.text()
        }
    }

    if (response.status === 401) {
        authService.logout();
    } else if (response.status === 403) {
        alert(403)
    }



    return response.err
        ? Promise.reject(response.err)
        : response.json().then(Promise.reject.bind(Promise));
}

function isJson(response) {
    const contentType = response.headers.get('Content-Type')
    if (contentType !== null) {
        return contentType.indexOf('application/json') > -1
    }
    return false
}

export const request = (uri, options) => {
    return fetch(`${API_SERVER}${uri}`, {
        ...options,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
        },
        credentials: 'include',
    })
        .then(response => handleResponse(response))
}