<script lang="ts">
    import authService from "$lib/service/auth/auth-service.js";
    import {createAuth} from "$lib/store/auth/auth.svelte.js";
    import {useQueryClient} from "@tanstack/svelte-query";
    import {AUTH_CHECK} from "$lib/constant/query-key/auth";

    let authStore = createAuth();
    let queryClient = useQueryClient();

    const handleLogout = () => {
        authService.logout()
            .then(() => {
                queryClient.invalidateQueries({
                    queryKey: [AUTH_CHECK]
                })
            })
    }

</script>

<main>

    {#if !!authStore.auth}
        <div>
            logged in : {authStore.auth.username}
        </div>
        <div>
            authorities: {authStore.auth.authorities.map(it => it.authority).join(',')}
        </div>
    {/if}
    <button on:click={handleLogout}>Logout</button>
    <a href="/join">join</a>
    <a href="/test">test</a>

</main>