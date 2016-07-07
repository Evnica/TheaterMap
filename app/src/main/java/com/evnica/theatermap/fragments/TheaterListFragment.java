package com.evnica.theatermap.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evnica.theatermap.ContentRequest;
import com.evnica.theatermap.R;
import com.evnica.theatermap.Theater;
import com.evnica.theatermap.TheaterArrayAdapter;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 03.07.2016.
 * Version: 0.1
 */

public class TheaterListFragment extends Fragment {

    private ListView mMainListView = null;
    private static ArrayList<Theater> theaters;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View listFragmentView = inflater.inflate(R.layout.fragment_theaterlist, container, false);

        mMainListView = (ListView) listFragmentView.findViewById(R.id.mainListView);
        //mMainListView.setOnItemClickListener();
        fillWithContent();

        return listFragmentView;
    }

    public void fillWithContent()
    {
        new DataGetter().execute();
    }

    private class DataGetter extends AsyncTask<String, Void, ArrayList<Theater>>{

        @Override
        protected ArrayList<Theater> doInBackground(String... params) {
            theaters = ContentRequest.retrieveContent();
            return theaters;
        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theaters) {
            mMainListView.setAdapter(new TheaterArrayAdapter(getActivity(), theaters));
        }
    }

    public static ArrayList<Theater> getTheaters() {
        return theaters;
    }


}
