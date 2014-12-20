package com.booltrip.booltrip.fragment;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.booltrip.booltrip.R;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.booltrip.booltrip.MyLocationListener;

import com.booltrip.booltrip.CommonUtilities;

import org.apache.http.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetupFragment} factory method to
 * create an instance of this fragment.
 */
public class SetupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SetupFragment";
    private boolean isConnected = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LocationManager locationManager;

    private OnFragmentInteractionListener mListener;

    public SetupFragment() {
        // Required empty public constructor
        getUsers();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_setup, container, false);
        locationManager = (LocationManager) rootview.getContext().getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new MyLocationListener();
        final RelativeLayout connectionLayout = (RelativeLayout)rootview.findViewById(R.id.ConnectionFrame);
        final RelativeLayout disconnectionLayout = (RelativeLayout)rootview.findViewById(R.id.DisconnectionFrame);

        // Inflate the layout for this fragment
        ImageButton connect = (ImageButton) rootview.findViewById(R.id.connectButton);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectionLayout.setVisibility(View.VISIBLE);
                connectionLayout.setVisibility(View.INVISIBLE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, locationListener);
            }
        });

        ImageButton disconnectButton = (ImageButton) rootview.findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectionLayout.setVisibility(View.INVISIBLE);
                connectionLayout.setVisibility(View.VISIBLE);
                locationManager.removeUpdates(locationListener);
            }
        });

        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void getUsers () {
        CommonUtilities.get("/users", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                JSONObject firstEvent = null;
                String tweetText = null;
                try {
                    firstEvent = timeline.getJSONObject(0);
                    tweetText = firstEvent.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(tweetText);
            }
        });
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
