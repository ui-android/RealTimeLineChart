package com.third.mikephil.charting.interfaces.dataprovider;

import com.third.mikephil.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
