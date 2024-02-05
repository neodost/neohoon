let auth = $state(null);

export function createAuth() {
    return {
        get auth() {
            return auth;
        },
        set auth(newAuth) {
            auth = newAuth;
        }
    }
}