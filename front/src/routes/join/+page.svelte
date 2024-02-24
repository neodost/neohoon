<script lang="ts">
    import api from "$lib/util/axios";

    let username = $state('');
    let password = $state('');

    const join = () => {
        api.post('/api/v1/member/join', {
            username,
            password
        })
            .then(response => {
                console.log(response);
            })
    }

</script>

<div>
    <span>JOIN</span>

    <form on:submit={join}>
        <label>
            <span>username</span>
            <input type="text" bind:value={username} >
        </label>
        <label>
            <span>password</span>
            <input type="password" bind:value={password} >
        </label>
        <button type="submit">submit</button>
    </form>
    <a href="/">main</a>

</div>