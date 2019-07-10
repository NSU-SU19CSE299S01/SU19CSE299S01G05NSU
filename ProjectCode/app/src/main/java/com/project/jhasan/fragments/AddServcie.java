package com.project.jhasan.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.jhasan.servicemodel.serviceInfo;
import com.project.jhasan.soudagor.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jhasan on 27/6/19.
 */


public class AddServcie extends Fragment implements AdapterView.OnItemSelectedListener {



    private ImageView btnChoose,btnDone , imageView;
    Button btnUpload;

    private Uri filepath;

    private final int PICK_IMAGE_REQUEST = 71;


    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase Firedatabase;


    //dummy
    private String userName;




    public AddServcie() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String[] servicesSpinner={"Automotive","Beauty","Computer","creative", "Event", "Farm+garden","Household","labor/move","travel/vac",
                                    "other" };

        List<serviceInfo>serviceInfos=new ArrayList<>();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Inflate the layout for this fragment
        View fragView= inflater.inflate(R.layout.fragment_add_servcie, container, false);

        Spinner spin=fragView.findViewById(R.id.spinner);

        btnChoose=fragView.findViewById(R.id.btn_addImage);
        imageView = fragView.findViewById(R.id.ImageView);
        btnDone=fragView.findViewById(R.id.btn_done);
        btnUpload=fragView.findViewById(R.id.button_up);


        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the servicce list

        ArrayAdapter adapterSpin = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,servicesSpinner);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapterSpin);


        // Choose image

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        //Upload Image

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        /// Complete adding info
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allDone();
            }
        });


       return fragView;

    }

    private void allDone() {


    }

    private void uploadImage() {

        if(filepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddServcie.this.getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddServcie.this.getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parentview, View selectedItemview, int position, long id) {

        String item= parentview.getItemAtPosition(position).toString();
        CharSequence text=item;

        if (item != null) {
            Toast.makeText(getContext(), item,
                    Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), "Selected",
                Toast.LENGTH_SHORT).show();

//        Toast.makeText(parentview.getContext(),item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
}
