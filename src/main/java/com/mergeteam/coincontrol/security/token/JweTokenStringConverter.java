package com.mergeteam.coincontrol.security.token;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class JweTokenStringConverter implements TokenStringConverter<Token> {

    private final JWEEncrypter jweEncrypter;
    private final JWEDecrypter jweDecrypter;
    private static final String AUTHORITIES = "authorities";

    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;

    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    @Override
    public String serialize(Token token) {
        JWEHeader jweHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
                .keyID(token.getId().toString())
                .build();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.getId().toString())
                .subject(token.getSubject())
                .issueTime(Date.from(token.getCreatedAt()))
                .expirationTime(Date.from(token.getExpiresAt()))
                .claim(AUTHORITIES, token.getAuthorities())
                .build();
        EncryptedJWT encryptedJWT = new EncryptedJWT(jweHeader, claimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Token deserialize(String token) {
        try {
            EncryptedJWT encryptedJWT = EncryptedJWT.parse(token);
            encryptedJWT.decrypt(this.jweDecrypter);
            JWTClaimsSet jwtClaimsSet = encryptedJWT.getJWTClaimsSet();
            return Token.builder()
                    .id(UUID.fromString(jwtClaimsSet.getJWTID()))
                    .subject(jwtClaimsSet.getSubject())
                    .createdAt(jwtClaimsSet.getIssueTime().toInstant())
                    .expiresAt(jwtClaimsSet.getExpirationTime().toInstant())
                    .authorities(jwtClaimsSet.getStringListClaim(AUTHORITIES))
                    .build();
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
