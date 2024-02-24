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
        const authorization: string = response.headers['authorization'];
        if (authorization) {
            authService.accessToken = authorization;
        }
        return response;
    },
    async (error) => {
        const originalRequest = error.config;
        if (error.response?.status) {
            switch (error.response.status) {
                case 400:
                    break;
                case 401:
                    return authService.refreshAccessToken()
                        .then(() => {
                            originalRequest._retry = true;
                            return api(originalRequest)
                        });
                case 403:
                    alert(403);
                    break;
            }
        }

        return Promise.reject(error);
    }
)

export default api;