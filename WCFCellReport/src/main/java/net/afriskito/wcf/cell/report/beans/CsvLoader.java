package net.afriskito.wcf.cell.report.beans;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvLoader {
    public static List<CellPhone> loadEmployeePhones(File file) throws LoaderException {
        try {
            List<CellPhone> cellPhones = (List) new CsvToBeanBuilder(new FileReader(file))
                    .withType(CellPhoneBean.class)
                    .build().<CellPhoneBean>parse()
                    .stream()
                    .map(bean -> CellPhone.create((CellPhoneBean) bean))
                    .collect(Collectors.toCollection(ArrayList::new));
            return cellPhones;
        } catch (FileNotFoundException ex) {
            throw new LoaderException("Error loading employee phones", ex);
        }
    }
    
    public static List<CellUsage> loadPhoneData(File file) throws LoaderException {
        try {
            List<CellUsage> cellPhones = (List) new CsvToBeanBuilder(new FileReader(file))
                    .withType(CellUsageBean.class)
                    .build().<CellUsageBean>parse()
                    .stream()
                    .map(bean -> CellUsage.create((CellUsageBean) bean))
                    .collect(Collectors.toCollection(ArrayList::new));
            return cellPhones;
        } catch (FileNotFoundException ex) {
            throw new LoaderException("Error loading employee phones", ex);
        }
    }
    
    public static class LoaderException extends Exception {
        public LoaderException(String message) {
            super(message);
        }        
        public LoaderException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
