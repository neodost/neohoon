import {request} from "../request.js";
import {createAuth} from "$lib/store/auth.svelte.js";
const API_SERVER = import.meta.env.VITE_API_SERVER;

let auth = createAuth();

const logout = () => {
    localStorage.removeItem('accessToken')
    auth.set(null);
}

export default {
    loginByOAuth: (provider) => {
        location.href = `${API_SERVER}/oauth2/authorization/${provider}`
    },

    loadUser: () => {
        if (!localStorage.getItem('accessToken')) {
            return Promise.resolve()
        }

        return request('/api/v1/member/me')
            .then((response) => {
                auth.set({...response});
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