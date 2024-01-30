<script>

    import {page} from "$app/stores";
    import {goto} from "$app/navigation";
    import {onMount} from "svelte";
    import authService from "$lib/service/auth-service.js";


    const setUserLogin = (accessToken) => {
        authService.saveAccessToken(accessToken)
        authService.loadUser()
    }

    onMount(() => {
        setUserLogin($page.url.searchParams.get('accessToken'))
        goto('/')
    })

</script>