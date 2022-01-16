package com.evdictionaries.controllers;

import com.evdictionaries.error.CustomErrorException;
import com.evdictionaries.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<BaseResponse> handleCustomErrorExceptions(
            Exception e
    ) {
        // casting the generic Exception e to CustomErrorException
        CustomErrorException customErrorException = (CustomErrorException) e;
        HttpStatus status = customErrorException.getStatus();
        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        customErrorException.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

//        return new ResponseEntity<>(
//                new BaseResponse(
//                        status,
//                        customErrorException.getMessage(),
//                        stackTrace,
//                        customErrorException.getData()
//                ),
//                status
//        );
        return ResponseEntity.ok(new BaseResponse(
                status,
                customErrorException.getMessage(),
                stackTrace,
                customErrorException.getData()
        ));
    }
}
