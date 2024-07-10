package org.devguru.functionalpipelinesv4.standard;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.logging.Logger;

public class TransactionProcessor {
  private static final Logger logger = Logger.getLogger(TransactionProcessor.class.getName());

  public TransactionResult processTransaction(Transaction transaction) {
    return validate(transaction)
            .andThen(this::enrichTransaction)
            .andThen(this::processPayment)
            .andThen(this::notifyParties)
            .andThen(this::updateDatabase)
            .apply(new TransactionResult(transaction, "", true));
  }

  private Function<TransactionResult, TransactionResult> validate(Transaction transaction) {
    return result -> {
      if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        return new TransactionResult(transaction, "Invalid amount", false);
      }
      transaction.setStatus(TransactionStatus.VALIDATED);
      return result;
    };
  }

  private TransactionResult enrichTransaction(TransactionResult result) {
    if (!result.isSuccess()) return result;
    try {
      // Simulate enriching transaction with additional data
      Thread.sleep(100);
      return result;
    } catch (InterruptedException e) {
      return new TransactionResult(result.getTransaction(), "Enrichment failed", false);
    }
  }

  private TransactionResult processPayment(TransactionResult result) {
    if (!result.isSuccess()) return result;
    try {
      // Simulate payment processing
      Thread.sleep(200);
      result.getTransaction().setStatus(TransactionStatus.PROCESSED);
      return result;
    } catch (InterruptedException e) {
      return new TransactionResult(result.getTransaction(), "Payment processing failed", false);
    }
  }

  private TransactionResult notifyParties(TransactionResult result) {
    if (!result.isSuccess()) return result;
    try {
      // Simulate sending notifications
      Thread.sleep(50);
      return result;
    } catch (InterruptedException e) {
      logger.warning("Notification failed, but continuing pipeline");
      return result; // Continue pipeline even if notification fails
    }
  }

  private TransactionResult updateDatabase(TransactionResult result) {
    if (!result.isSuccess()) return result;
    try {
      // Simulate database update
      Thread.sleep(100);
      result.getTransaction().setStatus(TransactionStatus.COMPLETED);
      return new TransactionResult(result.getTransaction(), "Transaction completed successfully", true);
    } catch (InterruptedException e) {
      return new TransactionResult(result.getTransaction(), "Database update failed", false);
    }
  }
}
