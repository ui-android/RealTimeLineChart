package com.third.mikephil.charting.interfaces.dataprovider;

import com.third.mikephil.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
