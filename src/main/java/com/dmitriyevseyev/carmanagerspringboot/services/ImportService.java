package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.models.CarDealership;
import com.dmitriyevseyev.carmanagerspringboot.models.dto.CarDTO;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarDealershipEntity;
import com.dmitriyevseyev.carmanagerspringboot.models.entity.CarEntity;
import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.*;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.ExportConfigStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.PropertyFileException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.ImportStrategyHelper;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.importFile.JSONValidatorExeption;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ImportService {
    private ConverterDTO converterDTO;
    private ObjectMapper objectMapper;
    private ExportConfigStrategy exportConfigStrategy;
    private ImportStrategyHelper importStrategyHelper;

    @Transactional
    public void importFile(String json) throws IOException, JSONValidatorExeption, ImportExeption {
        JsonValidator jsonValidator = JsonValidator.getInstance();

        if (!jsonValidator.isValidImport(json).isEmpty()) {
            throw new JSONValidatorExeption(Constants.VALIDATION_EXEPTION +
                    jsonValidator.isValidImport(json));
        } else {
            importObjects(objectMapper.readValue(json, ExportDTO.class));
        }
    }

    private void importObjects(ExportDTO exportDTO) throws ImportExeption {
        List<CarDealershipEntity> dealerEntityList = new ArrayList<>();
        List<CarDTO> carDTOList = new ArrayList<>();

        dealerEntityList.addAll(converterDTO.convertetDealerDTOListToDealerEntityList(exportDTO.getDealers()));
        carDTOList.addAll(exportDTO.getCars());

        int dealerImpIdStrategy;
        int carImpIdStrategy;
        try {
            dealerImpIdStrategy = exportConfigStrategy.getImportConfig().get(Constants.DEALER_TYPE);
            carImpIdStrategy = exportConfigStrategy.getImportConfig().get(Constants.CAR_TYPE);
        } catch (PropertyFileException | StrategyNotFoundException e) {
            throw new ImportExeption(Constants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        try {
            if (importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy).equals(null)) {
                System.out.println("IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE");
                throw new StrategyNotFoundException(Constants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
            } else {
                ImportStrategy<CarDealershipEntity> dealerImportStrategy = importStrategyHelper.resolveDealerStrategy(dealerImpIdStrategy);
                for (CarDealershipEntity dealer : dealerEntityList) {
                    dealerImportStrategy.store(dealer);
                }
            }
        } catch (StrategyNotFoundException e) {
            throw new ImportExeption(Constants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        try {
            if (importStrategyHelper.resolveCarStrategy(carImpIdStrategy).equals(null)) {
                throw new StrategyNotFoundException(Constants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
            } else {
                ImportStrategy<CarDTO> carImportStrategy = importStrategyHelper.resolveCarStrategy(carImpIdStrategy);
                for (CarDTO carDTO : carDTOList) {
                    carImportStrategy.store(carDTO);
                }
            }
        } catch (StrategyNotFoundException e) {
            throw new ImportExeption(Constants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

    }
}
