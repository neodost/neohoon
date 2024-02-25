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
    <button on:click={authService.loadUser}>loadUser</button>
    <!--{#if store.auth?.authorities.filter(it => it.authority === 'ROLE_ADMINISTRATOR').length ?? 0 > 0}-->
        <input type="text" name="" id="" bind:value={username}>
        <button on:click={switchUser}>switchUser</button>
    <!--{/if}-->
    <button on:click={exitSwitchExit}>switchExit</button>
</div>