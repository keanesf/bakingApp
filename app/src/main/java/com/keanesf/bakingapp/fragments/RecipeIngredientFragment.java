package com.keanesf.bakingapp.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeIngredientFragment extends Fragment {

    private List<Ingredient> ingredients;
    private OnIngredientItemClickListener clickListener;
    @BindView(R.id.ingredient_title_textView) TextView ingredientTitle;

    public interface OnIngredientItemClickListener {
        void onIngredientItemClicked(List<Ingredient> ingredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickListener = (OnIngredientItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnIngredientItemClickListener");
        }
    }

    public RecipeIngredientFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.card_ingredient)
    void onClick() {
        clickListener.onIngredientItemClicked(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
