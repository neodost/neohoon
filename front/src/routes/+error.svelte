<script lang="ts">
    import {page} from "$app/stores";
    import {goto} from "$app/navigation";
    import {browser} from "$app/environment";

    if (browser) {
        if ($page.status === 404) {
            goto('/')
        }
    }
</script>