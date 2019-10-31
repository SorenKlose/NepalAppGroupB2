package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Profile2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_2);




        ImageView imFaneP = (ImageView) findViewById(R.id.imFaneP);
        imFaneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Profile2Activity.this, ProfileActivity.class);
                Profile2Activity.this.startActivity(myIntent);
                finish();
            }});
    }


}
