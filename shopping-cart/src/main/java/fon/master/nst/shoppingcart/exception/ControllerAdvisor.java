package fon.master.nst.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fon.master.nst.shoppingcart.model.Error;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Error> handleApiException(ApiException apiException) {

        Error error = new Error();
        error.setMessage(apiException.getErrorMessage());
        error.setStatus(apiException.getStatus());
        error.setStatusCodeName(apiException.getStatusCodeName());

        return new ResponseEntity<>(error, HttpStatus.valueOf(apiException.getStatus()));
    }


}
