package com.jxzy.AppMigration.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwk.Jwk;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Dong.w
 */
@Component
@Slf4j
public class AppleIdValidationComponent {

    public boolean isValid(String identityToken) {
        if (StringUtils.isEmpty(identityToken)) {
            return false;
        }
        CusJws cusJws = this.getCusJws(identityToken);
        if (Objects.isNull(cusJws)) {
            log.info("1");
            return false;
        }
        if (cusJws.getJwsPayload().getExp() * 1000 < System.currentTimeMillis()) {
            log.info("2");
            return false;
        }
        if (!StringUtils.pathEquals(JwsPayload.ISS, cusJws.getJwsPayload().getIss())) {
            log.info("3");
            return false;
        }
        PublicKey publicKey = publicKeyAdapter(cusJws.getJwsHeader().getKid());

        return verify(publicKey, identityToken, cusJws.getJwsPayload().getAud(), cusJws.getJwsPayload().getSub());
    }

    private boolean verify(PublicKey publicKey, String jwt, String audience, String subject) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(publicKey);
        jwtParser.requireIssuer("https://appleid.apple.com");
        jwtParser.requireAudience(audience);
        jwtParser.requireSubject(subject);
        try {
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);
            return Objects.nonNull(claimsJws) && claimsJws.getBody().containsKey("auth_time");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PublicKey publicKeyAdapter(String kid) {
        String appleIdPublicKeyForRemote = getAppleIdPublicKeyForRemote();
        if (StringUtils.isEmpty(appleIdPublicKeyForRemote)) {
            return null;
        }
        Map map = JSON.parseObject(appleIdPublicKeyForRemote, Map.class);
        List<Map> keys = (List<Map>) map.get("keys");
        Map maps = null;
        for (Map key : keys) {
            if (kid.equals(key.get("kid").toString())) {
                maps = key;
                break;
            }
        }
        Jwk jwk = Jwk.fromValues(maps);
        try {
            return jwk.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAppleIdPublicKeyForRemote() {
        ResponseEntity<String> entity = new RestTemplate().getForEntity("https://appleid.apple.com/auth/keys", String.class);
        if (Objects.isNull(entity) || entity.getStatusCode() != HttpStatus.OK) {
            log.error("get apple id public key for remote error, error detail is -> [{}]", entity.getStatusCode());
            return null;
        }
        return entity.getBody();
    }

    private CusJws getCusJws(String identityToken) {
        String[] identityTokenArr = identityToken.split("\\.");
        if (identityTokenArr.length != 3) {
            return null;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        JwsHeader jwsHeader = JSON.parseObject(new String(decoder.decode(identityTokenArr[0])), JwsHeader.class);
        JwsPayload jwsPayload = JSON.parseObject(new String(decoder.decode(identityTokenArr[1])), JwsPayload.class);
        return new CusJws(jwsHeader, jwsPayload, identityTokenArr[2]);
    }

    static class CusJws {
        private JwsHeader jwsHeader;
        private JwsPayload jwsPayload;
        private String signature;

        public CusJws(JwsHeader jwsHeader, JwsPayload jwsPayload, String signature) {
            this.jwsHeader = jwsHeader;
            this.jwsPayload = jwsPayload;
            this.signature = signature;
        }

        public JwsHeader getJwsHeader() {
            return jwsHeader;
        }

        public void setJwsHeader(JwsHeader jwsHeader) {
            this.jwsHeader = jwsHeader;
        }

        public JwsPayload getJwsPayload() {
            return jwsPayload;
        }

        public void setJwsPayload(JwsPayload jwsPayload) {
            this.jwsPayload = jwsPayload;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

    static class JwsHeader {
        private String kid;
        private String alg;

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }
    }

    static class JwsPayload {
        private String iss;
        private String sub;
        private String aud;
        private long exp;
        private long iat;
        private String nonce;
        private String email;
        private boolean email_verified;

        public final static String ISS = "https://appleid.apple.com";

        public String getIss() {
            return iss;
        }

        public void setIss(String iss) {
            this.iss = iss;
        }

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getAud() {
            return aud;
        }

        public void setAud(String aud) {
            this.aud = aud;
        }

        public long getExp() {
            return exp;
        }

        public void setExp(long exp) {
            this.exp = exp;
        }

        public long getIat() {
            return iat;
        }

        public void setIat(long iat) {
            this.iat = iat;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isEmail_verified() {
            return email_verified;
        }

        public void setEmail_verified(boolean email_verified) {
            this.email_verified = email_verified;
        }
    }

}
