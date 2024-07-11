package org.devguru.functionalpipelinesv4.standard;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
  private String id;
  private BigDecimal amount;
  private String accountFrom;
  private String accountTo;
  private LocalDateTime timestamp;
  private TransactionStatus status;

  public Transaction(String id, BigDecimal amount, String accountFrom, String accountTo, LocalDateTime timestamp, TransactionStatus status) {
    this.id = id;
    this.amount = amount;
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.timestamp = timestamp;
    this.status = status;
  }

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

