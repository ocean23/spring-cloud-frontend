package com.fujfu.frontend.permission.authentication;

import java.util.Optional;

/**
 * @author Beldon
 */
public final class AuthenticationHolder {
    private static final ThreadLocal<Authentication> AUTHENTICATION = new ThreadLocal<>();

    private AuthenticationHolder() {

    }

    public static Optional<Authentication> get() {
        return Optional.ofNullable(AUTHENTICATION.get());
    }

    public static void remove() {
        AUTHENTICATION.remove();
    }

    public static void set(Authentication authentication) {
        AUTHENTICATION.set(authentication);
    }
}
