package org.koreait.exceptions.file;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends CommonException {
    public AuthorizationException(){
        super(bundleValidations.getString("UnAuthorization"), HttpStatus.UNAUTHORIZED);
    }

    public AuthorizationException(String code){
        super(bundleValidations.getString(code),HttpStatus.UNAUTHORIZED);
    }
}
