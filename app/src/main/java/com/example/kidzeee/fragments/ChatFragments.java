package com.example.kidzeee.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kidzeee.Model.UserModel;
import com.example.kidzeee.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragments extends Fragment {

    private RecyclerView ChatsRecyclerview;
    private ChatsAdapter adapter;
    List<UserModel> UserDetails;

    public ChatFragments() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chat_fragments, container, false);

        UserDetails = new ArrayList<>();
        UserDetails = getAllUsers();
        ChatsRecyclerview = v.findViewById(R.id.chats_recyclerview);
        ChatsRecyclerview.setHasFixedSize(true);
        ChatsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        v.findViewById(R.id.chats_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Fab clicked", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ChatsAdapter();
        ChatsRecyclerview.setAdapter(adapter);

        return v;
    }

    private List<UserModel> getAllUsers() {

        final List<UserModel> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                ChatsRecyclerview.removeAllViews();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String key = snapshot.getKey();
                    String email = snapshot.child("email").getValue().toString();
                    list.add(new UserModel(key,email));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return list;

    }


    private class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>{
        @NonNull
        @Override
        public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.single_user_layout,viewGroup,false);
            return new ChatsAdapter.ChatsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatsViewHolder chatsViewHolder, int i) {

            chatsViewHolder.UserEmail.setText(UserDetails.get(i).getEmail());
        }

        @Override
        public int getItemCount() {
            return UserDetails.size();
        }

        public class ChatsViewHolder extends RecyclerView.ViewHolder {

            TextView UserEmail;

            public ChatsViewHolder(@NonNull View itemView) {
                super(itemView);

                UserEmail = itemView.findViewById(R.id.single_user_layout_name);
            }
        }
    }
}
