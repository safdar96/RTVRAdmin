package com.example.safdar.rtvradmin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safdar.rtvradmin.R;
import com.example.safdar.rtvradmin.data.AuthorisedEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AuthorisedEntriesListAdapter extends RecyclerView.Adapter<AuthorisedEntriesListAdapter.AuthorisedEntriesItemViewHolder> {


    ArrayList<AuthorisedEntry> mList;

    public AuthorisedEntriesListAdapter(ArrayList<AuthorisedEntry> list) {
        mList = list;
    }
    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMM yyyy");

    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss aa");
    @NonNull
    @Override
    public AuthorisedEntriesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.authorised_entry_item, parent, false);
        return new AuthorisedEntriesItemViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorisedEntriesItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(AuthorisedEntry AuthorisedEntry) {
        mList.add(AuthorisedEntry);
        Log.v("Saf", "added");
        notifyDataSetChanged();
    }


    class AuthorisedEntriesItemViewHolder extends RecyclerView.ViewHolder {


        private final Context mContext;
        TextView mOwner, mTime, mLicense, mDate;
        public AuthorisedEntriesItemViewHolder(View itemView, Context context) {
            super(itemView);
            mOwner = itemView.findViewById(R.id.owner);
            mLicense = itemView.findViewById(R.id.unauth_license_no);
            mTime = itemView.findViewById(R.id.unauth_time);
            mDate = itemView.findViewById(R.id.unauth_date);
            mContext = context;
        }

        public void bind(int pos) {
            AuthorisedEntry authorisedEntry = mList.get(pos);
            Log.v("Saf", "entry :" + authorisedEntry.getName());
            mOwner.setText(authorisedEntry.getName());
            String time = timeFormatter.format(new Date(authorisedEntry.getTime()*1000));
            mTime.setText(time);
            mLicense.setText(authorisedEntry.getLicenseNo());
            String date = dateFormatter.format(new Date(authorisedEntry.getTime()*1000));
            mDate.setText(date);
        }
    }
}
