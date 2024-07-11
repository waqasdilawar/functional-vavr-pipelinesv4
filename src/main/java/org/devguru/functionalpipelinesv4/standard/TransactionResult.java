package org.devguru.functionalpipelinesv4.standard;

public class TransactionResult {
  private Transaction transaction;
  private String message;
  private boolean success;

  public TransactionResult(Transaction transaction, String transactionCompletedSuccessfully, boolean b) {
    this.transaction = transaction;
    this.message = transactionCompletedSuccessfully;
    this.success = b;
  }

  // Constructor, getters, and setters


  public Transaction getTransaction() {
    return transaction;
  }

  public void setTransaction(Transaction transaction) {
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
