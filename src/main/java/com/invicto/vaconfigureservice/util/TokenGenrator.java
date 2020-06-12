package com.invicto.vaconfigureservice.util;

import java.util.UUID;

public class TokenGenrator {

    public static String randomTokenGenrator(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
