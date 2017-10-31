package com.bh.android.bhskyscan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.android.bhskyscan.data.Itinerary;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlightListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlightListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlightListFragment extends Fragment {

    private final boolean DBG = Config.DBG;
    private final String LOG_TAG = "BH_FlightListFragment";
    //private ArrayList<Quote> mQuoteList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public FlightListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FlightListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FlightListFragment newInstance(String param1, String param2) {
        FlightListFragment fragment = new FlightListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DBG) Log.v(LOG_TAG, "+++ onCreate +++");

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (DBG) Log.v(LOG_TAG, "--- onCreate ---");
    }

    private RecyclerView.Adapter mAdapter = null;
    private Context mContext = null;
    private TextView mProgressMsg = null;
    private View mProgressArea = null;
    private TextView mResultInfoTextView = null;

    private void showProgressBar(boolean isShowed, String message) {
        if (isShowed) {
            mProgressArea.setVisibility(View.VISIBLE);

            if (message != null) {
                mProgressMsg.setText(message);
            }


        } else {
            mProgressArea.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (DBG) Log.v(LOG_TAG, "+++ onCreateView +++");

        // Inflate the layout for this fragment
        mContext = this.getContext();

        View view = inflater.inflate(R.layout.fragment_flight_list, container, false);


        mProgressArea = view.findViewById(R.id.progress_bar);
        mProgressMsg = view.findViewById(R.id.progress_msg);
        mResultInfoTextView = view.findViewById(R.id.result_info);

        RecyclerView mRecyclerView = view.findViewById(R.id.list_view);
        mAdapter = new MyAdapter();
        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        if (DBG) Log.v(LOG_TAG, "--- onCreateView ---");
        return view;
    }

    private DataRequester mDataRequester = null;
    private DataParser mDataParser = null;

    private ArrayList<Itinerary> mItineraryList = new ArrayList<Itinerary>();

    @Override
    public void onResume() {
        super.onResume();
        if (DBG) Log.v(LOG_TAG, "+++ onResume +++");

        if (mDataRequester == null) {
            mDataRequester = new DataRequester(getContext());
        }
        if (mDataParser == null) {
            mDataParser = new DataParser();
        }

        final DataRequester dataRequester = new DataRequester(mContext);
        if (DBG) Log.d(LOG_TAG, ">>@Server+ReportAppInstalledForMapping");

        final SearchArgs searchArgs = new SearchArgs();
        searchArgs.setupDefaultArgs();

        //20171031@BH_Lin: ---------------------------------------------------->
        // purpose: Task - departing next Monday and returning the following day.
        Date nextMonday = MyUtils.getNextMonday();

        String nextMondayString = MyUtils.getDateByFormatYYYYMMDD(nextMonday);
        Date followingDay = MyUtils.getFollowingDay(nextMonday);
        String followingDayString = MyUtils.getDateByFormatYYYYMMDD(followingDay);

        searchArgs.outboundDate = nextMondayString;
        searchArgs.inboundDate = followingDayString;
        //20171031@BH_Lin: ----------------------------------------------------<

        showProgressBar(true, "Step 1: Creating the session");
        dataRequester.httpPost(Config.URL_CREATE_SESSION, searchArgs.getParametersJSONObj(), new DataCallback() {
            @Override
            public void onCallback(String result) {
                if (DBG)
                    Log.d(LOG_TAG, ">>@Server+ReportAppInstalledForMapping: " + result);

                showProgressBar(true, "Step 2: Polling the results.");
                dataRequester.httpGet(Config.getUrlForPollingSession(dataRequester.sessionKey, searchArgs.apikey),
                        new DataCallback() {
                            @Override
                            public void onCallback(String result) {
                                if (DBG)
                                    Log.d(LOG_TAG, ">>@Server+PollingSession: " + result);
                                showProgressBar(true, "Step 3: Parsing Data.");
                                mItineraryList = mDataParser.getFlightsLivePrices(result);

                                showProgressBar(false, null);
                                showResult();
                                mAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });

    }

    private void showResult() {
        mResultInfoTextView.setText(
                mItineraryList.size() + " results showen"
        );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (DBG) Log.v(LOG_TAG, ">>> onCreateViewHolder");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.flight_info, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Itinerary itinerary = mItineraryList.get(position);

            if (itinerary != null) {


                holder.outBoundDateTimeText.setText(
                        itinerary.outboundLeg.departureTime + " - " + itinerary.outboundLeg.arrivalTime
                );
                holder.outBoundDateTimeDescription.setText(
                        itinerary.outboundLeg.originPlace.code + "-" +
                                itinerary.outboundLeg.destinationPlace.code +
                                ", " + itinerary.outboundLeg.carrier.name
                );

                holder.inBoundDateTimeText.setText(
                        itinerary.inboundLeg.departureTime + " - " + itinerary.inboundLeg.arrivalTime
                );
                holder.inBoundDateTimeDescription.setText(
                        itinerary.inboundLeg.originPlace.code + "-" +
                                itinerary.inboundLeg.destinationPlace.code +
                                ", " + itinerary.inboundLeg.carrier.name
                );

                holder.outBoundDurationText.setText(itinerary.outboundLeg.duration + "m");
                holder.inBoundDurationText.setText(itinerary.inboundLeg.duration + "m");
                holder.priceTextView.setText(
                        itinerary.pricingOptions.get(0).currencySymbol +
                                String.valueOf(itinerary.pricingOptions.get(0).price)
                );

                new ImageDownloader(mContext, holder.outBoundCarrierIcon)
                        .execute(itinerary.outboundLeg.carrier.imageUrl);
                new ImageDownloader(mContext, holder.inBoundCarrierIcon)
                        .execute(itinerary.inboundLeg.carrier.imageUrl);

            }

        }

        @Override
        public int getItemCount() {
            if (DBG) Log.v(LOG_TAG, ">> getItemCount:" + mItineraryList.size());

            return mItineraryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            //public final ImageView iconForOutbound;
            //public final ImageView iconForInbound;
            public final TextView outBoundDateTimeText;
            public final TextView outBoundDateTimeDescription;

            public final TextView inBoundDateTimeText;
            public final TextView inBoundDateTimeDescription;

            public final TextView outBoundDurationText;
            public final TextView inBoundDurationText;

            public final ImageView outBoundCarrierIcon;
            public final ImageView inBoundCarrierIcon;

            public final TextView priceTextView;

            public ViewHolder(View itemView) {
                super(itemView);

                outBoundDateTimeText = itemView.findViewById(R.id.outbound_datetime);
                outBoundDateTimeDescription = itemView.findViewById(R.id.outbound_description);

                inBoundDateTimeText = itemView.findViewById(R.id.inbound_datetime);
                inBoundDateTimeDescription = itemView.findViewById(R.id.inbound_description);

                outBoundDurationText = itemView.findViewById(R.id.outbound_duration);
                inBoundDurationText = itemView.findViewById(R.id.inbound_duration);

                outBoundCarrierIcon = itemView.findViewById(R.id.out_carrier_icon);
                inBoundCarrierIcon = itemView.findViewById(R.id.in_carrier_icon);

                priceTextView = itemView.findViewById(R.id.price_text);

            }
        }
    }

}
