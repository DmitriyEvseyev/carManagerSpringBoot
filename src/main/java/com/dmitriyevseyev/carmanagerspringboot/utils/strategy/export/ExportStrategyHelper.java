package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export;

import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car.CarExportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.car.CarExportStrategyWithDealer;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.dealer.DealerExportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.dealer.DealerExportStrategyWithCar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class ExportStrategyHelper {
    private DealerExportStrategy dealerExportStrategy;
    private DealerExportStrategyWithCar dealerExportStrategyWithCar;
    private CarExportStrategy carExportStrategy;
    private CarExportStrategyWithDealer carExportStrategyWithDealer;

    public ExportStrategy resolveDealerStrategy(int strategyID) {
        Map<Integer, ExportStrategy> dealerStrategies = new HashMap<>();
        dealerStrategies.put(Constants.DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY, dealerExportStrategy);
        dealerStrategies.put(Constants.DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY, dealerExportStrategyWithCar);
        return dealerStrategies.get(strategyID);
    }

    public ExportStrategy resolveCarStrategy(int strategyId) {
        Map<Integer, ExportStrategy> carStrategies = new HashMap<>();
        carStrategies.put(Constants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY, carExportStrategy);
        carStrategies.put(Constants.CAR_EXPORT_WITH_DEALER_NUMBER_STRATEGY, carExportStrategyWithDealer);
        return carStrategies.get(strategyId);
    }
}
