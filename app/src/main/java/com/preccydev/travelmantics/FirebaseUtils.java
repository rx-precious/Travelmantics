package com.preccydev.travelmantics;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtils {
    public static FirebaseDatabase sFirebaseDatabase;
    public static DatabaseReference sDatabaseReference;
    private static FirebaseUtils sFirebaseUtils;
    public static FirebaseAuth sFirebaseAuth;
    public static FirebaseAuth.AuthStateListener sAuthStateListener;
    public static ArrayList<TravelDeal> sDeals;
    private static final int RC_SIGN_IN = 123;
    private static Activity caller;

    private FirebaseUtils() {

    }

    public static void openFbReference(String ref, final Activity callerActivity) {
        if (sFirebaseUtils == null) {
            sFirebaseUtils = new FirebaseUtils();
            sFirebaseDatabase = FirebaseDatabase.getInstance();
            caller = callerActivity;
            sFirebaseAuth = FirebaseAuth.getInstance();

            sAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                   FirebaseUtils.sinin();
                    Toast.makeText(callerActivity.getBaseContext(), "Welcome back", Toast.LENGTH_LONG).show();
                }


            };

        }

        sDeals = new ArrayList<TravelDeal>();
        sDatabaseReference = sFirebaseDatabase.getReference().child(ref);
    }

    private static void sinin (){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }


        public static void attachListener () {
            sFirebaseAuth.addAuthStateListener(sAuthStateListener);
        }

        public static void detachListener () {
            sFirebaseAuth.removeAuthStateListener(sAuthStateListener);
        }
    }
