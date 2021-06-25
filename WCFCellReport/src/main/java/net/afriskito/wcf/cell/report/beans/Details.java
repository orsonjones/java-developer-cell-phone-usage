package net.afriskito.wcf.cell.report.beans;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Details {
    public static Details create(
            CellPhone cellPhone,
            ImmutableList<Integer> monthlyMinutes,
            ImmutableList<Double> monthlyData)
    {
        return new AutoValue_Details(cellPhone, monthlyMinutes, monthlyData);
    }
    
    public abstract CellPhone cellPhone();
    public abstract ImmutableList<Integer> monthlyMinutes();
    public abstract ImmutableList<Double> monthlyData();
}
