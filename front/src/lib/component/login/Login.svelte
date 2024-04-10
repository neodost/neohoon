<script lang="ts">
    import authService from "$lib/service/auth/auth-service";
    import {useQueryClient} from "@tanstack/svelte-query";
    import {AUTH_CHECK} from "$lib/constant/query-key/auth";

    const queryClient = useQueryClient();

    const login = $state({
        loginId: '',
        password: ''
    })

    const loginByOAuth = (provider: string) => {
        authService.loginByOAuth(provider)
    }

    const handleLogin = (e) => {
        e.preventDefault();
        authService.login(login.loginId, login.password)
            .then(() => {
                queryClient.invalidateQueries({
                    queryKey: [AUTH_CHECK],
                })
            })
    }

</script>

<main>
    <form action="" onsubmit={handleLogin}>
        <fieldset>
            <legend>login</legend>
            <label for="loginId">username</label>
            <input type="text" id="loginId" name="loginId" bind:value={login.loginId} /><br />

            <label for="password">password</label>
            <input type="password" id="password" name="password" bind:value={login.password} /><br />

            <button type="submit">login</button>
        </fieldset>
        <div>
            <button type="button" onclick={() => loginByOAuth('kakao')}>kakao</button>
            <button type="button" onclick={() => loginByOAuth('naver')}>naver</button>
        </div>
    </form>
</main>
