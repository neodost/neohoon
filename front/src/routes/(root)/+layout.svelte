<script lang="ts">
	import authService from '$lib/service/auth/auth-service.js';
	import {createQuery} from "@tanstack/svelte-query";
    import Login from "$lib/component/login/Login.svelte";
    import {AUTH_CHECK} from "$lib/constant/query-key/auth";
    import Sidebar from "$lib/component/common/Sidebar.svelte";

	let query = createQuery({
		queryKey: [AUTH_CHECK],
		queryFn: authService.loadUser,
        retry: false
	});

	$inspect($query.isLoading, $query.isSuccess);
</script>

{#if $query.isLoading}
    <div>loading...</div>
{:else if $query.isError}
    <Login></Login>
{:else if $query.isSuccess}
    <Sidebar />
    <main>
        <slot />
    </main>
{/if}

<style>
    :global(body) {
        margin: 0;
        padding: 0;
    }
</style>
