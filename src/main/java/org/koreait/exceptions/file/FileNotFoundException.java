package org.koreait.exceptions.file;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {

    public FileNotFoundException() {
        super(bundleValidations.getString("File.notExists"), HttpStatus.BAD_REQUEST);
    }
}
