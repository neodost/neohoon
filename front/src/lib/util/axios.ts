import axios from "axios";
import authService from "$lib/service/auth/auth-service";

const api = axios.create({
    baseURL: `/api/v1`,
    withCredentials: true,
});

api.interceptors.request.use(
    (config) => {
        config.headers.Authorization = `Bearer ${authService.accessToken}`;
        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => {
        let authorization: string = response.headers['authorization'];
        if (authorization) {
            authService.accessToken = authorization;
        }
        return response;
    },
    (error) => {
        if (error.response?.status) {
            switch (error.response.status) {
                case 400:
                    break;
                case 401:
                    authService.logout();
                    break;
                case 403:
                    alert(403);
                    break;
            }
        }

        return Promise.reject(error);
    }
)

export default api;