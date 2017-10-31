package com.bh.android.bhskyscan;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BH_Lin on 30/10/2017.
 */

public class SearchArgs {
    public final static String COUNTRY = "country";
    public final static String CURRENCY = "currency";
    public final static String LOCALE = "locale";
    public final static String ORIGIN_PLACE = "originPlace";
    public final static String DESTINATION_PLACE = "destinationPlace";
    public final static String OUTBOUND_DATE = "outboundDate";
    public final static String INBOUND_DATE = "inboundDate";
    public final static String ADULTS = "adults";
    public final static String APIKEY = "apikey";

    String country;
    String currency;
    String locale;
    String originPlace;
    String destinationPlace;
    String outboundDate;
    String inboundDate;
    int adults;
    String apikey;

    public void setupDefaultArgs() {
        this.country = "UK";
        this.currency = "GBP";
        this.locale = "en-GB";
        this.originPlace = "EDI-sky";
        this.destinationPlace = "LOND-sky";
        this.outboundDate = "2017-11-05";
        this.inboundDate = "2017-11-06";
        this.adults = 1;
        this.apikey = "ss630745725358065467897349852985";
    }

    public String getRequestString() {

        String requestBody = "";

        requestBody = COUNTRY + "=" + this.country +
                "&" + CURRENCY + "=" + this.currency +
                "&" + LOCALE + "="+ this.locale +
                "&" + ORIGIN_PLACE + "="+ this.originPlace +
                "&" + destinationPlace + "="+ this.destinationPlace +
                "&" + outboundDate + "="+ this.outboundDate +
                "&" + inboundDate + "="+ this.inboundDate +
                "&" + adults + "="+ this.adults +
                "&" + apikey + "="+ this.apikey ;


        return requestBody;
    }

    public JSONObject getParametersJSONObj() {
        JSONObject parametersJsonObj = new JSONObject(this.getParametersMap());
        return parametersJsonObj;
    }

    public Map<String,String> getParametersMap() {
        Map<String,String> params = new HashMap<String, String>();
        params.put(SearchArgs.COUNTRY,this.country);
        params.put(SearchArgs.CURRENCY,this.currency);
        params.put(SearchArgs.LOCALE,this.locale);
        params.put(SearchArgs.ORIGIN_PLACE,this.originPlace);
        params.put(SearchArgs.DESTINATION_PLACE,this.destinationPlace);
        params.put(SearchArgs.OUTBOUND_DATE,this.outboundDate);
        params.put(SearchArgs.INBOUND_DATE,this.inboundDate);
        params.put(SearchArgs.ADULTS, String.valueOf(this.adults));
        params.put(SearchArgs.APIKEY,this.apikey);

        return params;
    }

}
