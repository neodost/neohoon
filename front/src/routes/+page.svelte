<script lang="ts">
    import authService from "$lib/service/auth/auth-service.js";
    import {createAuth} from "$lib/store/auth/auth.svelte.js";

    let authStore = createAuth();

    const handleLogout = () => {
        authService.logout()
    }

    const loginByOAuth = (provider: string) => {
        authService.loginByOAuth(provider)
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
    {:else}
        <div>
            <button on:click={() => {loginByOAuth('kakao')}}>kakao</button>
            <button on:click={() => {loginByOAuth('naver')}}>naver</button>
        </div>
    {/if}
    <button on:click={handleLogout}>Logout</button>
    <a href="/join">join</a>
    <a href="/test">test</a>

</main>