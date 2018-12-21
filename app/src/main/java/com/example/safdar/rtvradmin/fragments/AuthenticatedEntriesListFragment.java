package com.example.safdar.rtvradmin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safdar.rtvradmin.R;
import com.example.safdar.rtvradmin.adapters.AuthorisedEntriesListAdapter;
import com.example.safdar.rtvradmin.data.AuthorisedEntry;
import com.example.safdar.rtvradmin.data.AuthorisedLicenseNo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;


public class AuthenticatedEntriesListFragment extends Fragment {

    AuthorisedEntriesListAdapter mAdapter;
    ChildEventListener mChildEventListener;
    DatabaseReference mAuthorisedEntriesReference;

    public AuthenticatedEntriesListFragment() {
        // Required empty public constructor
    }

    public void setAuthorisedEntriesReference(DatabaseReference ref) {
        mAuthorisedEntriesReference = ref;
    }

    RecyclerView mAuthenticatedEntriesListRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticated_entries_list, container, false);
        mAuthenticatedEntriesListRv = view.findViewById(R.id.authenticated_entries_list_rv);
        mAuthenticatedEntriesListRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mAuthenticatedEntriesListRv.setHasFixedSize(true);
        mAdapter = new AuthorisedEntriesListAdapter(new ArrayList<AuthorisedEntry>());
        mAuthenticatedEntriesListRv.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachDatabaseReadListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    HashMap o  = (HashMap) dataSnapshot.getValue();
                    mAdapter.add(new AuthorisedEntry(o.get("owner").toString()
                            , o.get("license_no").toString(), (long)o.get("time")));
                    //Log.v("Saf", o.toString());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mAuthorisedEntriesReference.addChildEventListener(mChildEventListener);
        }
    }

    public void showEntryList() {
        if (mAuthenticatedEntriesListRv != null)
            mAuthenticatedEntriesListRv.setVisibility(View.VISIBLE);
    }

    public void hideEntryList() {
        if (mAuthenticatedEntriesListRv != null)
            mAuthenticatedEntriesListRv.setVisibility(View.INVISIBLE);
    }
}
