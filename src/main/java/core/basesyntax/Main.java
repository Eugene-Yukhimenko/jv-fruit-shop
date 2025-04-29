package core.basesyntax;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String inputFile = "reportToRead.csv";
        String outputFile = "finalReport.csv";

        try {
            final List<FruitTransaction> transactions = new DataConverterImpl()
                    .convertToTransaction(new FileReaderImpl().read(inputFile));

            Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
            operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
            operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
            operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
            operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

            OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
            ShopService shopService = new ShopServiceImpl(operationStrategy);
            shopService.process(transactions);

            ReportGenerator reportGenerator = new ReportGeneratorImpl(shopService);
            String resultingReport = reportGenerator.getReport();

            new FileWriterImpl().write(resultingReport, outputFile);

            System.out.println("Звіт сформовано і записано у файл: " + outputFile);

        } catch (IOException e) {
            System.err.println("Помилка при роботі з файлами: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Помилка під час виконання: " + e.getMessage());
        }
    }
}
