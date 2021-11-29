package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Create a new Sandwich object in which we will add info from JSON
        Sandwich sandwich = new Sandwich();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");

            String mainNameString = name.getString("mainName");
            sandwich.setMainName(mainNameString);

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownList = new ArrayList<String>();
            for(int i = 0; i < alsoKnownAsArray.length(); i++){
                alsoKnownList.add(alsoKnownAsArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownList);

            String placeOfOriginString = sandwichJson.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOriginString);

            String descriptionString = sandwichJson.getString("description");
            sandwich.setDescription(descriptionString);

            String imageString = sandwichJson.getString("image");
            sandwich.setImage(imageString);

            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<String>();
            for(int i = 0; i < ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the JSON", e);
        }
        return sandwich;
    }
}
