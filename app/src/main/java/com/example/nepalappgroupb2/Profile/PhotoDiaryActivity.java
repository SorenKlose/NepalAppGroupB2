package com.example.nepalappgroupb2.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
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
import java.util.Map;

public class PhotoDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gridView;
    ImageView cameraButton;
    String currentImagePath = null;
    ImageAdapter imageAdapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_diary);
        imageAdapter = new ImageAdapter(this);

        sp = getSharedPreferences("images", Context.MODE_PRIVATE);
        final Map<String,?> keys = sp.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()) {
            String imagePath = entry.getValue().toString();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageAdapter.images.add(bitmap);
        }

        gridView = (GridView) findViewById(R.id.gridView);
        cameraButton = (ImageView) findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PopupImage.class);

                String imagePath = sp.getString(String.valueOf(position),null);
                intent.putExtra("image_path", imagePath );
                startActivity(intent);
            }
        });
        getSupportActionBar().setTitle(R.string.diary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // brugeren vil navigere op i hierakiet
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imageFile = null;
            imageFile = makeImageFile();
            if(imageFile != null){
                Uri imageUri = FileProvider.getUriForFile(this,
                        "com.example.nepalappgroupb2.fileprovider",
                        imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
            startActivityForResult(intent, 1);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private File makeImageFile() throws IOException {
        SharedPreferences sp = getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

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
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentImagePath);
            imageAdapter.images.add(bitmap);
            imageAdapter.notifyDataSetChanged();

            String index = String.valueOf(imageAdapter.images.indexOf(bitmap));

            sp = getSharedPreferences("images", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(index, currentImagePath);
            editor.apply();
        }
    }
}
