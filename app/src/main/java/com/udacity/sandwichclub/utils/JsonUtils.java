package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichJson = new JSONObject(json);
        JSONObject name = sandwichJson.getJSONObject("name");

        //JSONObject mainNameJson = name.getJSONObject("mainName");
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
        sandwich.setPlaceOfOrigin(placeOfOriginString);

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        List<String> ingredientsList = new ArrayList<String>();
        for(int i = 0; i < ingredientsArray.length(); i++){
            ingredientsList.add(ingredientsArray.getString(i));
        }
        sandwich.setIngredients(ingredientsList);


        return sandwich;
    }
}
