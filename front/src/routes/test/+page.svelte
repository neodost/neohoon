<script lang="ts">
    import {createAuth} from "$lib/store/auth/auth.svelte.js";
    import authService from "$lib/service/auth/auth-service.js";
    import authMasterService from "$lib/service/auth/auth-master-service";

    let username = $state('189cfbed-b901-46b6-bf54-97d06a2d0116');

    let store = createAuth();

    function switchUser() {
        authMasterService.switch(username);
    }

    function exitSwitchExit() {
        authMasterService.exitSwitch();
    }

</script>

<div>
    <span>Hi?</span>
    <span>{store.auth?.username}</span>
    <a href="/">test</a>
    <button onclick={authService.loadUser}>loadUser</button>
    {#if store.auth?.authorities.filter(it => it.authority === 'ADMINISTRATOR').length ?? 0 > 0}
        <input type="text" name="" id="" bind:value={username}>
        <button onclick={switchUser}>switchUser</button>
    {/if}
    <button onclick={exitSwitchExit}>switchExit</button>
</div>