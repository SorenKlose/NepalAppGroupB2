package com.example.nepalappgroupb2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.nepalappgroupb2.Calendar.CalendarRcView;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.Progress.ProgressBarFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of App Widget functionality.
 */
public class CalenderWidget extends AppWidgetProvider {
    ProgressBarFragment progressBar = new ProgressBarFragment();
    DataFromSheets db = new DataFromSheets();
    CalendarRcView calendar = new CalendarRcView();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        int month = progressBar.monthsOld(context);
        if(month == 0) month = 3;
        System.out.println("måned widget: " + month);

        try {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    System.out.println("starter");
                    try {
                        db.fromSheets(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            // get means that we wait for the background thread to finish
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("er done");
//        List<Integer> monthList = DataService.getMonthsFromData(context);
//        int monthToShow = calendar.scrollToMonth(month, monthList);
//        List<String> hej = db.getWithMonth(DataFromSheets.Headers.MsgEng, month);
        List<String> texts = DataService.getMessageOfMonth(context.getString(R.string.chosen_language), month-10);
        System.out.println("widget: " + texts);

        for(int id: appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_calender);
            views.setTextViewText(R.id.widget_title, month + " " + context.getString(R.string.month));

            StringBuilder textToShow = new StringBuilder();
            for(int i = 0; i < texts.size(); i++) {
                if(i > 0) {
                    textToShow.append("\n\n");
                }
                textToShow.append(texts.get(i));
            }
            views.setTextViewText(R.id.widget_desc, textToShow);

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

