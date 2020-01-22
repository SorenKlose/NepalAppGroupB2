package com.example.nepalappgroupb2.Widget;

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
import com.example.nepalappgroupb2.R;

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
        //finding indices for the info text
        List<Integer> monthList = DataService.getMonthsFromData(context);
        int monthIndex = calendar.scrollToMonth(month, monthList);
        int monthToShow = monthList.get(monthIndex);
        System.out.println("monthindex i widget: " + monthIndex);
        System.out.println("monthtoshow i widget: " + monthToShow);

        //all the info text for the given month
        List<String> texts = DataService.getMessageOfMonth(context.getString(R.string.chosen_language), monthToShow);
        System.out.println("widget: " + texts);

        for(int id: appWidgetIds) {
            System.out.println("jeg er her");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_calender);

            //setting the title of the widget
            String titleText;
            if(monthToShow < 0) {
                titleText = (monthToShow+10) + " " + context.getString(R.string.month_preg);
            } else {
                titleText = monthToShow + " " + context.getString(R.string.month);
            }
            views.setTextViewText(R.id.widget_title, titleText);

            //combining the info texts to a single string
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

