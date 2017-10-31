package com.bh.android.bhskyscan;

/**
 * Created by binghuan on 1/8/15.
 */
public class Config {
    public static final boolean DBG = true;

    public static String URL_DOMAIN = "http://partners.api.skyscanner.net/apiservices";
    public static String URL_CREATE_SESSION = URL_DOMAIN + "/pricing/v1.0";
    public static String getUrlForPollingSession(String sessinoKey, String apikey) {
        String url = URL_CREATE_SESSION + "/" + sessinoKey + "?apikey=" + apikey;
        return url;
    }
}
