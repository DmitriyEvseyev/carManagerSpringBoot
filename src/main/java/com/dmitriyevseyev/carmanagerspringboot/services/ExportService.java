package com.dmitriyevseyev.carmanagerspringboot.services;

import com.dmitriyevseyev.carmanagerspringboot.repositories.CarRepository;
import com.dmitriyevseyev.carmanagerspringboot.repositories.DealerRepository;
import com.dmitriyevseyev.carmanagerspringboot.utils.ConverterEntity;
import com.dmitriyevseyev.carmanagerspringboot.utils.ExportDTO;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.ExportConfigStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.PropertyFileException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyConstants;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.StrategyNotFoundException;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportExeption;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategy;
import com.dmitriyevseyev.carmanagerspringboot.utils.strategy.export.ExportStrategyHelper;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ExportService {
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    private ConverterEntity converterEntity;
    private ExportConfigStrategy exportConfigStrategy;
    private ExportStrategyHelper exportStrategyHelper;

    public ExportDTO create(String dealersIdString, String carIdString) throws StrategyNotFoundException, ExportExeption {

        System.out.println("dealersIdString - " + dealersIdString);

        List<Integer> dealersIds = new ArrayList<>();
        dealersIds = createIdList(dealersIdString);
        List<Integer> carsIds = new ArrayList<>();
        carsIds = createIdList(carIdString);

        ExportDTO exportList = new ExportDTO();
        this.fillExportDealer(exportList, dealersIds);
        // this.fillExportCar(exportList, carsIds);
        return exportList;
    }

    private List<Integer> createIdList(String ids) {
        List<Integer> idList = new ArrayList<>();
        if (ids != null) {

            System.out.println("AAAAA");

            String idArr[] = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                idList.add(Integer.parseInt(idArr[i]));
            }
        }
        return idList;
    }


    private void fillExportDealer(ExportDTO exportList, List<Integer> dealersIds) throws StrategyNotFoundException, ExportExeption {
        int dealerExpIdStrategy;
        try {
            dealerExpIdStrategy = exportConfigStrategy.getExportConfig().get(StrategyConstants.DEALER_TYPE);


            System.out.println("dealerExpIdStrategy - " + dealerExpIdStrategy);



        } catch (PropertyFileException e) {
            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }



        if (exportStrategyHelper.resolveDealerStrategy(dealerExpIdStrategy).equals(null)) {
            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            ExportStrategy dealerExportStrategy = exportStrategyHelper.resolveDealerStrategy(dealerExpIdStrategy);
            dealerExportStrategy.collectExportIds(exportList, dealersIds);
        }
    }

//    private void fillExportCar(ExportDTO exportList, List<Integer> carIds) throws StrategyNotFoundException, ExportExeption {
//
//        ExportConfigStrategy config = null;
//        int carExpIdStrategy;
//        config = ExportConfigStrategy.getInstance();
//
//        try {
//            if (exportList.getDealers().size() > 0) {
//                carExpIdStrategy = StrategyConstants.CAR_EXPORT_WITHOUT_DEALER_NUMBER_STRATEGY;
//            } else {
//                carExpIdStrategy = config.getExportConfig().get(StrategyConstants.CAR_TYPE);
//            }
//        } catch (PropertyFileException e) {
//            throw new ExportExeption(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
//        }
//
//        if (ExportStrategyHelper.getInstance().resolveCarStrategy(carExpIdStrategy).equals(null)) {
//            throw new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
//        } else {
//            ExportStrategy carExportStrategy = ExportStrategyHelper.getInstance().resolveCarStrategy(carExpIdStrategy);
//            carExportStrategy.collectExportIds(exportList, carIds);
//        }
//    }
}

