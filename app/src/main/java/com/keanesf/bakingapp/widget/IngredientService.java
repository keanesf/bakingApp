package com.keanesf.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.activties.RecipeDetailActivity;

public class IngredientService extends IntentService {

    public static final String ACTION_CHANGE_INGREDIENT_LIST = "com.keanesf.bakingapp.widget.action.change_list";

    public IngredientService() {
        super("IngredientService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHANGE_INGREDIENT_LIST.equals(action)) {
                handleActionChangeIngredientList();
            }
        }
    }

    public static boolean startActionChangeIngredientList(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_CHANGE_INGREDIENT_LIST);

        // a temporary solution for Android 8.0
        try {
            context.startService(intent);
            return true;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void handleActionChangeIngredientList() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        RecipeIngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, RecipeDetailActivity.recipeTitle, appWidgetIds);
    }

}
