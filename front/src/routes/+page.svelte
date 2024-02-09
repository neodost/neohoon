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
            logged in : {authStore.auth.id}
        </div>
        <div>
            roles: {authStore.auth.authorities.map(it => it.authority).join(',')}
        </div>
        <button on:click={handleLogout}>Logout</button>
    {:else}
        <div>
            <button on:click={() => {loginByOAuth('kakao')}}>kakao</button>
            <button on:click={() => {loginByOAuth('naver')}}>naver</button>
        </div>
    {/if}

    <a href="/test">test</a>

</main>