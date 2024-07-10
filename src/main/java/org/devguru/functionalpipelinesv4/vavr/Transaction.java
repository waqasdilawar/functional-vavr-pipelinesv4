package org.devguru.functionalpipelinesv4.vavr;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class Transaction {
  private String id;
  private BigDecimal amount;
  private String accountFrom;
  private String accountTo;
  private LocalDateTime timestamp;
  private TransactionStatus status;

  // Constructor, getters, and setters

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getAccountFrom() {
    return accountFrom;
  }

  public void setAccountFrom(String accountFrom) {
    this.accountFrom = accountFrom;
  }

  public String getAccountTo() {
    return accountTo;
  }

  public void setAccountTo(String accountTo) {
    this.accountTo = accountTo;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }
}

enum TransactionStatus {
  INITIATED, VALIDATED, PROCESSED, COMPLETED, FAILED
}

class TransactionResult {
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