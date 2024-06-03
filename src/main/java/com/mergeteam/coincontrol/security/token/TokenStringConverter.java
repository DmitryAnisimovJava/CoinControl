package com.mergeteam.coincontrol.security.token;

public interface TokenStringConverter<T extends Token> {

    String serialize(T token);

    T deserialize(String token);
}
