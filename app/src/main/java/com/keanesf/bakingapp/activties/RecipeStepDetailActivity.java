package com.keanesf.bakingapp.activties;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.fragments.RecipeStepDetailFragment;
import com.keanesf.bakingapp.models.RecipeStep;


public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            RecipeStep recipeStep = (RecipeStep) bundle.getSerializable("ser");

            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setRecipeStep(recipeStep);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, recipeStepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

}
