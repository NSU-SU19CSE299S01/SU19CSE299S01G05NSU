package com.project.jhasan.soudagor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.jhasan.fragments.AddServcie;
import com.project.jhasan.fragments.serviceFeed;

import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filepath;
    private NavigationView navigationView;
    private View navHeader;
    private ImageView  imgProfile;
    private TextView txtName, txtEmail;

    private FirebaseUser user;
    private Button addService,button,button2;

    private String NavName,NavEmail;
    private Uri NavPhotoUrl;
    private FirebaseAuth mAuth;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firedatabase;
    GridLayout mainGrid;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
//        button=findViewById(R.id.button);
//        button2=findViewById(R.id.button2);
        setSupportActionBar(toolbar);


        mainGrid=findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtEmail = (TextView) navHeader.findViewById(R.id.email);
        imgProfile = (ImageView) navHeader.findViewById(R.id.imageViewPro);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firedatabase = FirebaseDatabase.getInstance();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                uploadImage();

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddServcie fragment = new AddServcie();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
                fab.setVisibility(View.GONE);



            }
        });

        loadNavHeader();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // ImageView proImage= navigationView.getHeaderView(0).findViewById(R.id.imageViewPro);

        if (downloadImageUrl==null) {


            }else {
            Glide.with(getApplicationContext())
                    .load(downloadImageUrl)
                    .into(imgProfile);
        }


    }


    private String downloadImageUrl;

    private void uploadImage() {

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("ProfileImages/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadImageUrl = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        }

    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        for (int i=0;i<mainGrid.getChildCount();i++)
        {
            CardView cardView= (CardView) mainGrid.getChildAt(i);
            final int finalI=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(MainActivity.this,"Clicked at index"+finalI,Toast.LENGTH_SHORT).show();
                    if (finalI == 0){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName = "ALL";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==1){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="Beauty";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==2){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="creative";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==3){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="computer";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==4){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="Farm+garden";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==5){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="Automotive";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==6){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="Household";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    else if (finalI==7){
                        serviceFeed fragment = new serviceFeed();
                        fragment.categoryName="labor";
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }


                }
            });
        }
    }

    private void loadNavHeader() {

        if (user != null) {
// Name, email address, and profile photo Url
             NavName = user.getDisplayName();
             NavEmail = user.getEmail();
            NavPhotoUrl = user.getPhotoUrl();


            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }

        txtName.setText(NavName);
        txtEmail.setText(NavEmail);
        if (downloadImageUrl!=null) {
            imgProfile.setImageURI(Uri.parse(downloadImageUrl));
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {


            logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {

    }





}
