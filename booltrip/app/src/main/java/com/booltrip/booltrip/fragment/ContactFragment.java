package com.booltrip.booltrip.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.booltrip.booltrip.CommonUtilities;
import com.booltrip.booltrip.MainActivity;
import com.booltrip.booltrip.R;
import com.booltrip.booltrip.adapter.TrackListAdapter;
import com.booltrip.booltrip.model.Track;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactFragment extends Fragment {
    private static View                     view;
    private OnFragmentInteractionListener   mListener;
    private String                          name="";

    public ContactFragment() {
        // Required empty public constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        ((TextView) view.findViewById(R.id.ContactName)).setText(this.name);
        ((ImageButton)view.findViewById(R.id.sendButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              sendEmail();
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT,"Kevin veut booltriper avec vous");
        i.putExtra(Intent.EXTRA_TEXT, "Bonjour "+this.name+ "\n");
        i.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { this.name+ "@booltrip.bzh" });
        //String URL = getString(R.string.url_website) + "?android=true&deviceId=" + user.getDeviceId();
        //i.setData(Uri.parse(URL));
        startActivity(Intent.createChooser(i, "Email:"));
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
