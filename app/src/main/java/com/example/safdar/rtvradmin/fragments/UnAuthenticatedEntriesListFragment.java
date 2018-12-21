package com.example.safdar.rtvradmin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safdar.rtvradmin.R;
import com.example.safdar.rtvradmin.adapters.AuthorisedEntriesListAdapter;
import com.example.safdar.rtvradmin.adapters.UnauthorisedEntriesListAdapter;
import com.example.safdar.rtvradmin.data.AuthorisedEntry;
import com.example.safdar.rtvradmin.data.UnauthorisedEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;


public class UnAuthenticatedEntriesListFragment extends Fragment {

    UnauthorisedEntriesListAdapter mAdapter;
    ChildEventListener mChildEventListener;
    DatabaseReference mUnauthorisedEntriesReference;

    public UnAuthenticatedEntriesListFragment() {
        // Required empty public constructor
    }

    public void setUnauthorisedEntriesReference(DatabaseReference ref) {
        mUnauthorisedEntriesReference = ref;
    }

    RecyclerView mUnauthenticatedEntriesListRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticated_entries_list, container, false);
        mUnauthenticatedEntriesListRv = view.findViewById(R.id.authenticated_entries_list_rv);
        mUnauthenticatedEntriesListRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mUnauthenticatedEntriesListRv.setHasFixedSize(true);
        mAdapter = new UnauthorisedEntriesListAdapter(new ArrayList<UnauthorisedEntry>());
        mUnauthenticatedEntriesListRv.setAdapter(mAdapter);
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
                    mAdapter.add(new UnauthorisedEntry(o.get("license_no").toString(), (long)o.get("time")));
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
            mUnauthorisedEntriesReference.addChildEventListener(mChildEventListener);
        }
    }
}
