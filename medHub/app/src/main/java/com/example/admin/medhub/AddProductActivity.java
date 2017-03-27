package com.example.admin.medhub;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Admin on 25-03-2017.
 */

public class AddProductActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    public EditText tradeName, price, manufacturerName, unit, quantity;
    private Button submit;
    private static final String[] methods = {"Oral", "Ophthalmic", "Inhalation", "Parenteral", "Topical", "Suppository"};
    private ImageView imageView;
    private static final int SELECT_PICTURE = 100;
    public String str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type;
    public DatabaseReference mDatabase;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://medhub-e6d40.appspot.com");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        tradeName = (EditText) findViewById(R.id.ed_tradeName);
        price = (EditText) findViewById(R.id.ed_price);
        manufacturerName = (EditText) findViewById(R.id.ed_manufacturerName);
        unit = (EditText) findViewById(R.id.ed_unit);
        quantity = (EditText) findViewById(R.id.ed_quantity);

        submit = (Button) findViewById(R.id.btn_submit);

        imageView = (ImageView) findViewById(R.id.imageViewAdd);

        Firebase.setAndroidContext(this);

        spinner = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProductActivity.this,
                android.R.layout.simple_spinner_item, methods);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        onImageViewClick(); // for selecting an Image from gallery.
        onUploadButtonClick();

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                str_type = "Oral";
                break;
            case 1:
                str_type = "Ophthalmic";
                break;
            case 2:
                str_type = "Inhalation";
                break;
            case 3:
                str_type = "Parenteral";
                break;
            case 4:
                str_type = "Topical";
                break;
            case 5:
                str_type = "Suppository";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onImageViewClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);

                }
            }
        }
    }

    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    protected void onUploadButtonClick() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_manufacturerName = manufacturerName.getText().toString();
                str_price = price.getText().toString();
                str_quantity = quantity.getText().toString();
                str_tradeName = tradeName.getText().toString();
                str_unit = unit.getText().toString();

                StorageReference myfileRef = storageRef.child(str_tradeName);
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = myfileRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(AddProductActivity.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddProductActivity.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String DOWNLOAD_URL = downloadUrl.getPath();
                        Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                        Toast.makeText(AddProductActivity.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                    }
                });

                Data medData = new Data(str_tradeName, str_price, str_manufacturerName, str_unit, str_quantity, str_type);
                mDatabase.child(str_tradeName).setValue(medData);
            }
        });


    }
}