import {type Auth, createAuth} from "$lib/store/auth/auth.svelte";
import api from '$lib/util/axios/axios-api';
import authApi from '$lib/util/axios/axios-auth';
import {alert} from "$lib/util/common-util";

const AUTH_SERVER: string = import.meta.env.VITE_AUTH_SERVER ?? '';
const ACCESS_TOKEN_NAME: string = 'accessToken';
const LOGOUT_URL: string = '/logout';
const LOGIN_URL: string = '/authenticate';
const AUTHENTICATION_REFRESH_URL: string = '/refresh';
const JOIN_URL: string = '/join';

const store = createAuth();

const logout = async (): Promise<null> => {
    return authApi.post(LOGOUT_URL)
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

    login: async function(loginId: string, password: string): Promise<void> {
        return authApi.post(LOGIN_URL, new URLSearchParams({
            loginId, password
        }))
            .then((response) => {
                this.accessToken = response.headers['authorization'];
                this.loadUser()
            })
            .catch((error) => {
                console.log(error);
                alert('asdf')
            })
    },

    logout: async (): Promise<null> => {
        return logout();
    },

    join: (loginId: string, password: string, firstName: string, lastName: string) => {
        authApi.post(JOIN_URL, {
            loginId,
            password,
            name: {
                firstName,
                lastName,    
            }
        })
            .then(() => {
                alert('가입되었습니다.')
            })
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
        return authApi.post(AUTHENTICATION_REFRESH_URL, null, {
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        })
            .then(response => {
                console.log("refreshed....");
                this.accessToken = response.headers['authorization'];
                return response.headers['authorization'];
            })
    },

    set accessToken(accessToken: string) {
        localStorage.setItem(ACCESS_TOKEN_NAME, accessToken);
    },

    get accessToken(): string | null {
        return localStorage.getItem(ACCESS_TOKEN_NAME);
    },

    clearAccessToken: () => {
        localStorage.removeItem(ACCESS_TOKEN_NAME);
    }
}