package com.example.jobportalcorporate;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    DatabaseReference myRef;
    RecyclerView rcv;
    MyAdapter adapter=new MyAdapter();
    ArrayList<com.example.jobportalcorporate.CandidateList> candilist=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final EditText searchquery = (EditText) findViewById(R.id.searchquery);
        ImageView search = (ImageView) findViewById(R.id.search);



        myRef= FirebaseDatabase.getInstance().getReference("Users");

        myRef.keepSynced(true);

        rcv=(RecyclerView)findViewById(R.id.rcv);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(com.example.jobportalcorporate.ProfileActivity.this);
        rcv.setLayoutManager(manager);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {

                    String achievements = dataSnapshot.child("achievements").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String certifications = dataSnapshot.child("certifications").getValue(String.class);
                    String city = dataSnapshot.child("city").getValue(String.class);
                    String dob = dataSnapshot.child("dob").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String pgcourse = dataSnapshot.child("pgcourse").getValue(String.class);
                    String pgmarks = dataSnapshot.child("pgmarks").getValue(String.class);
                    String pgyear = dataSnapshot.child("pgyear").getValue(String.class);
                    String ugcourse = dataSnapshot.child("ugcourse").getValue(String.class);
                    String ugmarks = dataSnapshot.child("ugmarks").getValue(String.class);
                    String ugyear = dataSnapshot.child("ugyear").getValue(String.class);
                    String pincode = dataSnapshot.child("pincode").getValue(String.class);
                    String sex = dataSnapshot.child("sex").getValue(String.class);
                    String skills = dataSnapshot.child("skills").getValue(String.class);
                    String workexp = dataSnapshot.child("workexp").getValue(String.class);
                    String xiimarks = dataSnapshot.child("xiimarks").getValue(String.class);
                    String xiiyear = dataSnapshot.child("xiiyear").getValue(String.class);
                    String xmarks = dataSnapshot.child("xmarks").getValue(String.class);
                    String xyear = dataSnapshot.child("xyear").getValue(String.class);
                    String cv = dataSnapshot.child("cv").child("url").getValue(String.class);

                    com.example.jobportalcorporate.CandidateList list = new com.example.jobportalcorporate.CandidateList();
                    list.setAchievements(achievements);
                    list.setAddress(address);
                    list.setCertifications(certifications);
                    list.setCity(city);
                    list.setDob(dob);
                    list.setEmail(email);
                    list.setName(name);
                    list.setPgcourse(pgcourse);
                    list.setPgmarks(pgmarks);
                    list.setPgyear(pgyear);
                    list.setPincode(pincode);
                    list.setSex(sex);
                    list.setSkills(skills);
                    list.setUgcourse(ugcourse);
                    list.setUgmarks(ugmarks);
                    list.setUgyear(ugyear);
                    list.setWorkexp(workexp);
                    list.setXiimarks(xiimarks);
                    list.setXiiyear(xiiyear);
                    list.setXmarks(xmarks);
                    list.setXyear(xyear);
                    list.setCv(cv);

                    candilist.add(list);
                    if (candilist.size() > 0) {
                        rcv.setAdapter(adapter);
                    }
                }catch (Exception e){}

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(com.example.jobportalcorporate.ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = searchquery.getText().toString();
                if (s.isEmpty()) {
                    Toast.makeText(com.example.jobportalcorporate.ProfileActivity.this, "No Result", Toast.LENGTH_SHORT).show();

                } else {
                    filter(s);
                }
            }
        });


    }


    private void filter(String text){

        ArrayList<com.example.jobportalcorporate.CandidateList> filteredlist=new ArrayList<>();

        for(com.example.jobportalcorporate.CandidateList item :candilist){

            if (item.getSkills().toLowerCase().contains(text.toLowerCase()) ){
                filteredlist.add(item);
            }
            else if (item.getCertifications().toLowerCase().contains(text.toLowerCase()) ){

                filteredlist.add(item);
            }

        }

        adapter.filterlist(filteredlist);

    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,skills,certifications,workexp;
        public MyViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tv_name);
          
            skills=itemView.findViewById(R.id.tv_skills);
            certifications=itemView.findViewById(R.id.tv_certifications);
          
            workexp=itemView.findViewById(R.id.tv_workexp);

        }
    }


    public  class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.candidatelist, parent, false);
            MyViewHolder holder = new MyViewHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final com.example.jobportalcorporate.CandidateList list = candilist.get(position);

            holder.name.setText(list.getName());
          
            holder.skills.setText(list.getSkills());
            holder.certifications.setText(list.getCertifications());
            holder.workexp.setText(list.getWorkexp());
            
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String achievements=list.getAchievements();
                    String address=list.getAddress();
                    String certifications=list.getCertifications();
                    String city=list.getCity();
                    String dob=list.getDob();
                    String email=list.getEmail();
                    String name=list.getName();
                    String pgcourse=list.getPgcourse();
                    String pgmarks=list.getPgmarks();
                    String pgyear=list.getPgyear();
                    String ugcourse=list.getUgcourse();
                    String ugmarks=list.getUgmarks();
                    String ugyear=list.getUgyear();
                    String pincode=list.getPincode();
                    String sex=list.getSex();
                    String skills=list.getSkills();
                    String workexp=list.getWorkexp();
                    String xiimarks=list.getXiimarks();
                    String xiiyear=list.getXiiyear();
                    String xmarks=list.getXmarks();
                    String xyear=list.getXyear();
                    String cv=list.getCv();

                    Intent intent = new Intent(com.example.jobportalcorporate.ProfileActivity.this, com.example.jobportalcorporate.CandidateProfile.class);


                    intent.putExtra("name", name);
                    intent.putExtra("achievements", achievements);
                    intent.putExtra("email", email);
                    intent.putExtra("workexp", workexp);
                    intent.putExtra("address", address);
                    intent.putExtra("certifications", certifications);
                    intent.putExtra("city", city);
                    intent.putExtra("dob", dob);
                    intent.putExtra("skills", skills);
                    intent.putExtra("pgcourse", pgcourse);
                    intent.putExtra("pgmarks", pgmarks);
                    intent.putExtra("pgyear", pgyear);
                    intent.putExtra("ugcourse", ugcourse);
                    intent.putExtra("ugmarks", ugmarks);
                    intent.putExtra("ugyear", ugyear);
                    intent.putExtra("pincode", pincode);
                    intent.putExtra("sex", sex);
                    intent.putExtra("xiimarks", xiimarks);
                    intent.putExtra("xiiyear", xiiyear);
                    intent.putExtra("xmarks", xmarks);
                    intent.putExtra("xyear", xyear);
                    intent.putExtra("cv", cv);


                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return candilist.size();
        }
        public void filterlist(ArrayList<com.example.jobportalcorporate.CandidateList> filteredlist){
            candilist =filteredlist;
            adapter.notifyDataSetChanged();
        }
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
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, com.example.jobportalcorporate.MainActivity.class));
                break;


        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

           if (id == R.id.nav_upload_job) {
            startActivity(new Intent(com.example.jobportalcorporate.ProfileActivity.this, com.example.jobportalcorporate.UploadJob.class));

            }
            if (id == R.id.nav_posted_job){
               startActivity(new Intent(com.example.jobportalcorporate.ProfileActivity.this,PostedJob.class));

            }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
