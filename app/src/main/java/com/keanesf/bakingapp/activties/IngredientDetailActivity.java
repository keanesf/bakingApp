package com.keanesf.bakingapp.activties;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.fragments.IngredientDetailFragment;
import com.keanesf.bakingapp.models.Ingredient;


import java.util.List;

public class IngredientDetailActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
        ingredients = (List<Ingredient>) bundle.getSerializable("key");

        IngredientDetailFragment detailFragment = new IngredientDetailFragment();
        detailFragment.setIngredients(ingredients);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.ingredient_detail_container, detailFragment)
                .commit();
    }

}
