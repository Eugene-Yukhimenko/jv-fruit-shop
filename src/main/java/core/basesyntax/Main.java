package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String inputFile = "reportToRead.csv";
        String outputFile = "finalReport.csv";

        try {
            final FileReader fileReader = new FileReaderImpl();
            final List<String> inputReport = fileReader.read(inputFile);

            // Convert to transactions
            final DataConverter dataConverter = new DataConverterImpl();
            final List<FruitTransaction> transactions = dataConverter
                    .convertToTransaction(inputReport);

            final Map<FruitTransaction
                    .Operation, OperationHandler> operationHandlers = new HashMap<>();
            operationHandlers.put(FruitTransaction
                    .Operation.BALANCE, new BalanceOperation());
            operationHandlers.put(FruitTransaction
                    .Operation.SUPPLY, new SupplyOperation());
            operationHandlers.put(FruitTransaction
                    .Operation.PURCHASE, new PurchaseOperation());
            operationHandlers.put(FruitTransaction
                    .Operation.RETURN, new ReturnOperation());

            final OperationStrategy operationStrategy = new OperationStrategyImpl(
                    operationHandlers);
            final ShopService shopService = new ShopServiceImpl(
                    operationStrategy);
            shopService.process(transactions);

            final ReportGenerator reportGenerator = new ReportGeneratorImpl(
                    shopService);
            final String resultingReport = reportGenerator.getReport();

            final FileWriter fileWriter = new FileWriterImpl();
            fileWriter.write(resultingReport, outputFile);

            System.out.println("Report generated and saved to file: " + outputFile);

        } catch (IOException | RuntimeException e) {
            System.err.println("Critical error: " + e.getMessage());
            throw new RuntimeException("Application failed during processing", e);
        }
    }
}
