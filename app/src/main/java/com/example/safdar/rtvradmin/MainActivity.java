package com.example.safdar.rtvradmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.safdar.rtvradmin.adapters.AuthorisedEntriesListAdapter;
import com.example.safdar.rtvradmin.adapters.AuthorisedLicenseNoListAdapter;
import com.example.safdar.rtvradmin.data.AuthorisedLicenseNo;
import com.example.safdar.rtvradmin.fragments.AuthenticatedEntriesListFragment;
import com.example.safdar.rtvradmin.fragments.AuthenticatedUserListFragment;
import com.example.safdar.rtvradmin.fragments.UnAuthenticatedEntriesListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirebaseAuth.AuthStateListener{


    static final int RC_SIGN_INT = 123;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    AuthorisedLicenseNoListAdapter authorisedLicenseNoListAdapter;
    DatabaseReference mAuthorisedLicensesReference;
    FirebaseDatabase mFirebaseDatabase;
    AuthenticatedUserListFragment mAuthUsersFragment;
    AuthenticatedEntriesListFragment mAuthEntriesFragment;
    UnAuthenticatedEntriesListFragment mUnauthEntriesFragment;
    Fragment mCurrentFragment;
    DatabaseReference mAuthorisedEntriesReference;
    DatabaseReference mUnauthorisedEntriesReference;
    View entryContainer, userContainer, unauthEntryContainer;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mActionBar = getSupportActionBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        authorisedLicenseNoListAdapter = new AuthorisedLicenseNoListAdapter(new ArrayList<AuthorisedLicenseNo>());
        mAuthorisedLicensesReference = mFirebaseDatabase.getReference().child("Authenticated License Plates");
        mAuthorisedEntriesReference = mFirebaseDatabase.getReference().child("Authorized Entries");
        mUnauthorisedEntriesReference = mFirebaseDatabase.getReference().child("Unauthorized Entries");

        mAuthUsersFragment = new AuthenticatedUserListFragment();
        mAuthUsersFragment.setAuthorisedLicensesReference(mAuthorisedLicensesReference);
        mAuthEntriesFragment = new AuthenticatedEntriesListFragment();
        mAuthEntriesFragment.setAuthorisedEntriesReference(mAuthorisedEntriesReference);
        mUnauthEntriesFragment = new UnAuthenticatedEntriesListFragment();
        mUnauthEntriesFragment.setUnauthorisedEntriesReference(mUnauthorisedEntriesReference);

        entryContainer = findViewById(R.id.main_container);
        userContainer = findViewById(R.id.main_container2);
        unauthEntryContainer = findViewById(R.id.main_container3);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mAuthEntriesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container2, mAuthUsersFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container3, mUnauthEntriesFragment).commit();
        mCurrentFragment = mAuthEntriesFragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            entryContainer.setVisibility(View.VISIBLE);
            userContainer.setVisibility(View.GONE);
            unauthEntryContainer.setVisibility(View.GONE);
            mActionBar.setTitle("Authenticated Entries");
        } else if (id == R.id.nav_gallery) {
            entryContainer.setVisibility(View.GONE);
            userContainer.setVisibility(View.VISIBLE);
            unauthEntryContainer.setVisibility(View.GONE);
            mActionBar.setTitle("Authenticated Residents");
        } else if (id == R.id.nav_slideshow) {
            entryContainer.setVisibility(View.GONE);
            userContainer.setVisibility(View.GONE);
            unauthEntryContainer.setVisibility((View.VISIBLE));
            mActionBar.setTitle("Unauthenticated Entries");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onPause() {
        mAuth.removeAuthStateListener(this);
        super.onPause();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        mUser = firebaseAuth.getCurrentUser();
        if (mUser == null) {
            startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(
                        Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                .build(), RC_SIGN_INT);
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_INT) {
            if (resultCode == RESULT_OK) {
                mUser = mAuth.getCurrentUser();
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sign in not successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
