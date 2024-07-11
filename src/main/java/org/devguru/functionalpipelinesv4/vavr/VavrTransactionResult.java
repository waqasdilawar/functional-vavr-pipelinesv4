package org.devguru.functionalpipelinesv4.vavr;

public class VavrTransactionResult {
  private VavrTransaction transaction;
  private String message;
  private boolean success;

  public VavrTransactionResult(VavrTransaction transaction, String transactionCompletedSuccessfully, boolean b) {
    this.transaction = transaction;
    this.message = transactionCompletedSuccessfully;
    this.success = b;
  }

  // Constructor, getters, and setters


  public VavrTransaction getTransaction() {
    return transaction;
  }

  public void setTransaction(VavrTransaction transaction) {
    this.transaction = transaction;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
