import {createAuth} from "$lib/store/auth/auth.svelte";
import api from '$lib/util/axios';
import axios from "axios";

const AUTH_SERVER: string = import.meta.env.VITE_AUTH_SERVER ?? '';
const ACCESS_TOKEN_NAME: string = 'accessToken';

let store = createAuth();

const logout = async () => {
    return axios.post('/api/v1/authenticate/logout')
        .finally(() => {
            localStorage.removeItem(ACCESS_TOKEN_NAME);
            store.auth = null;
        })
}

export default {
    loginByOAuth: (provider: string): void => {
        location.href = `${AUTH_SERVER}/oauth2/authorization/${provider}`
    },

    logout: async (): Promise<any> => {
        return logout();
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
            await logout();
        }
    },

    refreshAccessToken: async function(error: any): Promise<any> {
        let accessToken: string | null  = this.accessToken;
        if (!localStorage.getItem(ACCESS_TOKEN_NAME)) {
            await logout();
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