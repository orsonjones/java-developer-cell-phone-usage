package net.afriskito.wcf.cell.report.beans;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.time.LocalDate;

@AutoValue
public abstract class ReportData {
    public static ReportData create(
            LocalDate reportDate,
            Integer phones,
            Integer totalMinutes,
            Float totalData,
            Float averageMinutes,
            Float averageData,
            ImmutableList<Details> details)
    {
        return new AutoValue_ReportData(reportDate, phones, totalMinutes, totalData, averageMinutes, averageData, details);
    }
    
    //header
    public abstract LocalDate reportDate();
    public abstract Integer phones();
    public abstract Integer totalMinutes();
    public abstract Float totalData();
    public abstract Float averageMinutes();
    public abstract Float averageData();
    //details
    public abstract ImmutableList<Details> details();
}
