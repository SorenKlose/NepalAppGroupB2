package com.example.nepalappgroupb2.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.example.nepalappgroupb2.Calendar.CalendarLogic;
import com.example.nepalappgroupb2.Calendar.CalendarRcView;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Domain.DataService;
import com.example.nepalappgroupb2.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CalendarWidgetLogic {

    DataFromSheets db = new DataFromSheets();
    CalendarRcView calendar = new CalendarRcView();
    CalendarLogic calendarActLogic = new CalendarLogic();

    public String getWidgetInfoText(final Context context, int monthToShow) {
        try {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        db.fromSheets(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                    //.get waits for background thread to finish
                }
                // get means that we wait for the background thread to finish
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //finding the month info to show
        List<String> texts = DataService.getMessageOfMonth(context.getString(R.string.chosen_language), monthToShow);
        StringBuilder textToShow = new StringBuilder();
        for (int i = 0; i < texts.size(); i++) {
            if (i > 0) {
                textToShow.append("\n\n");
            }
            textToShow.append(texts.get(i));
        }
        return textToShow.toString();
    }

    public void updateWidget(Context context, int month) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_calender);
        ComponentName thisWidget = new ComponentName(context, CalendarWidget.class);
        /**
         * These 3 lines is how you can update a widget from an activity.
         * Taken from src= https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
         */

        //setting text on widget
        setWidgetText(context, remoteViews, month);

        //updating the widget
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    //set text on both the widget title and the description
    public void setWidgetText(Context context, RemoteViews remoteViews, int month) {
        List<Integer> monthList = DataService.getMonthsFromData(context);
        int monthIndex = calendarActLogic.scrollToMonth(month, monthList);
        int monthToShow = monthList.get(monthIndex);

        //setting text for widget title
        String titleText;
        if (monthToShow < 0) {
            titleText = (monthToShow + 10) + " " + context.getString(R.string.month_preg);
        } else {
            titleText = monthToShow + " " + context.getString(R.string.month);
        }
        remoteViews.setTextViewText(R.id.widget_title, titleText);

        //setting text for widget description (info for the month)
        String widgetText = getWidgetInfoText(context, monthToShow);
        remoteViews.setTextViewText(R.id.widget_desc, widgetText);
    }

}
