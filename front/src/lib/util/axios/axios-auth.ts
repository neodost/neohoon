import axios from "axios";
import authService from "$lib/service/auth/auth-service";
import {alert} from "$lib/util/common-util";

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
        if (error.config.ignoreResponseReject) {
            return;
        }
        switch (error.response.status) {
            case 400:
                alert('' + error.response.data.message);
                break;
            case 401:
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

export default authApi;