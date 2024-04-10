<script lang="ts">
    import {page} from "$app/stores";
    import authService from "$lib/service/auth/auth-service.js";
    import {goto} from "$app/navigation";

    const setUserLogin = (accessToken: string) => {
        authService.accessToken = accessToken;
        authService.loadUser()
            .catch(e => {
                console.error(e);
            })
    }

    $effect(() => {
        let accessToken: string | null = $page.url.searchParams.get('accessToken');
        if (accessToken) {
            setUserLogin(accessToken)
            goto('/')
        }
    })

</script>