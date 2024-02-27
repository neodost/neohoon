<script lang="ts">
    import authService from "$lib/service/auth/auth-service";

    let loginId = $state('');
    let password = $state('');
    let lastName = $state('');
    let firstName = $state('');

    const join = () => {
        authService.join(loginId, password, firstName, lastName);
    }

</script>

<div>
    <span>JOIN</span>

    <form on:submit={join}>
        <label>
            <span>lastName</span>
            <input type="text" bind:value={lastName} >
        </label>
        <label>
            <span>firstName</span>
            <input type="text" bind:value={firstName} >
        </label>
        <label>
            <span>loginId</span>
            <input type="text" bind:value={loginId} >
        </label>
        <label>
            <span>password</span>
            <input type="password" bind:value={password} >
        </label>
        <button type="submit">submit</button>
    </form>
    <a href="/">main</a>

</div>