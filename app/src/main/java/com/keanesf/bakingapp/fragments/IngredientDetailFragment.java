package com.keanesf.bakingapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.adapaters.IngredientAdapter;
import com.keanesf.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientDetailFragment extends Fragment {

    private List<Ingredient> ingredients;
    @BindView(R.id.ingredient_detail_list) RecyclerView ingredientList;
    private IngredientAdapter adapter;


    public IngredientDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_detail, container, false);
        ButterKnife.bind(this, rootView);
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new IngredientAdapter();
        ingredientList.setAdapter(adapter);
        ingredientList.setNestedScrollingEnabled(false);
        adapter.setIngredients(ingredients);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("state", ingredientList.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("state");
            ingredientList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
