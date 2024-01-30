<script>

    import authService from "$lib/service/auth-service.js";
    import {request} from "$lib/request.js";
    import {auth} from "$lib/store/auth.js";

    export let username = 'kkkqwerasdf123@naver.com';
    export let password = '1234';

    $: loggedIn = !!$auth;

    const handleLogin = () => {

        if (!username.trim() || !password) {
            return
        }

        authService.login(username, password)
            .then(() => {
                authService.loadUser()
            })
            .catch(err => {
                console.log(err)
                if (err) {
                    alert(err.detail ?? 'Server Error ......')
                }
            })
    }

    const handleLogout = () => {
        authService.logout()
    }

    const authTest = () => {
        request('/api/v1/auth-check')
            .then((ee) => {
                console.log(ee)
                alert('OK!')
            })
            .catch(err => {
                console.log(err)
                if (err) {
                    alert(err.detail ?? 'Server Error ......')
                }
            })
    }

    const loginByOAuth = (provider) => {
        authService.loginByOAuth(provider)
    }

</script>

<main>

    {#if loggedIn}
        <div>

            logged in : {$auth.email}

        </div>
        <div>

            roles: {$auth.authorities}

        </div>

        <button on:click={handleLogout}>Logout</button>

    {:else}
        <label >
            username: <input type="text" bind:value={username}>
        </label>

        <label >
            password: <input type="password" bind:value={password}>
        </label>


        <button on:click={handleLogin}>Login</button>

        <div>
            <button on:click={() => {loginByOAuth('kakao')}}>kakao</button>
            <button on:click={() => {loginByOAuth('naver')}}>naver</button>
        </div>
    {/if}


    <div>
        <button on:click={authTest}>auth test</button>
    </div>


</main>