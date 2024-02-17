<script lang="ts">
    import {page} from "$app/stores";
    import {goto} from "$app/navigation";
    import {onMount} from "svelte";
    import authService from "$lib/service/auth/auth-service.js";

    const setUserLogin = (accessToken: string) => {
        authService.accessToken = accessToken;
        authService.loadUser()
    }

    onMount(() => {
        let accessToken: string | null = $page.url.searchParams.get('accessToken');
        if (!accessToken) {
            throw new Error('Access token is required');
        }
        setUserLogin(accessToken)
        goto('/')
    })

</script>