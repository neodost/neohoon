import {createAuth} from "$lib/store/auth/auth.svelte";
import api from '$lib/util/axios';
import axios from "axios";

const API_SERVER: string = import.meta.env.VITE_AUTH_SERVER ?? '';
const ACCESS_TOKEN_NAME: string = 'accessToken';

let store = createAuth();

const logout = () => {
    localStorage.removeItem(ACCESS_TOKEN_NAME);
    store.auth = null;
}

export default {
    loginByOAuth: (provider: string): void => {
        location.href = `${API_SERVER}/oauth2/authorization/${provider}`
    },

    logout: (): void => {
        localStorage.removeItem(ACCESS_TOKEN_NAME);
        store.auth = null;
    },

    loadUser: async (): Promise<any> => {
        if (!localStorage.getItem(ACCESS_TOKEN_NAME)) {
            return Promise.resolve();
        }

        try {
            const response = await api.get('/member/me');
            store.auth = {...response.data};
            return response.data;
        } catch (e) {
            console.error(e);
            logout();
        }
    },

    refreshAccessToken: async function(error: any): Promise<any> {
        let accessToken: string | null  = this.accessToken;
        if (!localStorage.getItem(ACCESS_TOKEN_NAME)) {
            logout();
            return Promise.reject(error);
        }
        return axios.post('/api/v1/authenticate/refresh', null, {
            headers: {
                'Authorization' : `Bearer ${accessToken}`
            }
        })
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    this.accessToken = response.headers['authorization'];
                    Promise.resolve();
                } else {
                    logout()
                    Promise.reject(error);
                }
            })
    },

    set accessToken(accessToken: string) {
        localStorage.setItem('accessToken', accessToken);
    },

    get accessToken(): string | null {
        return localStorage.getItem('accessToken');
    }
}