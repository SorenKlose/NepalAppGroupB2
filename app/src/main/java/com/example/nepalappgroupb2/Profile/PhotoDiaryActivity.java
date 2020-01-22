package com.example.nepalappgroupb2.Profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.nepalappgroupb2.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    //ImageView test;
    ImageView cameraButton;


    String currentImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_diary);

        //test = (ImageView) findViewById(R.id.test);

        gridView = (GridView) findViewById(R.id.gridView);
        cameraButton = (ImageView) findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(this);

        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PopupImage.class);
                intent.putExtra("id", i);
                // skal ikke være currentImagePath
                intent.putExtra("image_path", currentImagePath);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        savePicture();
        ImageAdapter imageAdapter = new ImageAdapter(this);

        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(MediaStore.EXTRA_OUTPUT));
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.birthday);
        imageAdapter.images.add(bitmap1);
    }

    public void savePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = null;
        try{
            imageFile = getImageFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(imageFile != null){
            Uri imageUri = FileProvider.getUriForFile(this,
                    "com.example.nepalappgroupb2.fileprovider",
                    imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        startActivityForResult(intent, 1);
    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", storageDirectory);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            ImageAdapter imageAdapter = new ImageAdapter(this);

            Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(MediaStore.EXTRA_OUTPUT));
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.birthday);
            imageAdapter.images.add(bitmap1);
            //test.setImageBitmap(bitmap1);
            System.out.println(imageAdapter.getCount());
        }
    }
}
