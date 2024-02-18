import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	server: {
		proxy: {
			'/api/v1/authenticate': {
				target: 'https://localhost:8081',
				secure: false
			},
			'/api': {
				target: 'https://localhost:8080',
				secure: false
			},
		}
	},
	plugins: [sveltekit()]
});
