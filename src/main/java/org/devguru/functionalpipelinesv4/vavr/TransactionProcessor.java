package org.devguru.functionalpipelinesv4.vavr;

import io.vavr.control.Try;
import io.vavr.collection.List;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class TransactionProcessor {
  private static final Logger logger = Logger.getLogger(TransactionProcessor.class.getName());

  public Try<TransactionResult> processTransaction(Transaction transaction) {
    return validate(transaction)
            .flatMap(this::enrichTransaction)
            .flatMap(this::processPayment)
            .flatMap(this::notifyParties)
            .flatMap(this::updateDatabase);
  }

  private Try<TransactionResult> validate(Transaction transaction) {
    return Try.of(() -> {
      if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Invalid amount");
      }
      transaction.setStatus(TransactionStatus.VALIDATED);
      return new TransactionResult(transaction, "", true);
    });
  }

  private Try<TransactionResult> enrichTransaction(TransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(100); // Simulate enriching transaction
      return result;
    }).recover(e -> {
      logger.warning("Enrichment failed, but continuing pipeline");
      return result; // Continue pipeline even if enrichment fails
    });
  }

  private Try<TransactionResult> processPayment(TransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(200); // Simulate payment processing
      result.getTransaction().setStatus(TransactionStatus.PROCESSED);
      return result;
    });
  }

  private Try<TransactionResult> notifyParties(TransactionResult result) {
    return Try.run(() -> {
              Thread.sleep(50); // Simulate sending notifications
            }).map(__ -> result)
            .recover(e -> {
              logger.warning("Notification failed, but continuing pipeline");
              return result; // Continue pipeline even if notification fails
            });
  }

  private Try<TransactionResult> updateDatabase(TransactionResult result) {
    return Try.of(() -> {
      Thread.sleep(100); // Simulate database update
      result.getTransaction().setStatus(TransactionStatus.COMPLETED);
      return new TransactionResult(result.getTransaction(), "Transaction completed successfully", true);
    });
  }

  public void executeTransactions(List<Transaction> transactions) {
    transactions.forEach(transaction ->
            processTransaction(transaction)
                    .onSuccess(result -> logger.info("Transaction processed: " + result.getMessage()))
                    .onFailure(error -> logger.severe("Transaction failed: " + error.getMessage()))
    );
  }
}
