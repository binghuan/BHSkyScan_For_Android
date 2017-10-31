package com.bh.android.bhskyscan;

import android.util.Log;

import com.bh.android.bhskyscan.data.BoundLeg;
import com.bh.android.bhskyscan.data.Carrier;
import com.bh.android.bhskyscan.data.Currency;
import com.bh.android.bhskyscan.data.Itinerary;
import com.bh.android.bhskyscan.data.Place;
import com.bh.android.bhskyscan.data.PricingOption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by binghuan on 30/10/2017.
 */

class DataParser {

    private final ArrayList<Itinerary> mItineraryList = new ArrayList<Itinerary>();
    private final HashMap<String, BoundLeg> mInboundLegs = new HashMap<String, BoundLeg>();
    private final HashMap<String, BoundLeg> mOutboundLegs = new HashMap<String, BoundLeg>();
    private final HashMap<String, Carrier> mCarriers = new HashMap<String, Carrier>();
    private final HashMap<String, Place> mPlaces = new HashMap<String, Place>();
    private final HashMap<String, Currency> mCurrencies = new HashMap<String, Currency>();

    public ArrayList<Itinerary> getFlightsLivePrices(String result) {

        mItineraryList.clear();

        String currencySymbol = "$";

        boolean DBG = Config.DBG;
        String TAG = "BH_DataParser";
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray currencyObjArray = jsonObject.getJSONArray("Currencies");
            for(int i =0; i< currencyObjArray.length(); i++) {
                JSONObject currencyObj = (JSONObject)currencyObjArray.get(i);

                Currency currency = new Currency();
                currency.code = currencyObj.getString(Currency.CODE);
                currency.symbol = currencyObj.getString(Currency.SYMBOL);

                if(i == 0) {
                    currencySymbol = currency.symbol;
                }

                mCurrencies.put(currency.code, currency);
            }



            JSONArray placeObjArray = jsonObject.getJSONArray("Places");
            for(int i=0; i< placeObjArray.length(); i++) {
                Place place = new Place();

                JSONObject placeObj = (JSONObject)placeObjArray.get(i);
                place.code = placeObj.getString(Place.CODE);
                place.id = placeObj.getString(Place.ID);
                place.name = placeObj.getString(Place.NAME);
                place.type = placeObj.getString(Place.TYPE);

                mPlaces.put(place.id, place);
            }

            JSONArray carrierObjArray = jsonObject.getJSONArray("Carriers");
            for(int i =0; i< carrierObjArray.length(); i++) {
                Carrier carrier = new Carrier();

                JSONObject carrierObj = (JSONObject)carrierObjArray.get(i);
                carrier.code = carrierObj.getString(Carrier.CODE);
                carrier.displayCode = carrierObj.getString(Carrier.DISPLAY_CODE);
                carrier.id = carrierObj.getString(Carrier.ID);
                carrier.imageUrl = carrierObj.getString(Carrier.IMAGE_URL);
                carrier.name = carrierObj.getString(Carrier.NAME);

                mCarriers.put(carrier.id, carrier);
            }

            JSONArray boundLegObjArray = jsonObject.getJSONArray("Legs");
            for(int i = 0; i< boundLegObjArray.length(); i++) {
                BoundLeg boundLeg = new BoundLeg();

                JSONObject boundLegObj = (JSONObject)boundLegObjArray.get(i);
                //boundLeg.arrival = boundLegObj.getString(BoundLeg.ARRIVAL);
                boundLeg.setArrivalDateTime(boundLegObj.getString(BoundLeg.ARRIVAL));
                //boundLeg.departure = boundLegObj.getString(BoundLeg.DEPARTURE);
                boundLeg.setDepartureDateTime(boundLegObj.getString(BoundLeg.DEPARTURE));
                if(boundLeg.departureTime == null) {
                    if(DBG)Log.w(TAG, "boundLeg.departureTime == null");
                }

                boundLeg.directionality = boundLegObj.getString(BoundLeg.DIRECTIONALITY);
                boundLeg.duration = boundLegObj.getInt(BoundLeg.DURATION);
                boundLeg.id = boundLegObj.getString(BoundLeg.ID);
                boundLeg.journeyMode = boundLegObj.getString(BoundLeg.JOURNEY_MODE);

                boundLeg.destinationStation = boundLegObj.getString(BoundLeg.DESTINATION_STATION);
                boundLeg.destinationPlace = mPlaces.get(boundLeg.destinationStation);
                boundLeg.originStation = boundLegObj.getString(BoundLeg.ORIGIN_STATION);
                boundLeg.originPlace = mPlaces.get(boundLeg.originStation);

                JSONArray carriersObjArray = boundLegObj.getJSONArray(BoundLeg.CARRIERS);
                boundLeg.carrierId = String.valueOf(carriersObjArray.get(0));
                boundLeg.carrier = mCarriers.get(boundLeg.carrierId);

                if(boundLeg.directionality.equalsIgnoreCase("outbound")) {
                    mOutboundLegs.put(boundLeg.id, boundLeg);
                } else {
                    mInboundLegs.put(boundLeg.id, boundLeg);
                }
            }

            JSONArray itineraryObjArray = jsonObject.getJSONArray("Itineraries");
            for(int i=0; i< itineraryObjArray.length(); i++) {

                Itinerary itinerary = new Itinerary();

                JSONObject itinerayObj = (JSONObject)itineraryObjArray.get(i);
                itinerary.inboundLegId = itinerayObj.getString(Itinerary.INBOUDLEG_ID);
                itinerary.inboundLeg = mInboundLegs.get(itinerary.inboundLegId);

                itinerary.outboundLegId= itinerayObj.getString(Itinerary.OUTBOUDLEG_ID);
                itinerary.outboundLeg = mOutboundLegs.get(itinerary.outboundLegId);

                JSONArray pricingObjArray = itinerayObj.getJSONArray("PricingOptions");
                for(int j =0; j< pricingObjArray.length(); j++) {
                    PricingOption pricingOption = new PricingOption();

                    JSONObject pricingOptionObj = (JSONObject)pricingObjArray.get(j);
                    pricingOption.price = pricingOptionObj.getDouble(PricingOption.PRICE);
                    pricingOption.quoteAgeInMinutes = pricingOptionObj.getInt(PricingOption.QUOTE_AGE_IN_MINUTES);
                    pricingOption.currencySymbol = currencySymbol;

                    itinerary.pricingOptions.add(pricingOption);
                }

                mItineraryList.add(itinerary);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(DBG) Log.v(TAG, "Get mItineraryList: " +  mItineraryList.size());
        return mItineraryList;

    }

}

