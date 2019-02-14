package com.century21.century21cambodia.util;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.Map;

public class DecodeJWT {
    public static String getEmailFromJwt(String token) {
        String data = null;
        if (token == null)
            return null;
        String[] split_string = token.split("\\.");
        if (split_string.length < 3) {
            throw new CustomRuntimeException(400, "Wrong Token Format");
        }
        //String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        //String base64EncodedSignature = split_string[2];

        //JWT Header
        Base64 base64Url = new Base64(true);
        //String header = new String(base64Url.decode(base64EncodedHeader));

        //JWT Body
        String body = new String(base64Url.decode(base64EncodedBody));
        try {
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(body);
            data = map.get("user_name").toString();
        } catch (JsonParseException ex) {
            throw new CustomRuntimeException(400, "Token not invalid.");
        }
        return data;
    }
}
