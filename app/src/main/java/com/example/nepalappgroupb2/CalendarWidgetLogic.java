package com.example.nepalappgroupb2;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import com.example.nepalappgroupb2.Domain.DataFromSheets;
import com.example.nepalappgroupb2.Domain.DataService;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CalendarWidgetLogic {

    DataFromSheets db = new DataFromSheets();

    private String getWidgetText(final Context context, int month) {
        try {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    System.out.println("starter her");
                    try {
                        db.fromSheets(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
                // get means that we wait for the background thread to finish
            }.execute().get();
            System.out.println("er done her");
            System.out.println("map er: " + db.map.size());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //finding the month info to show
        List<String> texts = DataService.getMessageOfMonth(context.getString(R.string.chosen_language), month-10);
        System.out.println("listen er: " + texts);
        //List<String> texts = db.getWithMonth(DataFromSheets.Headers.MsgEng, month-10);
        //showing the text as one string on the widget
        StringBuilder textToShow = new StringBuilder();
        for(int i = 0; i < texts.size(); i++) {
            if(i > 0) {
                textToShow.append("\n\n");
            }
            textToShow.append(texts.get(i));
        }
        return textToShow.toString();
    }

    public void updateWidget(Context context, int month) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_calender);
        ComponentName thisWidget = new ComponentName(context, CalenderWidget.class);
        //setting text for widget title
        remoteViews.setTextViewText(R.id.widget_title, month + " " + context.getString(R.string.month));
        String widgetText = getWidgetText(context, month);
        //setting text for widget description (info for the month)
        remoteViews.setTextViewText(R.id.widget_desc, widgetText);
        //updating the widget
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

}
