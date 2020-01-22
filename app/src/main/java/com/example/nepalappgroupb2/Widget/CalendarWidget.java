package com.example.nepalappgroupb2.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import com.example.nepalappgroupb2.Calendar.CalendarRcView;
import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;
import com.example.nepalappgroupb2.R;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class CalendarWidget extends AppWidgetProvider {
    ProgressBarFragment progressBar = new ProgressBarFragment();
    CalendarWidgetLogic widgetLogic = new CalendarWidgetLogic();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            //taking current month from sharedprefs
            int month = progressBar.monthsOld(context);

            for (int id : appWidgetIds) {
                //finding views
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_calender);

                //setting the texts on title and description
                widgetLogic.setWidgetText(context, views, month);

                //updating the widget
                appWidgetManager.updateAppWidget(id, views);
            }
        }
    }
}

