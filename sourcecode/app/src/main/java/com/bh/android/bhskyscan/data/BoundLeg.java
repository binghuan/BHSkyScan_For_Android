package com.bh.android.bhskyscan.data;

/**
 * Created by BH_Lin on 31/10/2017.
 */

public class BoundLeg {
    public final static String ARRIVAL = "Arrival";
    public final static String DEPARTURE = "Departure";
    public final static String DESTINATION_STATION = "DestinationStation";
    public final static String DIRECTIONALITY = "Directionality";
    public final static String DURATION = "Duration";
    public final static String ID = "Id";
    public final static String JOURNEY_MODE = "JourneyMode";
    public final static String ORIGIN_STATION = "OriginStation";
    public final static String CARRIERS = "Carriers";

    public Carrier carrier;
    public String carrierId;

    private String arrival;
    public String getArrival() {
        return this.arrival;
    }
    private String arrivalDate;
    public String getArrivalDate() {
        return this.arrivalDate;
    }
    public String arrivalTime;
    public void setArrivalDateTime(String dateTime) {
        this.arrival = dateTime;
        if(this.arrival != null) {
            String[] items = this.arrival.split("T");
            this.arrivalDate = items[0];
            String[] times = items[1].split(":");
            this.arrivalTime = times[0] + ":" + times[1];
        }
    }

    private String departure;
    public String getDeparture() {
        return this.departure;
    }
    public void setDepartureDateTime(String dateTime) {
        this.departure = dateTime;
        if(this.departure != null) {
            String[] items = this.departure.split("T");
            this.departureDate = items[0];
            String[] times = items[1].split(":");
            this.departureTime = times[0] + ":" + times[1];
        }
    }
    public String departureTime;
    private String departureDate;
    public String getDepartureDate() {
        return this.departureDate;
    }
    public String destinationStation;
    public Place destinationPlace;

    public String directionality;
    public int duration;
    public String id;
    public String journeyMode;
    public String originStation;
    public Place originPlace;
}
