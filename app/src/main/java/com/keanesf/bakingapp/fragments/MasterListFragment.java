package com.keanesf.bakingapp.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.adapaters.RecipeAdapter;
import com.keanesf.bakingapp.models.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private final String RECIPEURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

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

        new FetchRecipiesTask().execute(RECIPEURL);

        return rootView;
    }

    @Override
    public void onClick(Recipe recipe) {
        recipeClickListener.onRecipeClicked(recipe);
    }

    private class FetchRecipiesTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            recipes = new Gson().fromJson(result, recipeListType);
            recipeAdapter.setRecipes(recipes);
        }
    }

}
