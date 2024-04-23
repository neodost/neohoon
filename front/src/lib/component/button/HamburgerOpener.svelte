<script lang="ts">
    export let opened: boolean = true;
    export let size: number = 30;
    export let color: String = '#414141'
    export let onclick: (e: Event) => void = () => undefined;
</script>

<div class="hamburger-opener-box" class:opened style='--size:{size}px; --color:{color}' onclick={onclick}>
    <div></div>
    <div></div>
    <div></div>
    <div></div>
</div>

<style>
    .hamburger-opener-box {
        width: calc(var(--size));
        height: calc(var(--size));
        background-color: #ffffff;
        border: var(--color) 1px solid;
        border-radius: calc(var(--size) * 0.2);
        box-sizing: border-box;
        position: relative;

        --top-margin: calc((var(--size) - 2px - var(--size) * 0.3) / 4);
        --height: calc(var(--size) * 0.1);

        div {
            position: absolute;
            background-color: var(--color);
            border-radius: calc(var(--size) * 0.1);
            width: calc((var(--size) - 2px) * 0.75);
            left: calc((var(--size) - 2px) * 0.125);
            height: var(--height);
            transition: opacity 0.6s, transform 0.6s, visibility 0.6s;
        }

        div:nth-child(1) {
            top: var(--top-margin);
            opacity: 1;
        }
        div:nth-child(2) {
            top: calc(var(--top-margin) * 2 + var(--height));
            visibility: hidden;
        }
        div:nth-child(3) {
            top: calc(var(--top-margin) * 2 + var(--height));
        }
        div:nth-child(4) {
            top: calc(var(--top-margin) * 3 + var(--height) * 2);
            opacity: 1;
        }

        &.opened {
            div:nth-child(1) {
                opacity: 0;
            }
            div:nth-child(2) {
                transform: rotate(45deg);
                visibility: visible;
            }
            div:nth-child(3) {
                transform: rotate(-45deg);
            }
            div:nth-child(4) {
                opacity: 0;
            }
        }
    }
</style>