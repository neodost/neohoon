<script lang="ts">
    import authService from "$lib/service/auth/auth-service.js";

    let initialized = $state(false);

    $effect(() => {
        if (!initialized) {
            authService.loadUser()
                .finally(() => {
                    initialized = true;
                })
        }
    })

</script>

{#if initialized}
    <slot />
{/if}