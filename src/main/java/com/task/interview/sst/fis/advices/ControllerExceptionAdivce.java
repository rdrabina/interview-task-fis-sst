package com.task.interview.sst.fis.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionAdivce {

//    @ExceptionHandler(Throwable.class)
//    @ResponseBody
//    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
//        SnapshotExceptionVo errorResponse = new SnapshotExceptionVo();
//        errorResponse.setErrorMessage(ex.toString());
//
//        if (ex instanceof SnapshotException) {
//            SnapshotException snapshotException = (SnapshotException) ex;
//            errorResponse.setErrorCode(snapshotException.getErrorCode());
//            errorResponse.setErrorMessage(snapshotException.getErrorMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
//        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
//            errorResponse.setErrorCode("RT002");
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        } else {
//            logger.error("Exception in handleControllerException, and Message is: {0}", ex);
//            errorResponse.setErrorCode("RT001");
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
