package net.afriskito.wcf.cell.report.beans;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class CellUsage {
    public static CellUsage create(CellUsageBean bean) {
        return new AutoValue_CellUsage(bean.employeeId, bean.date, bean.minutes, bean.data);
    }
    
    abstract Integer employeeId();
    abstract LocalDate date();
    abstract Integer minutes();
    abstract Float data();
}
