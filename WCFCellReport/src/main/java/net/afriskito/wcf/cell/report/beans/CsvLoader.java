package net.afriskito.wcf.cell.report.beans;

import com.google.common.collect.ImmutableList;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CsvLoader {
    public static ImmutableList<CellPhone> loadEmployeePhones(File file) throws LoaderException {
        try {
            ImmutableList.Builder<CellPhone> builder = ImmutableList.builder();
            new CsvToBeanBuilder(new FileReader(file))
                    .withType(CellPhoneBean.class)
                    .build()
                    .<CellPhoneBean>parse()
                    .stream()
                    .map(bean -> CellPhone.create((CellPhoneBean) bean))
                    .forEachOrdered(entry -> builder.add((CellPhone) entry));
            return builder.build();
        } catch (FileNotFoundException | NullPointerException ex) {
            throw new LoaderException("Error loading employee phones", ex);
        }
    }
    
    public static ImmutableList<CellUsage> loadPhoneData(File file) throws LoaderException {
        try {
            ImmutableList.Builder<CellUsage> builder = ImmutableList.builder();
            new CsvToBeanBuilder(new FileReader(file))
                    .withType(CellUsageBean.class)
                    .build()
                    .<CellUsageBean>parse()
                    .stream()
                    .map(bean -> CellUsage.create((CellUsageBean) bean))
                    .forEachOrdered(entry -> builder.add((CellUsage) entry));
            return builder.build();
        } catch (FileNotFoundException | NullPointerException ex) {
            throw new LoaderException("Error loading phone data", ex);
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
