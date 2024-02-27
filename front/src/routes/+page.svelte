<script lang="ts">
    import authService from "$lib/service/auth/auth-service.js";
    import {createAuth} from "$lib/store/auth/auth.svelte.js";

    let authStore = createAuth();
    let loginId: string = $state('');
    let password: string = $state('');

    const handleLogout = () => {
        authService.logout()
    }

    const loginByOAuth = (provider: string) => {
        authService.loginByOAuth(provider)
    }

    const handleLogin = () => {
        authService.login(loginId, password)
            .then(response => {
                console.log(response);
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
    {:else}
        <div>
            <form on:submit={handleLogin}>
                <label>
                    <span>loginId</span>
                    <input type="text" name="" id="" bind:value={loginId}>
                </label>
                <label>
                    <span>password</span>
                    <input type="password" name="" id="" bind:value={password}>
                </label>
                <button type="submit">submit</button>
            </form>
        </div>
        <div>
            <button on:click={() => {loginByOAuth('kakao')}}>kakao</button>
            <button on:click={() => {loginByOAuth('naver')}}>naver</button>
        </div>
    {/if}
    <button on:click={handleLogout}>Logout</button>
    <a href="/join">join</a>
    <a href="/test">test</a>

</main>