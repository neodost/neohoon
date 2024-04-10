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
    <div class="wrapper">
        <Sidebar />
        <div class="main">
            <main class="content">
                <div class="container-fluid p-0">
                    <h1 class="h3 mb-3">example</h1>
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <slot />
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </main>
        </div>
    </div>
{/if}
