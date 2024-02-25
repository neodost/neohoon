import api from "$lib/util/axios";
import authService from "$lib/service/auth/auth-service";

export default {

    switch: (username: string) => {
        api.post('/authenticate/switch', new URLSearchParams({
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
        api.post('/authenticate/switch/exit')
            .then(() => {
                console.log('exit success')
                authService.loadUser()
                    .catch((e: Error) => {
                        console.error(e);
                    })
            })
    }

}