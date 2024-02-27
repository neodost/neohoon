import authApi from '$lib/util/axios/axios-auth';
import authService from "$lib/service/auth/auth-service";

export default {

    switch: (username: string) => {
        authApi.post('/switch', new URLSearchParams({
            username
        }))
            .then((response) => {
                console.log('response', response)
                console.log('success')
                authService.loadUser()
                    .catch((e: Error) => {
                        console.error(e);
                    })
            });
    },

    exitSwitch: () => {
        authApi.post('/switch/exit')
            .then(() => {
                console.log('exit success')
                authService.loadUser()
                    .catch((e: Error) => {
                        console.error(e);
                    })
            })
    }

}