package com.keanesf.bakingapp.activties;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.fragments.IngredientDetailFragment;
import com.keanesf.bakingapp.fragments.RecipeIngredientFragment;
import com.keanesf.bakingapp.fragments.RecipeStepFragment;
import com.keanesf.bakingapp.fragments.RecipeStepDetailFragment;
import com.keanesf.bakingapp.models.Ingredient;
import com.keanesf.bakingapp.models.Recipe;
import com.keanesf.bakingapp.models.RecipeStep;
import com.keanesf.bakingapp.widget.IngredientService;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeIngredientFragment.OnIngredientItemClickListener,
        RecipeStepFragment.OnStepItemClickListener {

    private Recipe recipe;
    private boolean mTwoPane;
    private boolean mIngredientSelected = true;
    private RecipeStep stepsModelSave;
    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.step_title_textView) TextView stepTitleTextView;
    @BindView(R.id.parent_container) FrameLayout parentContainer;
    private Unbinder unbinder;
    public static String recipeTitle = "Recipe Title";
    public static List<Ingredient> ingredients = new ArrayList<Ingredient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipe = (Recipe) bundle.getSerializable("recipe");
        mTwoPane = false;
        titleTextView.setText(recipe.getName());
        getSupportActionBar().setTitle(recipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientFragment ingredientFragment = new RecipeIngredientFragment();
        ingredientFragment.setIngredients(recipe.getIngredients());

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_ingredient_container, ingredientFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_ingredient_container, ingredientFragment)
                    .commit();
        }

        RecipeStepFragment stepFragment = new RecipeStepFragment();
        stepFragment.setStepsModelList(recipe.getSteps());

        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_container, stepFragment)
                .commit();

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            IngredientDetailFragment ingredientDetailFragment = new IngredientDetailFragment();
            ingredientDetailFragment.setIngredients(recipe.getIngredients());
            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.detail_container, ingredientDetailFragment)
                        .commit();
            } else {
                Bundle bundle1 = savedInstanceState.getBundle("bun");
                mIngredientSelected = bundle1.getBoolean("bol");
                if (mIngredientSelected) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, ingredientDetailFragment)
                            .commit();
                } else {
                    RecipeStep stepsModel = (RecipeStep) bundle1.getSerializable("ser");
                    stepsModelSave = stepsModel;
                    RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                    recipeStepDetailFragment.setRecipeStep(stepsModel);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, recipeStepDetailFragment)
                            .commit();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ser", stepsModelSave);
        bundle.putBoolean("bol", mIngredientSelected);
        outState.putBundle("bun", bundle);
    }

    @Override
    public void onIngredientItemClicked(List<Ingredient> ingredients) {
        if (mTwoPane) {
            mIngredientSelected = true;
            IngredientDetailFragment ingredientDetailFragment = new IngredientDetailFragment();
            ingredientDetailFragment.setIngredients(ingredients);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, ingredientDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, IngredientDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", (Serializable) ingredients);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onStepItemClicked(RecipeStep recipeStep) {
        if (mTwoPane) {
            stepsModelSave = recipeStep;
            mIngredientSelected = false;
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setRecipeStep(recipeStep);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, recipeStepDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ser", recipeStep);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        boolean recipeAdded;
        if (itemId == R.id.action_add) {
            recipeTitle = recipe.getName();
            ingredients = recipe.getIngredients();
            recipeAdded = IngredientService.startActionChangeIngredientList(this);

            if (recipeAdded)
                Snackbar.make(parentContainer, R.string.widget_added_text, Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(parentContainer, R.string.widget_not_added_text, Snackbar.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
