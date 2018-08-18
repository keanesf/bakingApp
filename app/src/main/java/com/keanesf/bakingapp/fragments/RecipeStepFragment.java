package com.keanesf.bakingapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.adapaters.RecipeStepAdapter;
import com.keanesf.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepFragment extends Fragment implements RecipeStepAdapter.StepItemClickListener {

    private List<RecipeStep> recipeSteps;
    @BindView(R.id.step_list) RecyclerView stepList;
    private OnStepItemClickListener clickListener;

    public interface OnStepItemClickListener {
        void onStepItemClicked(RecipeStep recipeStep);
    }

    public RecipeStepFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickListener = (OnStepItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, rootView);
        RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(this);
        recipeStepAdapter.setRecipeSteps(recipeSteps);
        stepList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        stepList.setAdapter(recipeStepAdapter);
        stepList.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onClickStep(RecipeStep recipeStep) {
        clickListener.onStepItemClicked(recipeStep);
    }

    public void setStepsModelList(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }
}
