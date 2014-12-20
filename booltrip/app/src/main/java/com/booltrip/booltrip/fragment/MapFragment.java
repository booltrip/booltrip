package com.booltrip.booltrip.fragment;

import android.graphics.Color;
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
import com.booltrip.booltrip.adapter.UserListAdapter;
import com.booltrip.booltrip.model.Track;
import com.booltrip.booltrip.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment {
    private static View                         view;
    MapView                                     mMapView;
    private GoogleMap                           googleMap;
    private HashMap<String, PolylineOptions >   othersTrack;
    private UserListAdapter                     adapter;
    private int                                 length = 0;
    private Polyline                            currentOtherTrack = null;
    private Marker                              currentOtherTrackMarker2= null;
    private Marker                              currentOtherTrackMarker1= null;
    private User                                currentOtherUser = null;
    public  String                              trackName= "";
    public  String                              cost = "";
    private OnFragmentInteractionListener       mListener;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public MapFragment() {
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
        view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();
        ((TextView)view.findViewById(R.id.SelectedTrackTitle)).setText(this.trackName);
        ((TextView)view.findViewById(R.id.SelectedTrackCost)).setText(this.cost);
        getMyTrack();

        return view;
    }

    private void getMyTrack() {
        CommonUtilities.get("/usertracks/Kevin", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray coordinates = response.getJSONArray("values");
                    PolylineOptions  rectOptions = new PolylineOptions ();
                    LatLng middlePosition = null;
                    LatLng startPosition = null;
                    LatLng lastPosition = null;
                    for (int i = 0; i < coordinates.length(); i++) {
                        JSONArray points = coordinates.getJSONArray(i);
                        if (points.length() == 2)
                            rectOptions.add(new LatLng(points.getDouble(0),points.getDouble(1)));
                        if (i == ((int)(coordinates.length() / 2))) {
                            middlePosition = new LatLng(points.getDouble(0),points.getDouble(1));
                        } else if (i == 0) {
                            startPosition = new LatLng(points.getDouble(0),points.getDouble(1));
                        } else if (i == coordinates.length() - 1) {
                            lastPosition = new LatLng(points.getDouble(0),points.getDouble(1));
                        }
                    }
                    rectOptions.color(Color.rgb(97,61,144));
                    rectOptions.width(20);
                    googleMap.addPolyline(rectOptions);

                    googleMap.addMarker(new MarkerOptions()
                            .position(startPosition)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marqueur_violet))
                            .title("Debut"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(lastPosition)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marqueur_violet))
                            .title("Fin"));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(middlePosition).zoom(10).build();
                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));
                    getMatching();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createList() {
        ArrayList<User> users = new ArrayList<User>();
        List<String> list = new ArrayList<String>(othersTrack.keySet());
        for (int i = 0; i <  list.size(); i++) {
            User u = new User();
            u.setName(list.get(i));
            users.add(u);
        }

        ListView mList = (ListView) view.findViewById(R.id.UserList);
        adapter = new UserListAdapter(view.getContext(), users);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String username = ((User)(adapter.getItem(i))).getName();
                if (currentOtherUser == (User)(adapter.getItem(i))) {
                    ((MainActivity)getActivity()).displayContact(currentOtherUser);
                    return;
                }
                currentOtherUser = (User)(adapter.getItem(i));

                if (currentOtherTrack != null) {
                    currentOtherTrack.remove();
                }
                if (currentOtherTrackMarker1 != null) {
                    currentOtherTrackMarker1.remove();
                }
                if (currentOtherTrackMarker2 != null) {
                    currentOtherTrackMarker2.remove();
                }
                PolylineOptions rectOptions = othersTrack.get(username);
                rectOptions.color(Color.rgb(152, 194, 28));
                rectOptions.zIndex(30);
                currentOtherTrack = googleMap.addPolyline(rectOptions);

                currentOtherTrackMarker1 = googleMap.addMarker(new MarkerOptions()
                        .position(rectOptions.getPoints().get( rectOptions.getPoints().size() - 1))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marqueur_vert))
                        .title("Fin"));
                currentOtherTrackMarker2 = googleMap.addMarker(new MarkerOptions()
                        .position(rectOptions.getPoints().get(0))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marqueur_vert))
                        .title("Début"));
            }
        });
    }

    private void getMatching() {
        othersTrack = new HashMap<String, PolylineOptions >();
        CommonUtilities.get("/find", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray matchs) {
                try {
                    for (int i = 0; i < matchs.length(); i++) {
                        JSONObject match = matchs.getJSONObject(i);
                        getTrack(match.getString("name"));
                    }
                    length = matchs.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTrack(String trackName) {

        CommonUtilities.get("/tracks/"+trackName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray coordinates = response.getJSONArray("values");
                    PolylineOptions  rectOptions = new PolylineOptions ();
                    for (int i = 0; i < coordinates.length(); i++) {
                        JSONArray points = coordinates.getJSONArray(i);
                        if (points.length() == 2)
                            rectOptions.add(new LatLng(points.getDouble(0),points.getDouble(1)));
                    }
                    othersTrack.put(response.getString("user"),rectOptions );
                    if (othersTrack.size() == length)
                        createList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        public void onFragmentInteraction(Uri uri);
    }

}
