package net.afriskito.wcf.cell.report.beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

public class CellPhoneBean {
    @CsvBindByName
    Integer employeeId;
    @CsvBindByName
    String employeeName;
    @CsvBindByName
    @CsvDate("yyyyMMdd")
    LocalDate purchaseDate;
    @CsvBindByName
    String model;
}
