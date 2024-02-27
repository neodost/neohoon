import axios from "axios";
import authService from "$lib/service/auth/auth-service";

const authApi = axios.create({
    baseURL: `/auth/v1`,
    withCredentials: true,
});

authApi.interceptors.response.use(
    (response) => {
        const authorization: string = response.headers['authorization'];
        if (authorization) {
            authService.accessToken = authorization;
        }
        return response;
    },
    async (error) => {
        return Promise.reject(error);
    }
)

export default authApi;