package com.whoami.milkcheque.exception;

import com.whoami.milkcheque.dto.ErrorModel;
import com.whoami.milkcheque.dto.response.LoginResponse;
import com.whoami.milkcheque.dto.response.SignUpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AuthenticationFormatException.class)
  public ResponseEntity<Object> handleAuthenticationFormatException(
      AuthenticationFormatException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(SignUpProcessFailureException.class)
  public ResponseEntity<Object> handleSigupProcessFailureException(
      SignUpProcessFailureException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new SignUpResponse(exception.getCode(), exception.getMessage()));
  }

  @ExceptionHandler(LoginProcessFailureException.class)
  public ResponseEntity<Object> handleLoginProcessFailureException(
      LoginProcessFailureException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new LoginResponse(exception.getCode(), exception.getMessage(), ""));
  }

  @ExceptionHandler(MenuItemRetrievalException.class)
  public ResponseEntity<Object> handleMenuItemRetrievalException(
      MenuItemRetrievalException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorModel);
  }

  @ExceptionHandler(StoreTableRetrievalException.class)
  public ResponseEntity<Object> handleStoreTableRetrievalException(
      StoreTableRetrievalException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorModel);
  }

  @ExceptionHandler(StoreInfoRetrievalException.class)
  public ResponseEntity<Object> handleStoreInfoRetrievalException(
      StoreInfoRetrievalException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode("22");
    errorModel.setMessage(exception.getCode());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
  }

  @ExceptionHandler(AddOrderPatchFailureException.class)
  public ResponseEntity<Object> handleAddOrderPatchFailureException(
      AddOrderPatchFailureException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getCode());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
  }

  @ExceptionHandler(InvalidRequest.class)
  public ResponseEntity<Object> handleInvalidRequest(InvalidRequest exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
  }

  @ExceptionHandler(PaymobException.class)
  public ResponseEntity<Object> handlePaymobException(PaymobException exception) {
    ErrorModel errorModel = new ErrorModel();
    errorModel.setCode(exception.getCode());
    errorModel.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorModel);
  }
}
