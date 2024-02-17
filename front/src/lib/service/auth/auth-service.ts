import {createAuth} from "$lib/store/auth/auth.svelte";
import api from '$lib/util/axios';

const API_SERVER: string = import.meta.env.VITE_API_SERVER ?? '';
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

    set accessToken(accessToken: string) {
        localStorage.setItem('accessToken', accessToken);
    },

    get accessToken(): string | null {
        return localStorage.getItem('accessToken');
    }
}