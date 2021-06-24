package net.afriskito.wcf.cell.report;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.afriskito.wcf.cell.report.beans.CellPhoneBean;
import net.afriskito.wcf.cell.report.beans.CellPhone;
import net.afriskito.wcf.cell.report.beans.CellUsage;
import net.afriskito.wcf.cell.report.beans.CellUsageBean;

public class CellReportMain {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello World");
        List<CellPhone> cellPhones = (List) new CsvToBeanBuilder(new FileReader("..\\CellPhone.csv"))
                .withType(CellPhoneBean.class)
                .build().<CellPhoneBean>parse()
                .stream()
                .map(bean -> CellPhone.create((CellPhoneBean) bean))
                .collect(Collectors.toCollection(ArrayList::new));
        cellPhones.stream()
                .forEachOrdered(phone -> System.out.println(phone));
        
        List<CellUsage> cellUsage = (List) new CsvToBeanBuilder(new FileReader("..\\CellPhoneUsageByMonth.csv"))
                .withType(CellUsageBean.class)
                .build().<CellUsageBean>parse()
                .stream()
                .map(bean -> CellUsage.create((CellUsageBean) bean))
                .collect(Collectors.toCollection(ArrayList::new));
        cellUsage.stream()
                .forEachOrdered(usage -> System.out.println(usage));
    }
}
