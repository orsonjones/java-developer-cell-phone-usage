package net.afriskito.wcf.cell.report.beans;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class CellPhone {
    public static CellPhone create(CellPhoneBean bean) {
        return new AutoValue_CellPhone(bean.employeeId, bean.employeeName, bean.purchaseDate, bean.model);
    }
    
    public abstract Integer employeeId();
    public abstract String employeeName();
    public abstract LocalDate purchaseDate();
    public abstract String model();
}
