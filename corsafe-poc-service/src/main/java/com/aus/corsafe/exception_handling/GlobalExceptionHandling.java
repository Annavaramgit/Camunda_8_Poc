package com.aus.corsafe.exception_handling;


import com.aus.corsafe.exceptions.BadCrediantialsCls;
import com.aus.corsafe.exceptions.ProductNotFoundException;
import com.aus.corsafe.exceptions.UnAuthorizedExceptionCls;
import com.aus.corsafe.exceptions.UserNotFoundExceptionCls;
import com.aus.corsafe.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(BadCrediantialsCls.class)
    public ResponseEntity<String> handleBadCredintials(BadCrediantialsCls ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserNotFoundExceptionCls.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundExceptionCls ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UnAuthorizedExceptionCls.class)
    public ResponseEntity<String> handleUserNotFoundException(UnAuthorizedExceptionCls ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }


}
