interface Authority {
    authority: string
}
interface Auth {
    username: string,
    vk: string,
    authorities: Authority[]
}

let auth: Auth | null = $state(null)

export function createAuth(): {auth: Auth | null} {
    return {
        get auth(): Auth | null {
            return auth;
        },
        set auth(newAuth: Auth | null) {
            auth = newAuth;
        }
    }
}