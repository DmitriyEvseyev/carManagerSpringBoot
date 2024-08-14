package com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile;

import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer.DealerConflictImportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer.DealerIgnoreImportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.dealer.DealerOverwriteImportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class ImportStrategyHelper {
    private DealerOverwriteImportStrategy dealerOverwriteImportStrategy;
    private DealerIgnoreImportStrategy dealerIgnoreImportStrategy;
    private DealerConflictImportStrategy dealerConflictImportStrategy;


    public ImportStrategy<CarDealershipEntity> resolveDealerStrategy(int strategyID) {
        Map<Integer, ImportStrategy<CarDealershipEntity>> dealerStrategies = new HashMap<>();
        dealerStrategies.put(Constants.DEALER_OVERWRITE_IMPORT_ID, dealerOverwriteImportStrategy);
        dealerStrategies.put(Constants.DEALER_IGNORE_IMPORT_ID, dealerIgnoreImportStrategy);
        dealerStrategies.put(Constants.DEALER_CONFLICT_IMPORT_ID, dealerConflictImportStrategy);
        return dealerStrategies.get(strategyID);
    }

    public ImportStrategy<CarDTO> resolveCarStrategy(int strategyId) {
        Map<Integer, ImportStrategy<CarDTO>> carStrategies = new HashMap<>();
//        this.carStrategies.put(Constants.CAR_OVERWRITE_IMPORT_ID, new CarOverwriteImportStrategy());
//        this.carStrategies.put(Constants.CAR_IGNORE_IMPORT_ID, new CarIgnoreImportStrategy());
//        this.carStrategies.put(Constants.CAR_CONFLICT_IMPORT_ID, new CarConflictImportStrategy());

        return carStrategies.get(strategyId);
    }
}
