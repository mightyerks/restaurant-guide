package com.example.jarone.restoguideproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewResto extends AppCompatActivity {

    TextView tvName,tvAddress,tvDescription,tvTags,tvPhone;
    RatingBar rbRating;
    Button back, btnEdit, btnDelete, map, btnShare, btnApps;

//    ShareIntent
    Intent shareIntent;
    String shareBody = "THE BEST ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resto);

        final ResturantDbHelper dbHelper = new ResturantDbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent i = getIntent();
        final Resturant resturant = (Resturant) i.getSerializableExtra("resturant");
        final int id = resturant.getId();
        final String name = resturant.getName();
        final String address = resturant.getAddress();
        final String phone = resturant.getPhone();
        final String description = resturant.getDescription();
        final String tags = resturant.getTags();
        final float rating = resturant.getRating();

        tvName = findViewById(R.id.etNameUpdate);
        tvAddress = findViewById(R.id.etAddressUpdate);
        tvPhone = findViewById(R.id.etPhoneUpdate);
        tvDescription = findViewById(R.id.etDescUpdate);
        tvTags = findViewById(R.id.etTagsUpdate);
        rbRating = findViewById(R.id.ratingBarUpdate);

        btnShare = findViewById(R.id.btnShare);

        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvDescription.setText(description);
        tvTags.setText(tags);
        rbRating.setRating(rating);

        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                String [] to= {"ppawluk@georgebrown.ca"};
                i.putExtra(Intent.EXTRA_EMAIL, to);
                i.putExtra(Intent.EXTRA_SUBJECT, "Hi, Pawluk!");
                i.putExtra(Intent.EXTRA_TEXT, "We're recommending this restaurant called " + resturant.getName()+"and its address is "+resturant.getAddress()+"."
                        +" This restaurant's phone number is "+resturant.getPhone()+".");
                i.setType("message/rfc822");
                startActivity(Intent.createChooser(i, "Send email"));
            }
        });



        findViewById(R.id.btnMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("address", address);
                startActivity(i);
            }
        });


        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewResto.this, EditResto.class);
                i.putExtra("id",id);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("phone", phone);
                i.putExtra("description", description);
                i.putExtra("tags", tags);
                i.putExtra("rating", rating);
                startActivity(i);
                setResult(RESULT_OK);
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewResto.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deletePost(db, id);
                setResult(RESULT_OK);
                finish();
            }
        });

        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(ViewResto.this, MainActivity.class);
                startActivity(list);
                finish();
            }
        });

    }
}
