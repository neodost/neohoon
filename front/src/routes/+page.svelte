<script>

    import authService from "$lib/service/auth-service.js";
    import {createAuth} from "$lib/store/auth.svelte.js";

    let authStore = createAuth();

    const handleLogout = () => {
        authService.logout()
    }

    const loginByOAuth = (provider) => {
        authService.loginByOAuth(provider)
    }

</script>

<main>

    {#if !!authStore.auth}
        <div>

            logged in : {authStore.auth.id}

        </div>
        <div>

            roles: {authStore.auth.authorities}

        </div>

        <button on:click={handleLogout}>Logout</button>

    {:else}
        <label >
            username: <input type="text" value={authStore.auth?.id}>
        </label>

        <div>
            <button on:click={() => {loginByOAuth('kakao')}}>kakao</button>
            <button on:click={() => {loginByOAuth('naver')}}>naver</button>
        </div>
    {/if}

</main>