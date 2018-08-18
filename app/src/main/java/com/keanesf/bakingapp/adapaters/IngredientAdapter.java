package com.keanesf.bakingapp.adapaters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {


    private List<Ingredient> ingredients;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item_layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingredientTitle.setText(ingredients.get(position).getIngredient());
        holder.ingredientQuantity.setText(" " + String.valueOf(ingredients.get(position).getQuantity()));
        holder.ingredientMeasure.setText(" " + ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_title) TextView ingredientTitle;
        @BindView(R.id.ingredient_quantity) TextView ingredientQuantity;
        @BindView(R.id.ingredient_measure) TextView ingredientMeasure;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
