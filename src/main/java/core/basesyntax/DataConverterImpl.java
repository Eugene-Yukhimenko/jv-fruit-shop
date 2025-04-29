package core.basesyntax;

import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        if (data == null || data.isEmpty()) {
            return transactions;
        }

        for (int i = 1;i < data.size(); i++) {
            String line = data.get(i);
            String[] parts = line.split(",");
            if (parts.length != 3) {
                continue;
            }
            try {
                FruitTransaction.Operation operation = FruitTransaction
                        .Operation.fromCode(parts[0]);
                String fruit = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
                transactions.add(transaction);
            } catch (Exception e) {
                System.out.println("Failed to parse line: " + line + " due to " + e.getMessage());
            }
        }
        return transactions;
    }
}
