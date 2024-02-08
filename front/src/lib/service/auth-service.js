import {createAuth} from "$lib/store/auth.svelte.js";
import client from "$lib/util/axios.js";

const API_SERVER = import.meta.env.VITE_API_SERVER ?? '';

let store = createAuth();

const logout = () => {
    localStorage.removeItem('accessToken')
    // auth.set(null);
    store.auth = null;
}

export default {
    loginByOAuth: (provider) => {
        location.href = `${API_SERVER}/oauth2/authorization/${provider}`
    },

    logout: () => {
        localStorage.removeItem('accessToken')
        store.auth = null;
    },

    loadUser: () => {
        if (!localStorage.getItem('accessToken')) {
            return Promise.resolve()
        }

        return client.get('/member/me')
            .then(response => {
                store.auth = {...response.data};
                return response.data;
            })
            .catch((e) => {
                console.log(e);
                logout();
            })
    },

    set accessToken(accessToken) {
        localStorage.setItem('accessToken', accessToken);
    },

    get accessToken() {
        return localStorage.getItem('accessToken');
    }

}