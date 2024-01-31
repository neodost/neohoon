import axios from "axios";

const client = axios.create({});

client.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);