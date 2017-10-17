package annekenl.nanobaking.widget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import annekenl.nanobaking.R;

/**
 * Created by annekenl1
 *
 * "A convenience class to aid in implementing an AppWidget provider.
 * Everything you can do with AppWidgetProvider, you can do with a regular BroadcastReceiver.
 * AppWidgetProvider merely parses the relevant fields out of the Intent that is received
 * in onReceive(Context,Intent), and calls hook methods with the received extras.
 *
 * Extend this class and override one or more of the onUpdate(Context, AppWidgetManager, int[]),
 * onDeleted(Context, int[]), onEnabled(Context) or onDisabled(Context) methods to implement your
 * own AppWidget functionality."
 * https://developer.android.com/reference/android/appwidget/AppWidgetProvider.html
 *
 * public void onReceive(android.content.Context context, android.content.Intent intent) { }
 * public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds) { }
 * public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle newOptions) { }
 * public void onDeleted(android.content.Context context, int[] appWidgetIds) { }
 * public void onEnabled(android.content.Context context) { }
 * public void onDisabled(android.content.Context context) { }
 * public void onRestored(android.content.Context context, int[] oldWidgetIds, int[] newWidgetIds) { }
 *
 * Modeled from reference material - Udacity Android Nanodegree - Lesson 7 widgets, https://www.androidauthority.com/create-simple-android-widget-608975/,
 * https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/ & https://www.sitepoint.com/how-to-code-an-android-widget/.
 */

public class MyWidgetProvider extends AppWidgetProvider
{
    public MyWidgetProvider() { }

    /**
     * Called in response to the ACTION_APPWIDGET_UPDATE and ACTION_APPWIDGET_RESTORED
     * broadcasts when this AppWidget provider is being asked to provide RemoteViews for a set of AppWidgets.
     * Override this method to implement your own AppWidget functionality.
     */
    public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch Activity
            //Intent intent = new Intent(context, RecipeListActivity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0); //context, request code, intent, flags

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_list_layout);
                                                                            //R.layout.my_widget_basic_layout
            // setting an empty view in case of no data
            remoteViews.setEmptyView(R.id.myWidgetListView, R.id.myWidgetListEmptyView);

            //views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // To update a label
            //views.setTextViewText(R.id.widget1label, df.format(new Date()));

            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            remoteViews.setRemoteAdapter(R.id.myWidgetListView, intent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    /* Called in response to the ACTION_APPWIDGET_OPTIONS_CHANGED broadcast when this widget has been layed out at a new size. */
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager,
                                          int appWidgetId, android.os.Bundle newOptions)
    {

    }

    /* Called in response to the ACTION_APPWIDGET_ENABLED broadcast when the a AppWidget for this provider
     * is instantiated. Override this method to implement your own AppWidget functionality. */
    public void onEnabled(android.content.Context context) { }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

    /* Called in response to the ACTION_APPWIDGET_UPDATE and ACTION_APPWIDGET_RESTORED broadcasts when this AppWidget provider
     * is being asked to provide RemoteViews for a set of AppWidgets. Override this method to implement your own AppWidget functionality.
     */
    public void onRestored(android.content.Context context, int[] oldWidgetIds, int[] newWidgetIds) { }
}
