<script>

    import {createAuth} from "$lib/store/auth.svelte.js";
    import authService from "$lib/service/auth-service.js";

    let store = createAuth();

</script>

<div>
    <span>Hi?</span>
    <span>{store.auth?.id}</span>
    <a href="/">asdf</a>
    <button on:click={authService.loadUser}>loadUser</button>
</div>