package com.triple.clubmileage.exception;

import com.triple.clubmileage.common.enums.ResponseType;
import com.triple.clubmileage.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class ClubMileageExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("e : {}", e.getMessage());
        String message = e.getBindingResult().getFieldErrors().get(0).getField() + " " +
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseDto.from(ResponseType.BAD_REQUEST, message);
    }

    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseDto<?> dataNotFoundException(DataNotFoundException e) {
        log.info("e: {}", e.getMessage());
        return ResponseDto.from(ResponseType.REVIEW_NOT_FOUND);

    }

    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseDto<?> duplicateKeyException(DuplicateKeyException e) {
        log.info("e: {}", e.getMessage());
        return ResponseDto.from(ResponseType.DUPLICATE_KEY_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDto<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("e : {}", e.getMessage());
        return ResponseDto.from(ResponseType.METHOD_NOT_ALLOWED);
    }

    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseDto<?> noHandlerFoundException(NoHandlerFoundException e) {
        log.info("e : {}", e.getMessage());
        return ResponseDto.from(ResponseType.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseDto<?> errorHandler(Exception e) {
//        log.info("e : {}", e.getMessage());
//        return ResponseDto.from(ResponseType.INTERNAL_SERVER_ERROR);
//    }
}
