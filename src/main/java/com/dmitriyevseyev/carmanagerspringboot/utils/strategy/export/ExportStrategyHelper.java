package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export;

import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyConstants;
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
    private Map<Integer, ExportStrategy> dealerStrategies;
    private Map<Integer, ExportStrategy> carStrategies;
    private DealerExportStrategy dealerExportStrategy;
    private DealerExportStrategyWithCar dealerExportStrategyWithCar;

    public ExportStrategy resolveDealerStrategy(int strategyID) {
        dealerStrategies = new HashMap<>();
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITHOUT_CARS_NUMBER_STRATEGY, dealerExportStrategy);
        dealerStrategies.put(StrategyConstants.DEALER_EXPORT_WITH_CARS_NUMBER_STRATEGY, dealerExportStrategyWithCar);
//
//        this.carStrategies = new HashMap<>();
//        carStrategies.put(StrategyConstants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY, new CarExportStrategy());
//        carStrategies.put(StrategyConstants.CAR_EXPORT_WITH_DEALER_NUMBER_STRATEGY, new CarExportStrategyWithDealer());


        return dealerStrategies.get(strategyID);
    }

//    public ExportStrategy resolveCarStrategy(int strategyId) {
//        return this.carStrategies.get(strategyId);
//    }
}
