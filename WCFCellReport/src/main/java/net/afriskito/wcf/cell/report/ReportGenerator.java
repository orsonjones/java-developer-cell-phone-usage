package net.afriskito.wcf.cell.report;

import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.afriskito.wcf.cell.report.beans.CellPhone;
import net.afriskito.wcf.cell.report.beans.CellUsage;
import net.afriskito.wcf.cell.report.beans.Details;
import net.afriskito.wcf.cell.report.beans.ReportData;
import org.apache.commons.collections.keyvalue.MultiKey;

public class ReportGenerator {
    public static ReportData generate(ImmutableList<CellPhone> cellPhones, ImmutableList<CellUsage> cellUsage, int year) {
        LocalDate reportDate = LocalDate.now();
        Integer phones = cellPhones.size();
        int totalMinutes = 0;
        float totalData = 0;
        float averageMinutes = 0;
        float averageData = 0;
        ImmutableList.Builder<Details> detailsBuilder = ImmutableList.builder();
        
        Map<Integer, CellPhone> employeeIdPhoneMap = new HashMap<>();
        cellPhones.forEach(cell -> employeeIdPhoneMap.put(cell.employeeId(), cell));
        Set<MultiKey> datesParsed = new HashSet<>();
        Map<Integer, Map<Month, Float>> employeeMonthData = new HashMap<>(phones);
        Map<Integer, Map<Month, Integer>> employeeMonthMinutes = new HashMap<>(phones);
        for (CellUsage row : cellUsage) {
            Integer employeeId = row.employeeId();
            if (!employeeIdPhoneMap.containsKey(employeeId))
                continue;
            LocalDate date = row.date();
            if (date.getYear() != year)
                continue;
            Month month = date.getMonth();
            if (!datesParsed.add(new MultiKey(employeeId, month, date.getDayOfMonth())))
                continue;
            
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
        
        employeeIdPhoneMap.keySet().stream().sorted()
                .map(id -> generateDetails(employeeIdPhoneMap.get(id), employeeMonthData.get(id), employeeMonthMinutes.get(id)))
                .forEach(detailsBuilder::add);
        
        return ReportData.create(reportDate, phones, totalMinutes, totalData, averageMinutes, averageData, detailsBuilder.build());
    }

    private static Details generateDetails(CellPhone cellPhone, Map<Month, Float> monthData, Map<Month, Integer> monthMinutes) {
        ImmutableList.Builder<Integer> minutesBuilder = ImmutableList.builderWithExpectedSize(12);
        ImmutableList.Builder<Float> dataBuilder = ImmutableList.builderWithExpectedSize(12);
        for (Month month : Month.values()) {
            minutesBuilder.add(monthMinutes.getOrDefault(month, 0));
            dataBuilder.add(monthData.getOrDefault(month, 0f));
        }
        return Details.create(cellPhone, minutesBuilder.build(), dataBuilder.build());
    }
}
