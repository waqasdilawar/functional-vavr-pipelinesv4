package org.devguru.functionalpipelinesv4.standard;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {



  // Transaction with positive amount is processed successfully
  @Test
  public void test_transaction_with_positive_amount_is_processed_successfully() {
    TransactionProcessor processor = new TransactionProcessor();
    Transaction transaction = new Transaction("1", new BigDecimal("100.00"), "account1", "account2", LocalDateTime.now(), TransactionStatus.INITIATED);
    TransactionResult result = processor.processTransaction(transaction);
    assertTrue(result.isSuccess());
    assertEquals(TransactionStatus.COMPLETED, result.getTransaction().getStatus());
    assertEquals("Transaction completed successfully", result.getMessage());
  }

  // Transaction status changes to VALIDATED after validation
  @Test
  public void test_transaction_status_changes_to_validated_after_validation() {
    TransactionProcessor processor = new TransactionProcessor();
    Transaction transaction = new Transaction("2", new BigDecimal("50.00"), "account1", "account2", LocalDateTime.now(), TransactionStatus.INITIATED);
    processor.processTransaction(transaction);
    assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
  }

  // Transaction with zero amount fails validation
  @Test
  public void test_transaction_with_zero_amount_fails_validation() {
    TransactionProcessor processor = new TransactionProcessor();
    Transaction transaction = new Transaction("3", BigDecimal.ZERO, "account1", "account2", LocalDateTime.now(), TransactionStatus.INITIATED);
    TransactionResult result = processor.processTransaction(transaction);
    assertFalse(result.isSuccess());
    assertEquals("Invalid amount", result.getMessage());
  }

  // Transaction with negative amount fails validation
  @Test
  public void test_transaction_with_negative_amount_fails_validation() {
    TransactionProcessor processor = new TransactionProcessor();
    Transaction transaction = new Transaction("4", new BigDecimal("-10.00"), "account1", "account2", LocalDateTime.now(), TransactionStatus.INITIATED);
    TransactionResult result = processor.processTransaction(transaction);
    assertFalse(result.isSuccess());
    assertEquals("Invalid amount", result.getMessage());
  }
}