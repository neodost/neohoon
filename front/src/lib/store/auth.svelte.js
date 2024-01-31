let auth = $state(null);

export function createAuth() {
    return {
        get auth() {
            return auth;
        },
        set: (newAuth) => {
            auth = newAuth;
        }
    }
}