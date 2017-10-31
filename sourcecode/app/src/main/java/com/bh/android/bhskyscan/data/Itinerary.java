package com.bh.android.bhskyscan.data;

import java.util.ArrayList;

/**
 * Created by BH_Lin on 31/10/2017.
 */

public class Itinerary {

    public static final String ITINERARY = "Itinerary";

    public static final String INBOUDLEG_ID = "InboundLegId";
    public static final String OUTBOUDLEG_ID = "OutboundLegId";
    public String inboundLegId;
    public String outboundLegId;

    public BoundLeg inboundLeg;
    public BoundLeg outboundLeg;

    public final ArrayList<PricingOption> pricingOptions = new ArrayList<PricingOption>();
}
