package com.keanesf.bakingapp.activties;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.fragments.MasterListFragment;
import com.keanesf.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity implements MasterListFragment.RecipeListener {

    @BindView(R.id.recipe_list_title) TextView title;
    @BindView(R.id.nested_scroll_view_mainActivity) NestedScrollView nestedScrollView;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_container, new MasterListFragment())
                    .commit();
        }

    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showError() {
        Snackbar.make(nestedScrollView, getString(R.string.error_loading_data), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, new MasterListFragment())
                        .commit();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

