package com.example.kidzeee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kidzeee.fragments.ChatFragments;
import com.example.kidzeee.fragments.NotificatonFragments;
import com.example.kidzeee.fragments.StatustFragments;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageButton Search, More;
    private TabLayout tabs;
    private ViewPager pager;
    private fragmetAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Search = findViewById(R.id.mainsearch);
        More = findViewById(R.id.mainsetting);
        tabs = findViewById(R.id.maintab);
        pager = findViewById(R.id.mainview);
        adapter=new fragmetAdapter(getSupportFragmentManager());
        tabs.setupWithViewPager(pager);
        pager.setAdapter(adapter);

        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivity(new Intent(MainActivity.this,loginactivity.class));
            finish();
        }
        else
        {
            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
    }

    public class fragmetAdapter extends FragmentPagerAdapter {

        public fragmetAdapter(FragmentManager fm) {
            super(fm);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0: return "Chats";
                case 1: return "Status";
                case 2: return "Notification";
                default : return "Chats";
            }
        }


        @Override
        public Fragment getItem(int i) {
            switch (i) {

                case 0:
                    return new ChatFragments();
                case 1:
                    return new StatustFragments();
                case 2:
                    return new NotificatonFragments();
                default:
                    return new ChatFragments();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
