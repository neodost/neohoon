import axios from "axios";
import authService from "$lib/service/auth/auth-service";
import {alert} from "$lib/util/common-util";

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
        switch (error.response.status) {
            case 400:
                alert('' + error.response.data.message);
                break;
            case 401:
                return await authService.refreshAccessToken()
                    .then(() => api(originalRequest))
                    .catch(() => {
                        authService.clearAccessToken();
                        // window.location.href = '/';
                    })
            case 403:
                alert('권한이 없습니다.');
                break;
            case 404:
                alert('404 ');
                break;
            default:
                alert('서버 오류');
                break;
        }

        return Promise.reject(error);
    }
)

export default api;