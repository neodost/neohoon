<script lang="ts">
    import {createAuth} from "$lib/store/auth/auth.svelte.js";
    import authService from "$lib/service/auth/auth-service.js";

    let store = createAuth();

</script>

<div>
    <span>Hi?</span>
    <span>{store.auth?.id}</span>
    <a href="/">test</a>
    <button on:click={authService.loadUser}>loadUser</button>
</div>