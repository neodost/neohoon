<script lang="ts">
    import authService from "$lib/service/auth/auth-service.js";
    import {createAuth} from "$lib/store/auth/auth.svelte.js";
    import {useQueryClient} from "@tanstack/svelte-query";
    import {AUTH_CHECK} from "$lib/constant/query-key/auth";
    import HamburgerOpener from "$lib/component/button/HamburgerOpener.svelte";

    let authStore = createAuth();
    let queryClient = useQueryClient();
    let opened = $state(false);

    const handleLogout = () => {
        authService.logout()
            .then(() => {
                queryClient.invalidateQueries({
                    queryKey: [AUTH_CHECK]
                })
            })
    }

</script>

<nav class:opened={opened} >
    <div>
        <span>
            logged in : {authStore.auth.username}
        </span>
        <wbr>
        <span>
            authorities: {authStore.auth.authorities.map(it => it.authority).join(',')}
        </span>
        <button onclick={handleLogout}>Logout</button>
    </div>
</nav>

<div class="hamburger-opener-wrapper">
    <HamburgerOpener opened={opened} size={30} onclick={(e) => {
            opened = !opened;
        }} />
</div>

<style>
    nav {
        position: relative;
        border: 1px solid;
        justify-content: space-between;
        height: 80px;
        overflow: hidden;
        &:not(.opened) {
            top: -80px;
            border: none;
        }
    }
    .hamburger-opener-wrapper {
        position: fixed;
        top: 10px;
        right: 10px;
    }
</style>