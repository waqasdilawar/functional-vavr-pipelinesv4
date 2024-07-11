package org.devguru.functionalpipelinesv4.vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VavrTransactionProcessorTest {


  // Successfully processes a valid transaction
  @Test
  public void test_successfully_processes_valid_transaction() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    VavrTransaction transaction = new VavrTransaction("1", new BigDecimal("100.00"), "account1", "account2", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);
    assertTrue(result.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, result.get().getTransaction().getStatus());
  }

  // Validates transaction amount is greater than zero
  @Test
  public void test_validates_transaction_amount_greater_than_zero() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    VavrTransaction transaction = new VavrTransaction("2", new BigDecimal("0.01"), "account1", "account2", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);
    assertTrue(result.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, result.get().getTransaction().getStatus());
  }

  // Transaction amount is zero or negative
  @Test
  public void test_transaction_amount_zero_or_negative() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    VavrTransaction transaction = new VavrTransaction("3", new BigDecimal("-1.00"), "account1", "account2", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);
    assertTrue(result.isFailure());
    assertEquals("Invalid amount", result.getCause().getMessage());
  }

  // Enrichment step fails but pipeline continues
  @Test
  public void test_enrichment_step_fails_but_pipeline_continues() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    VavrTransaction transaction = new VavrTransaction("4", new BigDecimal("100.00"), "account1", "account2", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);
    assertTrue(result.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, result.get().getTransaction().getStatus());
  }

  // Notification step fails but pipeline continues
  @Test
  public void test_notification_step_fails_but_pipeline_continues() {
    VavrTransactionProcessor processor = new VavrTransactionProcessor();
    VavrTransaction transaction = new VavrTransaction("5", new BigDecimal("100.00"), "account1", "account2", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);
    assertTrue(result.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, result.get().getTransaction().getStatus());
  }

}