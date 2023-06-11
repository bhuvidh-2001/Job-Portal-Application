package com.example.jobportalemployee;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AppliedJob extends AppCompatActivity {

    DatabaseReference myRef;
    RecyclerView appliedjob_rcv;

    FirebaseAuth mAuth;
    AppliedjobAdapter appliedjobAdapter=new AppliedjobAdapter();


    ArrayList<JobList> applied_joblist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_job);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        myRef = FirebaseDatabase.getInstance().getReference("Users"+"/"+user.getUid()+"/"+"Appliedjob");
        myRef.keepSynced(true);

        appliedjob_rcv = (RecyclerView) findViewById(R.id.appliedjob_rcv);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(com.example.jobportalemployee.AppliedJob.this);
        appliedjob_rcv.setLayoutManager(manager);



        

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String company_name = dataSnapshot.child("company_name").getValue(String.class);
                    String contact = dataSnapshot.child("contact").getValue(String.class);
                    String emailid = dataSnapshot.child("emailid").getValue(String.class);
                    String experience = dataSnapshot.child("experience").getValue(String.class);
                    String jobid = dataSnapshot.child("jobid").getValue(String.class);
                    String jobdescription = dataSnapshot.child("jobdescription").getValue(String.class);
                    String jobqualification = dataSnapshot.child("jobqualification").getValue(String.class);
                    String salary = dataSnapshot.child("salary").getValue(String.class);
                    String skills = dataSnapshot.child("skills").getValue(String.class);
                    String jobtype = dataSnapshot.child("jobtype").getValue(String.class);
                    String joblocation = dataSnapshot.child("location").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);


                JobList data = new JobList();

                data.setCompany_name(company_name);
                data.setContact(contact);
                data.setEmailid(emailid);
                data.setExperience(experience);
                data.setJobdescription(jobdescription);
                data.setJobid(jobid);
                data.setJobqualification(jobqualification);
                data.setSalary(salary);
                data.setSkills(skills);
                data.setJobtype(jobtype);
                data.setLocation(joblocation);
                data.setDate(date);
                applied_joblist.add(data);

                
                if (applied_joblist.size() > 0) {
                    appliedjob_rcv.setAdapter(appliedjobAdapter);
                }

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
                Toast.makeText(com.example.jobportalemployee.AppliedJob.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }









    public  class AppliedjobViewHolder extends RecyclerView.ViewHolder{

        TextView company_name
                ,jobdescription,jobid,contact,emailid,experience,jobqualification,salary,skills,jobtype,location,date;
        public AppliedjobViewHolder(View itemView) {
            super(itemView);

            company_name=itemView.findViewById(R.id.tv_name);
            contact=itemView.findViewById(R.id.tv_contact);
            emailid=itemView.findViewById(R.id.tv_emailid);
            experience=itemView.findViewById(R.id.tv_experience);
            jobdescription=itemView.findViewById(R.id.tv_jobdescription);
            jobid=itemView.findViewById(R.id.tv_jobid);
            jobqualification=itemView.findViewById(R.id.tv_jobqualification);
            salary=itemView.findViewById(R.id.tv_salary);
            skills=itemView.findViewById(R.id.tv_skills);
            jobtype=itemView.findViewById(R.id.tv_jobtype);
            location=itemView.findViewById(R.id.tv_joblocation);
            date=itemView.findViewById(R.id.tv_date);

        }
    }

    public class AppliedjobAdapter extends RecyclerView.Adapter<AppliedjobViewHolder>{

        @Override
        public AppliedjobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=getLayoutInflater();
            View v=inflater.inflate(R.layout.applied_job_list,parent,false);
            AppliedjobViewHolder appliedjobViewHolder=new AppliedjobViewHolder(v);
            return appliedjobViewHolder;
        }

        @Override
        public void onBindViewHolder(AppliedjobViewHolder holder, int position) {
            final JobList list=applied_joblist.get(position);

            holder.company_name.setText(list.getCompany_name());
            holder.contact.setText(list.getContact());
            holder.emailid.setText(list.getEmailid());
            holder.experience.setText(list.getExperience());
            holder.jobdescription.setText(list.getJobdescription());
            holder.jobid.setText(list.getJobid());
            holder.jobqualification.setText(list.getJobqualification());
            holder.salary.setText(list.getSalary());
            holder.skills.setText(list.getSkills());
            holder.jobtype.setText(list.getJobtype());
            holder.location.setText(list.getLocation());
            holder.date.setText(list.getDate());


        }

        @Override
        public int getItemCount() {

            return applied_joblist.size();
        }
    }

}
