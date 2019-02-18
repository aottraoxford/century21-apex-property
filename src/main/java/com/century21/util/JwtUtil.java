package com.century21.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.century21.exception.CustomRuntimeException;
import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
public class JwtUtil<T> {
    public static final String secret="12345678@Cenutry21CambodiaRealEstate";
    public T tokenToObject(String token,String secret,T t){

        if(token==null){
            throw new CustomRuntimeException(401,"UNAUTHORIZED");
        }
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if(jwt.getIssuer()==null || jwt.getIssuer().length()==0) throw new CustomRuntimeException(400,"NULL ISSUER");
            Gson gson=new Gson();

            t=gson.fromJson(jwt.getIssuer(), (Type) t);
        } catch (JWTVerificationException exception){
            throw new CustomRuntimeException(401,"TOKEN UNAUTHORIZED");
        }
        return t;
    }

    public String objectToToken(T t,String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer(t.toString())
                .sign(algorithm);
        return token;
    }
}
