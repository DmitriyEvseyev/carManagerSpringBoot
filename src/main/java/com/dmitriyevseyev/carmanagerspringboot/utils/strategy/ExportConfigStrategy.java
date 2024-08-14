package com.dmitriyevseyev.carmanagerspringboot.utils.strategy;

import com.dmitriyevseyev.carmanagerspringboot.utils.Constants;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class ExportConfigStrategy {

    public Map<String, Integer> getExportConfig() throws StrategyNotFoundException, PropertyFileException {
        Map<String, Integer> exportConfig = new HashMap<>();
        Properties property = new Properties();
        FileInputStream stream = null;
        File file = new File(getClass().getClassLoader().getResource(Constants.PATH_TO_PROPERTIES).getFile());
        try {
            stream = new FileInputStream(file);
            property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(Constants.PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (property.getProperty(Constants.DEALER_EXPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(Constants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerExpStrategy = Integer.parseInt(property.getProperty(Constants.DEALER_EXPORT_STRATEGY));
            exportConfig.put(Constants.DEALER_TYPE, dealerExpStrategy);
        }
        if (property.getProperty(Constants.CAR_EXPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(Constants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int carExpStrategy = Integer.parseInt(property.getProperty(Constants.CAR_EXPORT_STRATEGY));
            exportConfig.put(Constants.CAR_TYPE, carExpStrategy);
        }


        System.out.println("exportConfig - " + exportConfig);



        return exportConfig;
    }

    public Map<String, Integer> getImportConfig() throws StrategyNotFoundException, PropertyFileException {
        Map<String, Integer> importConfig = new HashMap<>();
        Properties property = new Properties();
        FileInputStream stream = null;
        File file = new File(getClass().getClassLoader().getResource(Constants.PATH_TO_PROPERTIES).getFile());
        try {
            stream = new FileInputStream(file);
            property.load(stream);
        } catch (IOException e) {
            throw new PropertyFileException(Constants.PATH_TO_PROPERTIES_NOT_FOUND_EXCEPTION_MESSAGE + e.getMessage());
        }

        if (property.getProperty(Constants.DEALER_IMPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(Constants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int dealerImpStrategy = Integer.parseInt(property.getProperty(Constants.DEALER_IMPORT_STRATEGY));
            importConfig.put(Constants.DEALER_TYPE, dealerImpStrategy);
        }
        if (property.getProperty(Constants.CAR_IMPORT_STRATEGY).isEmpty()) {
            throw new StrategyNotFoundException(Constants.IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } else {
            int carImpStrategy = Integer.parseInt(property.getProperty(Constants.CAR_IMPORT_STRATEGY));
            importConfig.put(Constants.CAR_TYPE, carImpStrategy);
        }


        System.out.println("importConfig - " + importConfig);


        return importConfig;
    }
}
