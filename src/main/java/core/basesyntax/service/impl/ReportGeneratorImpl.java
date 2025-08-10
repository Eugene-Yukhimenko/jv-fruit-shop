package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    private final ShopService shopService;

    public ReportGeneratorImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("fruit,quantity\n");
        for (Map.Entry<String, Integer> entry : shopService.getStorage().entrySet()) {
            sb.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
