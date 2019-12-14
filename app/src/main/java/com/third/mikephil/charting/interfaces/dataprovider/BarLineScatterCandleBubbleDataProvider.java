package com.third.mikephil.charting.interfaces.dataprovider;

import com.third.mikephil.charting.components.YAxis.AxisDependency;
import com.third.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.third.mikephil.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
