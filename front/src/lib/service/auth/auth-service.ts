import {type Auth, createAuth} from "$lib/store/auth/auth.svelte";
import api from '$lib/util/axios';
import axios from "axios";

const AUTH_SERVER: string = import.meta.env.VITE_AUTH_SERVER ?? '';
const ACCESS_TOKEN_NAME: string = 'accessToken';

const store = createAuth();

const logout = async (): Promise<null> => {
    return axios.post('/api/v1/authenticate/logout')
        .then(() => {
            return null;
        })
        .finally(() => {
            localStorage.removeItem(ACCESS_TOKEN_NAME);
            store.auth = null;
        })
}

export default {
    loginByOAuth: (provider: string): void => {
        location.href = `${AUTH_SERVER}/oauth2/authorization/${provider}`
    },

    logout: async (): Promise<null> => {
        return logout();
    },

    loadUser: async (): Promise<Auth> => {
        if (!localStorage.getItem(ACCESS_TOKEN_NAME)) {
            return Promise.reject('accessToken is missing');
        }

        return api.get('/member/me')
            .then(response => {
                store.auth = {...response.data};
                return response.data;
            })
            .catch(async (e: Error) => {
                console.error(e);
                await logout();
                return Promise.reject(e);
            });
    },

    refreshAccessToken: async function(): Promise<string> {
        const accessToken: string | null = this.accessToken;
        if (!accessToken) {
            await logout();
            return Promise.reject('accessToken is missing');
        }
        return axios.post('/api/v1/authenticate/refresh', null, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
            .then(response => {
                this.accessToken = response.headers['authorization'];
                return response.headers['authorization'];
            })
    },

    set accessToken(accessToken: string) {
        localStorage.setItem('accessToken', accessToken);
    },

    get accessToken(): string | null {
        return localStorage.getItem('accessToken');
    }
}