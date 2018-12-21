package com.example.safdar.rtvradmin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safdar.rtvradmin.R;
import com.example.safdar.rtvradmin.data.AuthorisedLicenseNo;

import java.util.ArrayList;

public class AuthorisedLicenseNoListAdapter extends RecyclerView.Adapter<AuthorisedLicenseNoListAdapter.AuthorisedLicenseNoItemViewHolder> {


    ArrayList<AuthorisedLicenseNo> mList;

    public AuthorisedLicenseNoListAdapter(ArrayList<AuthorisedLicenseNo> list) {
        mList = list;
    }
    @NonNull
    @Override
    public AuthorisedLicenseNoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.authorised_license_no_item, parent, false);
        return new AuthorisedLicenseNoItemViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorisedLicenseNoItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(AuthorisedLicenseNo AuthorisedLicenseNo) {
        mList.add(AuthorisedLicenseNo);
        notifyDataSetChanged();
    }


    class AuthorisedLicenseNoItemViewHolder extends RecyclerView.ViewHolder {


        private final Context mContext;
        TextView mOwner, mLicense;
        public AuthorisedLicenseNoItemViewHolder(View itemView, Context context) {
            super(itemView);
            mOwner = itemView.findViewById(R.id.owner);
            mLicense = itemView.findViewById(R.id.unauth_license_no);
            mContext = context;
        }

        public void bind(int pos) {
            AuthorisedLicenseNo AuthorisedLicenseNo = mList.get(pos);
            mOwner.setText(AuthorisedLicenseNo.getOwnerName());
            mLicense.setText(AuthorisedLicenseNo.getLicenseNo());
        }
    }
}
