<script>

    import authService from "$lib/service/auth-service.js";
    import {onMount} from "svelte";

    $: initialized = false

    onMount(() => {
        authService.loadUser()
            .then(() => {
                initialized = true;
            })
    })

</script>

{#if initialized}
    <slot />
{/if}
