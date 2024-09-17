package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.utils.exeptions.NotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.ExportConfigStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.PropertyFileException;
import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExportService {
    private ExportConfigStrategy exportConfigStrategy;
    private ExportStrategyHelper exportStrategyHelper;

    @Autowired
    public ExportService(ExportConfigStrategy exportConfigStrategy, ExportStrategyHelper exportStrategyHelper) {
        this.exportConfigStrategy = exportConfigStrategy;
        this.exportStrategyHelper = exportStrategyHelper;
    }

    public ExportDTO create(String dealersIdString, String carIdString) throws StrategyNotFoundException, ExportExeption, NotFoundException {
        List<Integer> dealersIds = createIdList(dealersIdString);
        List<Integer> carsIds = createIdList(carIdString);

        ExportDTO exportList = new ExportDTO();
        this.fillExportDealer(exportList, dealersIds);
        this.fillExportCar(exportList, carsIds);
        return exportList;
    }

    private List<Integer> createIdList(String ids) {
        List<Integer> idList = new ArrayList<>();
        if (ids != null) {
            String idArr[] = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                idList.add(Integer.parseInt(idArr[i]));
            }
        }
        return idList;
    }

    private void fillExportDealer(ExportDTO exportList, List<Integer> dealersIds) throws StrategyNotFoundException, ExportExeption, NotFoundException {
        int dealerExpIdStrategy;
        try {
            dealerExpIdStrategy = exportConfigStrategy.getExportConfig().get(Constants.DEALER_TYPE);
        } catch (PropertyFileException e) {
            throw new ExportExeption(Constants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        if (exportStrategyHelper.resolveDealerStrategy(dealerExpIdStrategy).equals(null)) {
            throw new StrategyNotFoundException(Constants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            ExportStrategy dealerExportStrategy = exportStrategyHelper.resolveDealerStrategy(dealerExpIdStrategy);
            dealerExportStrategy.collectExportIds(exportList, dealersIds);
        }
    }

    private void fillExportCar(ExportDTO exportList, List<Integer> carIds) throws StrategyNotFoundException, ExportExeption, NotFoundException {

        int carExpIdStrategy;
        try {
            if (exportList.getDealers().size() > 0) {
                carExpIdStrategy = Constants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY;
            } else {
                carExpIdStrategy = exportConfigStrategy.getExportConfig().get(Constants.CAR_TYPE);
            }
        } catch (PropertyFileException e) {
            throw new ExportExeption(Constants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (exportStrategyHelper.resolveCarStrategy(carExpIdStrategy).equals(null)) {
            throw new StrategyNotFoundException(Constants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            ExportStrategy carExportStrategy = exportStrategyHelper.resolveCarStrategy(carExpIdStrategy);
            carExportStrategy.collectExportIds(exportList, carIds);
        }
    }
}

