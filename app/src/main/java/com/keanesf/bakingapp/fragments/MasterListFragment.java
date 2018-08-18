package com.keanesf.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.adapaters.RecipeAdapter;
import com.keanesf.bakingapp.models.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MasterListFragment extends Fragment implements RecipeAdapter.ItemClickListener{

    private List<Recipe> recipes;
    private final Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
    @BindView(R.id.recipe_list) RecyclerView recipeList;
    @BindView(R.id.spin_kit) SpinKitView spinKitView;
    private RecipeAdapter recipeAdapter;
    private RecipeListener recipeClickListener;

    public interface RecipeListener {
        void onRecipeClicked(Recipe recipe);
        void showError();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            recipeClickListener = (RecipeListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeListener");
        }
    }

    public MasterListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        ButterKnife.bind(this, rootView);
        recipeAdapter = new RecipeAdapter(this);
        recipeList.setNestedScrollingEnabled(false);

        if (rootView.findViewById(R.id.check_view) != null)
            recipeList.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        else
            recipeList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        recipeList.setHasFixedSize(true);
        recipeList.setAdapter(recipeAdapter);

        recipes = new Gson().fromJson(readJSONFromAsset(), recipeListType);
        recipeAdapter.setRecipes(recipes);

        return rootView;
    }

    @Override
    public void onClick(Recipe recipe) {
        recipeClickListener.onRecipeClicked(recipe);
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
