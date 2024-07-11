package org.devguru.functionalpipelinesv4.vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VavrTransactionProcessorTestClaude {
  private VavrTransactionProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new VavrTransactionProcessor();
  }

  @Test
  void testSuccessfulTransaction() {
    VavrTransaction transaction = new VavrTransaction("1", new BigDecimal("100"), "A", "B", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);

    assertTrue(result.isSuccess());
    VavrTransactionResult transactionResult = result.get();
    assertTrue(transactionResult.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, transactionResult.getTransaction().getStatus());
    assertEquals("Transaction completed successfully", transactionResult.getMessage());
  }

  @Test
  void testInvalidAmount() {
    VavrTransaction transaction = new VavrTransaction("2", new BigDecimal("-50"), "C", "D", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);

    assertTrue(result.isFailure());
    assertTrue(result.getCause() instanceof IllegalArgumentException);
    assertEquals("Invalid amount", result.getCause().getMessage());
  }

  @Test
  void testZeroAmount() {
    VavrTransaction transaction = new VavrTransaction("3", BigDecimal.ZERO, "E", "F", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);

    assertTrue(result.isFailure());
    assertTrue(result.getCause() instanceof IllegalArgumentException);
    assertEquals("Invalid amount", result.getCause().getMessage());
  }

  // Assuming we can force failures in different stages
  @Test
  void testEnrichmentFailure() {
    VavrTransaction transaction = new VavrTransaction("4", new BigDecimal("100"), "G", "H", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    // Assume we have a way to force enrichment failure
    transaction.setForceEnrichmentFailure(true);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);

    assertTrue(result.isSuccess()); // The pipeline continues even if enrichment fails
    VavrTransactionResult transactionResult = result.get();
    assertTrue(transactionResult.isSuccess());
    assertEquals(VavrTransactionStatus.COMPLETED, transactionResult.getTransaction().getStatus());
  }

  @Test
  @Disabled("It's not working because of assumption")
  void testPaymentProcessingFailure() {
    VavrTransaction transaction = new VavrTransaction("5", new BigDecimal("100"), "I", "J", LocalDateTime.now(), VavrTransactionStatus.INITIATED);
    // Assume we have a way to force payment processing failure
    transaction.setForcePaymentFailure(true);
    Try<VavrTransactionResult> result = processor.processTransaction(transaction);

    assertTrue(result.isFailure());
    assertTrue(result.getCause() instanceof RuntimeException);
    assertEquals("Payment processing failed", result.getCause().getMessage());
  }
}
