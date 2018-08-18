package com.keanesf.bakingapp.adapaters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ItemViewHolder> {

    private List<Recipe> recipes;
    private final ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String recipeName = recipes.get(position).getName();
        String imagePath = recipes.get(position).getImage();
        int recipePic;

        switch (recipeName) {
            case "Nutella Pie":
                recipePic = R.drawable.nutella_pie;
                break;
            case "Brownies":
                recipePic = R.drawable.brownies;
                break;
            case "Yellow Cake":
                recipePic = R.drawable.yellow_cake;
                break;
            case "Cheesecake":
                recipePic = R.drawable.cheesecake;
                break;
            default:
                recipePic = R.drawable.image_placeholder;
        }

        holder.recipeTitle.setText(recipeName);

        if (imagePath.equals("")) {
            Picasso.with(context)
                    .load(recipePic)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(holder.recipeThumbnail);
        } else {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(holder.recipeThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_thumbnail) ImageView recipeThumbnail;
        @BindView(R.id.recipe_title) TextView recipeTitle;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemClickListener.onClick(recipes.get(position));
        }

    }
}