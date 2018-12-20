package com.century21.century21cambodia.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse<T> {
    private HttpStatus httpStatus;
    private String status;
    private int statusCode;
    private T[] t;
    public CustomResponse(int statusCode,T ...t){
        setT(t);
        setStatusCode(statusCode);
        setMessageAndCode(statusCode);
    }

    public CustomResponse(int statusCode){
        setMessageAndCode(statusCode);
        setStatusCode(statusCode);
    }

    private HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private T[] getT() {
        return t;
    }

    private void setT(T[] t) {
        this.t = t;
    }

    public ResponseEntity<Map<String, Object>> httpResponse(String ...key){
        Map<String,Object> map=new HashMap<>();
        map.put("status",getStatus());
        map.put("status_code",getStatusCode());
        if( getT()!=null ) {
            if( key.length!=getT().length ){
                map=new HashMap<>();
                map.put("status","Syntax Error");
                map.put("statusCode",500);
                return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            for (int i = 0; i < getT().length; i++) {
                map.put(key[i], t[i]);
            }
        }
        return new ResponseEntity<>(map,getHttpStatus());
    }

    private void setMessageAndCode(int httpStatusCode){
        switch(httpStatusCode){
            case 200 : setStatus("OK");setHttpStatus(HttpStatus.OK); break ;
            case 201 : setStatus("CREATED");setHttpStatus(HttpStatus.CREATED);break;
            case 304 : setStatus("NOT MODIFIED");setHttpStatus(httpStatus.NOT_MODIFIED);
            case 400 : setStatus("BAD REQUEST");setHttpStatus(HttpStatus.BAD_REQUEST);break;
            case 401 : setStatus("UNAUTHORIZED");setHttpStatus(HttpStatus.UNAUTHORIZED);break;
            case 403 : setStatus("FORBIDDEN");setHttpStatus(HttpStatus.FORBIDDEN);break;
            case 404 : setStatus("NOT FOUND"); setHttpStatus(HttpStatus.NOT_FOUND);break;
            case 409 : setStatus("CONFLICT");setHttpStatus(httpStatus.CONFLICT);break;
            case 500 : setStatus("INTERNAL_SERVER_ERROR");setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);break;
            default:setStatus(null);setHttpStatus(null);
        }
    }

}
