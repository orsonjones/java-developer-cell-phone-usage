package net.afriskito.wcf.cell.report;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import net.afriskito.wcf.cell.report.beans.CellPhone;
import net.afriskito.wcf.cell.report.beans.CellUsage;
import net.afriskito.wcf.cell.report.beans.Details;
import net.afriskito.wcf.cell.report.beans.ReportData;

public class ReportGenerator {
    public static ReportData generate(ImmutableList<CellPhone> cellPhones, ImmutableList<CellUsage> cellUsage, int year) {
        LocalDate reportDate = LocalDate.now();
        Integer phones = cellPhones.size();
        int totalMinutes = 0;
        float totalData = 0;
        float averageMinutes = 0;
        float averageData = 0;
        ImmutableList.Builder<Details> detailsBuilder = ImmutableList.builder();
        
        Set<Integer> employeeIds = new HashSet<>();
        cellPhones.forEach(cell -> employeeIds.add(cell.employeeId()));
        Map<Integer, Map<Month, Integer>> employeeMonthMinutes = new HashMap<>(phones);
        Map<Integer, Map<Month, Float>> employeeMonthData = new HashMap<>(phones);
        for (CellUsage row : cellUsage) {
            Integer employeeId = row.employeeId();
            if (!employeeIds.contains(employeeId))
                continue;
            LocalDate date = row.date();
            if (date.getYear() != year)
                continue;
            Month month = date.getMonth();
            
            Float data = row.data();
            employeeMonthData.computeIfAbsent(employeeId, x -> new HashMap<>(12))
                .merge(month, data, (a, b) -> a + b);
            totalData += data;
            
            Integer minutes = row.minutes();
            employeeMonthMinutes.computeIfAbsent(employeeId, x -> new HashMap<>(12))
                .merge(month, minutes, (a, b) -> a + b);
            totalMinutes += minutes;
        }
        averageMinutes = ((float) totalMinutes) / 12.0f / ((float) phones);
        averageData = ((float) totalData) / 12.0f / ((float) phones);
        
        return ReportData.create(reportDate, phones, totalMinutes, totalData, averageMinutes, averageData, detailsBuilder.build());
    }
}
