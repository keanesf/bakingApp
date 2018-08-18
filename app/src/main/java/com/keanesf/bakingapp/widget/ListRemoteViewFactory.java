package com.keanesf.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.activties.RecipeDetailActivity;
import com.keanesf.bakingapp.models.Ingredient;

import java.util.ArrayList;
import java.util.List;


public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<Ingredient> modelList = new ArrayList<>();

    public ListRemoteViewFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        modelList = RecipeDetailActivity.ingredients;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (modelList == null) return 0;
        return modelList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);
        remoteViews.setTextViewText(R.id.widget_list_view_text_ingredient, modelList.get(i).getIngredient());
        remoteViews.setTextViewText(R.id.widget_list_view_text_quantity, modelList.get(i).getQuantity() + "");
        remoteViews.setTextViewText(R.id.widget_list_view_text_measure, modelList.get(i).getMeasure());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
