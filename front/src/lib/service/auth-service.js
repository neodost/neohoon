import {request} from "../request.js";
import {goto} from "$app/navigation";
import {auth} from "$lib/store/auth.js";
const API_SERVER = import.meta.env.VITE_API_SERVER;

const logout = () => {
    localStorage.removeItem('accessToken')
    auth.update(() => null)
}

export default {

    login: (username, password) => {
        return request('/api/v1/authenticate', {
            method: 'POST',
            body: new URLSearchParams({
                username, password
            })
        })
    },

    loginByOAuth: (provider) => {
        goto(`${API_SERVER}/oauth2/authorization/${provider}`)
    },

    loadUser: () => {
        if (!localStorage.getItem('accessToken')) {
            return Promise.resolve()
        }

        return request('/api/v1/member/me')
            .then((response) => {
                auth.update(() => ({...response}))
            })
            .catch(() => {
                logout();
            });
    },

    logout,

    saveAccessToken: (accessToken) => {
        localStorage.setItem('accessToken', accessToken);
    }

}