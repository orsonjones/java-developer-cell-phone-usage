package net.afriskito.wcf.cell.report.beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;

public class CellUsageBean {
    @CsvBindByName(column="emplyeeId")
    Integer employeeId;
    @CsvBindByName
    @CsvDate("M/d/yyyy")
    LocalDate date;
    @CsvBindByName(column="totalMinutes")
    Integer minutes;
    @CsvBindByName(column="totalData")
    Float data;
}
