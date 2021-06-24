package net.afriskito.wcf.cell.report.beans;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class CellPhone {
    public static CellPhone create(CellPhoneBean bean) {
        return new AutoValue_CellPhone(bean.employeeId, bean.employeeName, bean.purchaseDate, bean.model);
    }
    
    abstract Integer employeeId();
    abstract String employeeName();
    abstract LocalDate purchaseDate();
    abstract String model();
}
