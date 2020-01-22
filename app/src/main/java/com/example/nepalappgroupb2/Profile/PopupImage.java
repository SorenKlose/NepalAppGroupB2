package com.example.nepalappgroupb2.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepalappgroupb2.R;

public class PopupImage extends AppCompatActivity {

    ImageView imageView;
    Button saveAsProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_image);

        imageView = (ImageView) findViewById(R.id.imageView);
        saveAsProfilePic = (Button) findViewById(R.id.saveAsProfilePic);

        Intent intent = getIntent();

        final String imagePath = intent.getExtras().getString("image_path");
        final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        imageView.setImageBitmap(bitmap);

        saveAsProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.imagePath = imagePath;
                Toast.makeText(getApplication().getBaseContext(),"Saved",Toast.LENGTH_LONG).show();
            }
        });
    }
}
