package com.evnica.theatermap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Evnica on 06.07.2016.
 */
public class TheaterArrayAdapter extends ArrayAdapter<Theater> {

    private Context mContext;
    private ArrayList<Theater> mTheaters;

    public TheaterArrayAdapter(Context context, ArrayList<Theater> theaters){
        super(context, R.layout.theater_row_layout, theaters);
        mContext = context;
        mTheaters = theaters;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.theater_row_layout, parent, false);
        TextView theaterAddress = (TextView)rowView.findViewById(R.id.theaterAddress);
        TextView theaterName = (TextView)rowView.findViewById(R.id.theaterName);

        Theater currentTheater = mTheaters.get(position);
        theaterName.setText(currentTheater.getmTheaterName());
        theaterAddress.setText(currentTheater.getmTheaterLocation());

        return rowView;
    }
}
