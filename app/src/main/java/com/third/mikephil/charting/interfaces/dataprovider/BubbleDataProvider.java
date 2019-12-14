package com.third.mikephil.charting.interfaces.dataprovider;

import com.third.mikephil.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
