package com.keanesf.bakingapp.adapaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.StepViewHolder> {

    private List<RecipeStep> recipeSteps;
    private final StepItemClickListener itemClickListener;

    public interface StepItemClickListener {
        void onClickStep(RecipeStep recipeStep);
    }

    public RecipeStepAdapter(StepItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_item_layout, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.stepDescription.setText(recipeSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (recipeSteps == null)return 0;
        return recipeSteps.size();
    }

    public void setRecipeSteps(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_description) TextView stepDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemClickListener.onClickStep(recipeSteps.get(position));
        }
    }

}
