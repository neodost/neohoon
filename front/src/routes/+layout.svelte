<script>

    import authService from "$lib/service/auth-service.js";

    let initialized = $state(false);

    $effect(() => {
        if (!initialized) {
            authService.loadUser()
                .then(() => {
                    initialized = true;
                })
        }
    })

</script>

{#if initialized}
    <slot />
{/if}
