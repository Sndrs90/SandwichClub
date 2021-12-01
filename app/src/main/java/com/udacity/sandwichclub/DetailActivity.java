package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.StringJoiner;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            //Log.i("Details", "ImagePath " + sandwich.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        setTitle(sandwich.getMainName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void populateUI(Sandwich sandwich) {
        // Find all TextViews
        TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        TextView alsoKnownTv = (TextView) findViewById(R.id.also_known_tv);
        TextView originTv = (TextView) findViewById(R.id.origin_tv);
        TextView ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);

        TextView descriptionLabel = (TextView) findViewById(R.id.description_label);
        LinearLayout alsoKnownAll = (LinearLayout) findViewById(R.id.also_known_all);
        LinearLayout originAll = (LinearLayout) findViewById(R.id.origin_all);
        TextView ingredientsLabel = (TextView) findViewById(R.id.ingredients_label);

        // Hide TextViews if sandwich doesn't contain these fields
        // elsewhere set the text
        if (TextUtils.isEmpty(sandwich.getDescription())) {
            descriptionLabel.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAll.setVisibility(View.GONE);
        } else {
            // Delete last delimiter \n in List<String> output
            alsoKnownTv.setText(String.join("\n", sandwich.getAlsoKnownAs()));
        }

        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            originAll.setVisibility(View.GONE);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(String.join("\n", sandwich.getIngredients()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
