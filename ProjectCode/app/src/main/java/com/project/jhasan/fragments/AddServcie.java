package com.project.jhasan.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.project.jhasan.servicemodel.serviceInfo;
import com.project.jhasan.soudagor.ActivityMaps;
import com.project.jhasan.soudagor.R;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jhasan on 27/6/19.
 */


public class AddServcie extends Fragment implements AdapterView.OnItemSelectedListener {


    public final static String MESSAGE_KEY = "com.project.jhasan.soudagor.test";
    private static final String TAG = AddServcie.class.getName();
    private static final int GET_MAP_DATA = 1001;

    private ImageView btnChoose, btnDone, imageView;
    private EditText serviceName, serviceFee, serviceAddress, serviceDescription, servicePhone;


    private Uri filepath;

    private final int PICK_IMAGE_REQUEST = 71;


    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firedatabase;



    private FirebaseAuth mAuth;


    public AddServcie() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String[] servicesSpinner={"Automotive","Beauty","Computer","creative", "Event", "Farm+garden","Household","labor/move","travel/vac",
                                    "other" };

       // List<serviceInfo>serviceInfos=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firedatabase = FirebaseDatabase.getInstance();
        // Inflate the layout for this fragment
        View fragView= inflater.inflate(R.layout.fragment_add_servcie, container, false);


        serviceName=fragView.findViewById(R.id.editServiceName);
        serviceFee=fragView.findViewById(R.id.editFee);
        serviceAddress=fragView.findViewById(R.id.editLocation);
        serviceDescription=fragView.findViewById(R.id.editDescription);
        servicePhone=fragView.findViewById(R.id.editPhone);


        Spinner spin=fragView.findViewById(R.id.spinner);
        btnChoose=fragView.findViewById(R.id.btn_addImage);
        imageView = fragView.findViewById(R.id.ImageView);
        btnDone=fragView.findViewById(R.id.btn_done);

        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the servicce list

        ArrayAdapter adapterSpin = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,servicesSpinner);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapterSpin);

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMaps.class);
                startActivityForResult(intent, GET_MAP_DATA);


            }
        });


        // Choose image

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        //Upload Image


        /// Complete adding info
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        return fragView;

    }

    //set service info data

    private void allDone() {

        String userId = mAuth.getUid();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        serviceInfo info = new serviceInfo();

        info.setserviceCategory(serviceCategory);
        info.setServiceName(serviceName.getText().toString());
        info.setServiceFees(serviceFee.getText().toString());
        info.setAddress(serviceAddress.getText().toString());
        info.setServiceDescription(serviceDescription.getText().toString());
        info.setImage(downloadImageUrl);
        info.setServiceProviderContact(servicePhone.getText().toString());
        info.setUID(userId);


        DatabaseReference reference = firedatabase.getReference().child("services").push();
        reference.setValue(info);

    }

    private String downloadImageUrl;
    private String serviceCategory;

    private void uploadImage() {

        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddServcie.this.getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadImageUrl = uri.toString();
                                    allDone();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddServcie.this.getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();

        Log.d(TAG, "onActivityResult called with " + data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parentview, View selectedItemview, int position, long id) {

        String item = parentview.getItemAtPosition(position).toString();
        CharSequence text = item;

        if (item != null) {
            Toast.makeText(getContext(), item,
                    Toast.LENGTH_SHORT).show();
            serviceCategory = item;

        }
        Toast.makeText(getContext(), "Selected",
                Toast.LENGTH_SHORT).show();

//        Toast.makeText(parentview.getContext(),item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}


/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

