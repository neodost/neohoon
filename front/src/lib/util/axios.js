import axios from "axios";
import authService from "$lib/service/auth-service.js";

const client = axios.create({
    baseURL: `/api/v1`,
    credentials: true,
});

client.interceptors.request.use(
    (config) => {
        config.headers.Authorization = `Bearer ${authService.accessToken}`;
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

client.interceptors.response.use(
    (response) => {
        let authorization = response.headers.get('Authorization');
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

export default client;