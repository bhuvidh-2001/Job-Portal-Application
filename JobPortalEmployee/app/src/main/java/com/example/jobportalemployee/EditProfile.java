package com.example.jobportalemployee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;


public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    final static int PICK_PDF_CODE=2342;
    final static int PICK_VIDEO_CODE=2340;

    EditText u_city,u_name,u_address,u_pincode
            ,u_xmarks,u_xiimarks
            ,u_ugmarks,u_pgmarks
            ,u_skills,u_achievements,u_certifications,u_workexp
            ,xspinner,xiispinner,ug_yearspinner,pg_yearspinner;

    ImageView date;
    TextView textViewStatus,u_dob;
   ProgressBar progressBar;

    RadioGroup u_radioGroup;
    RadioButton male,female;

    Spinner ugspinner,pgspinner,workexp_spinner;

    String[] uglist={"None","BTech","BCA","BCom","BBA"};
    String[] exp={"0-2 Years","2-4 Years","4-6 Years","More than 6 Years"};
    String[] pglist={"None","MTech","MCA","MCom","MBA"};



    FirebaseAuth mAuth;

    StorageReference mStorageReference;
    DatabaseReference databaseReference,mDatabasereference;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mStorageReference= FirebaseStorage.getInstance().getReference();

        mDatabasereference=FirebaseDatabase.getInstance().getReference((Constants.DATABASE_PATH_UPLOADS+"/"+user.getUid()+"/"));
        databaseReference=FirebaseDatabase.getInstance().getReference();

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        u_name=(EditText)findViewById(R.id.et_name);
        u_radioGroup=(RadioGroup)findViewById(R.id.rg_sex);
        u_city=(EditText)findViewById(R.id.et_city);
        u_address=(EditText)findViewById(R.id.et_address);
        u_pincode=(EditText)findViewById(R.id.et_pincode);
        u_xmarks=(EditText)findViewById(R.id.et_10marks);

        u_xiimarks=(EditText)findViewById(R.id.et_12marks);
        
        u_ugmarks=(EditText)findViewById(R.id.et_ugmarks);
        
        u_pgmarks=(EditText)findViewById(R.id.et_pgmarks);
        
        u_skills=(EditText)findViewById(R.id.et_skills);
        u_achievements=(EditText)findViewById(R.id.et_achievement);
        u_certifications=(EditText)findViewById(R.id.et_certification);
        xspinner=(EditText)findViewById(R.id.sp_xsp);
        xiispinner=(EditText)findViewById(R.id.sp_xiisp);
        ug_yearspinner=(EditText)findViewById(R.id.sp_ugsp);
        pg_yearspinner=(EditText)findViewById(R.id.sp_pgsp);
        
        male=(RadioButton)findViewById(R.id.male);
        male.setChecked(true);
        u_dob=(TextView)findViewById(R.id.et_dob);
        date=(ImageView)findViewById(R.id.date);


        ugspinner=(Spinner) findViewById(R.id.sp_ug);
        pgspinner=(Spinner) findViewById(R.id.sp_pg);
        workexp_spinner=(Spinner)findViewById(R.id.workexp);



        ArrayAdapter<String> ug=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,uglist);
        ugspinner.setAdapter(ug);

        ArrayAdapter<String> pg=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pglist);
        pgspinner.setAdapter(pg);


        ArrayAdapter<String> workexp=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,exp);
        workexp_spinner.setAdapter(workexp);

        findViewById(R.id.btn_saveprofile).setOnClickListener(this);
        findViewById(R.id.cv).setOnClickListener(this);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(com.example.jobportalemployee.EditProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth+"/"+(month+1)+"/"+year;
                        u_dob.setText(date);

                    }
                },year,month,day);
                dialog.show();
            }
        });


    }

    //////to upload user data

    private void SaveProfile() {

        FirebaseUser user=mAuth.getCurrentUser();


        String name = u_name.getText().toString().trim();
        RadioGroup rg = (RadioGroup)findViewById(R.id.rg_sex);
        String sex = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        String city = u_city.getText().toString().trim();
        String address = u_address.getText().toString().trim();
        String pincode = u_pincode.getText().toString().trim();
        String xmarks = u_xmarks.getText().toString().trim();
        String xyear = xspinner.getText().toString().trim();
        String xiimarks = u_xiimarks.getText().toString().trim();
        String xiiyear = xiispinner.getText().toString().trim();
        String ugcourse = ugspinner.getSelectedItem().toString().trim();
        String ugmarks = u_ugmarks.getText().toString().trim();
        String ugyear = ug_yearspinner.getText().toString().trim();
        String pgcourse = pgspinner.getSelectedItem().toString().trim();
        String pgmarks = u_pgmarks.getText().toString().trim();
        String pgyear = pg_yearspinner.getText().toString().trim();
        String skills = u_skills.getText().toString().trim();
        String achievements = u_achievements.getText().toString().trim();
        String certifications = u_certifications.getText().toString().trim();
        String workexp =workexp_spinner.getSelectedItem().toString();
        String email = user.getEmail();
        String dob=u_dob.getText().toString();

        if (name.isEmpty()){
            u_name.setError("Name Required");
            u_name.requestFocus();
            return;
        }
        if (city.isEmpty()){
            u_city.setError("City Required");
            u_city.requestFocus();
            return;
        }
        if (address.isEmpty()){
            u_address.setError("Address Required");
            u_address.requestFocus();
            return;
        }
        if (pincode.isEmpty()){
            u_pincode.setError("Pincode Required");
            u_pincode.requestFocus();
            return;
        }
        if (xmarks.isEmpty()){
            u_xmarks.setError("Marks Needed");
            u_xmarks.requestFocus();
            return;
        }

        if (xiimarks.isEmpty()){
            u_xiimarks.setError("Marks Needed");
            u_xiimarks.requestFocus();
            return;
        }

        if (ugmarks.isEmpty()){
            u_ugmarks.setError("Marks Needed");
            u_ugmarks.requestFocus();
            return;
        }



        if (pgmarks.isEmpty()){
            u_pgmarks.setError("Marks Needed");
            u_pgmarks.requestFocus();
            return;
        }


        if (skills.isEmpty()){
            u_skills.setError("Skills Needed");
            u_skills.requestFocus();
            return;
        }

        if (workexp.isEmpty()){
            u_workexp.setError("Work Experience Needed");
            u_workexp.requestFocus();
            return;
        }
        if (dob.isEmpty()){
            u_dob.setError("DOB Needed");
            u_dob.requestFocus();
            return;
        }

        SaveUserProfile sp=new SaveUserProfile(name,city,address,pincode
                ,xmarks,xyear,xiimarks,xiiyear
                ,ugmarks,ugyear,pgmarks,pgyear
                ,skills,achievements,certifications,workexp,sex,ugcourse,pgcourse,email,dob);


        databaseReference.child("Users").child(user.getUid()).setValue(sp);
        Toast.makeText(com.example.jobportalemployee.EditProfile.this, "success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(com.example.jobportalemployee.EditProfile.this, com.example.jobportalemployee.ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



    ////TO GET PDF FROM STORAGE

    private void getPdf(){

        ////intent for file chooser
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        //when user choose file
        if (requestCode==PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            //if a file is selected
            if (data.getData()!= null){
                uploadPdfFile(data.getData());
            }else {
                Toast.makeText(this, "No File Choosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPdfFile(Uri data) {

        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(com.example.jobportalemployee.EditProfile.this, "CV Uploaded", Toast.LENGTH_SHORT).show();

                        UploadCv upload = new UploadCv( taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        mDatabasereference.child("cv").setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "Exceeds Upload Size Limit", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_saveprofile:
                SaveProfile();
                break;

            case  R.id.cv:
                getPdf();
                break;

        }

    }
}
