package fr.mb.bananesexport.controller;


import fr.mb.bananesexport.exception.BananesExportException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class BananesExportExceptionController {

    @ResponseBody
    @ExceptionHandler(BananesExportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(BananesExportException e){
        return e.getErrorMessageResponse();
    }

}
