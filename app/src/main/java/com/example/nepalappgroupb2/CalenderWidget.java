package com.example.nepalappgroupb2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CalenderWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sp = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        int month = sp.getInt("month", -100);
        System.out.println("month i onUpdate: " + month);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        for(int id: appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_calender);
            views.setTextViewText(R.id.widget_title, "Måned 2");
            views.setTextViewText(R.id.widget_desc, "Desc for måned 2");

            appWidgetManager.updateAppWidget(id, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        SharedPreferences sp = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        int month = sp.getInt("month", -100);
        System.out.println("month i onEnabled: " + month);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

