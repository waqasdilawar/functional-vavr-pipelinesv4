# Functional Pipelines v4

This project demonstrates the use of functional programming paradigms in Java to process transactions. It includes two main approaches: one using Vavr, a functional library for Java, and another using standard Java functional interfaces.

## Project Structure

The project is organized into the following packages:

- `org.devguru.functionalpipelinesv4.controller`: Contains the controllers for handling HTTP requests.
- `org.devguru.functionalpipelinesv4.vavr`: Contains the Vavr-based transaction processing logic.
- `org.devguru.functionalpipelinesv4.standard`: Contains the standard Java-based transaction processing logic.

## Controllers

### TransactionController

Handles HTTP requests for transaction processing.

#### Endpoints

- `POST /transaction`: Processes transactions using the Vavr library.
- `POST /transaction/standard`: Processes transactions using standard Java functional interfaces.

#### Methods

```java
/**
 * Handles POST requests for transactions.
 *
 * This method processes a list of VavrTransaction objects using the VavrTransactionProcessor.
 * If the transactions are processed successfully, it returns a response with a success message.
 * If an error occurs during processing, it logs the error and returns an internal server error response.
 *
 * @param transactions List<VavrTransaction> - The list of transactions to process.
 * @return ResponseEntity<String> - A response entity containing either a success message or an error message.
 */
@PostMapping
public ResponseEntity<String> transaction(@RequestBody List<VavrTransaction> transactions) {
    try {
        VavrTransactionProcessor processor = new VavrTransactionProcessor();
        processor.executeTransactions(io.vavr.collection.List.ofAll(transactions));
        return ResponseEntity.ok("Vavr! Transactions processed successfully!");
    } catch (Exception e) {
        logger.warning("Error processing transactions: " + e.getMessage());
        return ResponseEntity.internalServerError().body("Error processing transactions");
    }
}

/**
 * Handles POST requests for standard transactions.
 *
 * This method processes a list of Transaction objects using the TransactionProcessor.
 * If the transactions are processed successfully, it returns a response with a success message.
 * If an error occurs during processing, it logs the error and returns an internal server error response.
 *
 * @param transactions List<Transaction> - The list of transactions to process.
 * @return ResponseEntity<String> - A response entity containing either a success message or an error message.
 */
@PostMapping("/standard")
public ResponseEntity<String> standardTransaction(@RequestBody List<Transaction> transactions) {
    try {
        var processor = new TransactionProcessor();
        transactions.stream()
                .map(processor::processTransaction)
                .peek(result -> {
                    if (result.isSuccess()) {
                        logger.info("Transaction processed successfully: " + result.getMessage());
                    } else {
                        logger.warning("Transaction processing failed: " + result.getMessage());
                    }
                })
                .forEach(result -> {}); // No-op forEach to trigger the stream processing

        return ResponseEntity.ok("Standard! Transactions processed successfully!");
    } catch (Exception e) {
        logger.warning("Error processing standard transactions: " + e.getMessage());
        return ResponseEntity.internalServerError().body("Error processing standard transactions");
    }
}
```
Example JSON Request Bodies
## Vavr Transactions
```[
  {
    "id": "txn123",
    "amount": 100.00,
    "accountFrom": "account1",
    "accountTo": "account2",
    "timestamp": "2023-10-01T12:00:00",
    "status": "INITIATED"
  },
  {
    "id": "txn124",
    "amount": 200.00,
    "accountFrom": "account3",
    "accountTo": "account4",
    "timestamp": "2023-10-01T13:00:00",
    "status": "INITIATED"
  }
]

curl -X POST http://localhost:8080/transaction \
     -H "Content-Type: application/json" \
     -d '[
           {
             "id": "txn123",
             "amount": 100.00,
             "accountFrom": "account1",
             "accountTo": "account2",
             "timestamp": "2023-10-01T12:00:00",
             "status": "INITIATED"
           },
           {
             "id": "txn124",
             "amount": 200.00,
             "accountFrom": "account3",
             "accountTo": "account4",
             "timestamp": "2023-10-01T13:00:00",
             "status": "INITIATED"
           }
         ]'
```

## Standard Transactions
```[
  {
    "id": "txn125",
    "amount": 150.00,
    "accountFrom": "account5",
    "accountTo": "account6",
    "timestamp": "2023-10-01T14:00:00",
    "status": "PENDING"
  },
  {
    "id": "txn126",
    "amount": 250.00,
    "accountFrom": "account7",
    "accountTo": "account8",
    "timestamp": "2023-10-01T15:00:00",
    "status": "PENDING"
  }
]

curl -X POST http://localhost:8080/transaction/standard \
     -H "Content-Type: application/json" \
     -d '[
           {
             "id": "txn125",
             "amount": 150.00,
             "accountFrom": "account5",
             "accountTo": "account6",
             "timestamp": "2023-10-01T14:00:00",
             "status": "PENDING"
           },
           {
             "id": "txn126",
             "amount": 250.00,
             "accountFrom": "account7",
             "accountTo": "account8",
             "timestamp": "2023-10-01T15:00:00",
             "status": "PENDING"
           }
         ]'
```
## Running the Application
You can run the application using your IDE or by using Maven:

`mvn spring-boot:run`

Running the Tests
You can run the integration tests using your IDE's built-in test runner or by using Maven:

`mvn test`

These updates to the README.md file provide clear instructions on how to use the new request body format for both Vavr and standard transactions, including example curl commands to test the endpoints with appropriate data.