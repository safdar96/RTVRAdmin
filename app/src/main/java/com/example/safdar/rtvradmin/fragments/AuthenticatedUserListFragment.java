package com.example.safdar.rtvradmin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.safdar.rtvradmin.R;
import com.example.safdar.rtvradmin.adapters.AuthorisedLicenseNoListAdapter;
import com.example.safdar.rtvradmin.data.AuthorisedLicenseNo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;


public class AuthenticatedUserListFragment extends Fragment {

    AuthorisedLicenseNoListAdapter mAdapter;
    ChildEventListener mChildEventListener;
    DatabaseReference mAuthorisedLicensesReference;
    ImageButton mSendBtn;
    EditText mOwnerName;
    EditText mOwnerLicenseNo;

    public AuthenticatedUserListFragment() {
        // Required empty public constructor
    }

    public void setAuthorisedLicensesReference(DatabaseReference ref) {
        mAuthorisedLicensesReference = ref;
    }

    RecyclerView mAuthenticatedUsersListRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticated_users_list, container, false);
        mAuthenticatedUsersListRv = view.findViewById(R.id.authenticated_users_list_rv);
        mOwnerName = view.findViewById(R.id.owner_name);
        mOwnerLicenseNo = view.findViewById(R.id.owner_license_no);
        mSendBtn = view.findViewById(R.id.send_btn);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mOwnerName.getText().toString();
                String license_no = mOwnerLicenseNo.getText().toString();
                if (!name.isEmpty() && !license_no.isEmpty()) {
                    license_no = license_no.toUpperCase();
                    mAuthorisedLicensesReference.push().setValue(new AuthorisedLicenseNo(name, license_no)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), name + " is now authenticated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mOwnerName.setText("");
                    mOwnerLicenseNo.setText("");
                } else {
                    Toast.makeText(getContext(), "Please enter valid entries", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAuthenticatedUsersListRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mAuthenticatedUsersListRv.setHasFixedSize(true);
        mAdapter = new AuthorisedLicenseNoListAdapter(new ArrayList<AuthorisedLicenseNo>());
        mAuthenticatedUsersListRv.setAdapter(mAdapter);
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
                    mAdapter.add(new AuthorisedLicenseNo(o.get("ownerName").toString()
                            , o.get("licenseNo").toString()));
                    Log.v("Saf", o.get("owner") + o.get("licenseNo").toString());
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
            mAuthorisedLicensesReference.addChildEventListener(mChildEventListener);
        }
    }
}
