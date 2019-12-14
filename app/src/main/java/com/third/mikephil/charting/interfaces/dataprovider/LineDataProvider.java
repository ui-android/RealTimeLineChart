package com.third.mikephil.charting.interfaces.dataprovider;

import com.third.mikephil.charting.components.YAxis;
import com.third.mikephil.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
