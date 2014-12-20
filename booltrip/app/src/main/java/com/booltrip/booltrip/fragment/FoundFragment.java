package com.booltrip.booltrip.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.booltrip.booltrip.CommonUtilities;
import com.booltrip.booltrip.MainActivity;
import com.booltrip.booltrip.R;
import com.booltrip.booltrip.adapter.TrackListAdapter;
import com.booltrip.booltrip.model.Track;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FoundFragment extends Fragment {
    private String mParam1;
    private String mParam2;
    private static View view;
    private TrackListAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public static FoundFragment newInstance(String param1, String param2) {
        FoundFragment fragment = new FoundFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public FoundFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_found, container, false);

        getFoundedTrack();

        return view;
    }

    private void getFoundedTrack() {
        CommonUtilities.get("/find", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray tracks) {

                String tweetText = null;
                ArrayList<Track> foundedTrack = new ArrayList<Track>();
                try {
                    for (int i= 0; i< tracks.length(); i++) {
                        JSONObject track = tracks.getJSONObject(i);
                        Track trackobj = new Track();
                        trackobj.setName(track.getString("name"));
                        trackobj.setConsumption(track.getInt("cost"));
                        foundedTrack.add(trackobj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView text = (TextView) view.findViewById(R.id.booltripCounter);
                String txt = "A identifié "+tracks.length()+" booltrips";
                text.setText(txt);
                createList(foundedTrack);
            }
        });
    }

    public void createList(ArrayList<Track> tracks) {
        ListView mList = (ListView) view.findViewById(R.id.TrackList);
        adapter = new TrackListAdapter(view.getContext(), tracks);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ((MainActivity)getActivity()).displayMap((Track)(adapter.getItem(i)));
            }
        });
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