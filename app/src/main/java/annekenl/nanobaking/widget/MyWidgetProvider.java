package annekenl.nanobaking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
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
 * Modeled from reference material - Udacity Android Nanodegree - Lesson 7 widgets,
 * https://www.androidauthority.com/create-simple-android-widget-608975/,
 * https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/,
 * https://www.sitepoint.com/how-to-code-an-android-widget/,
 * http://dharmangsoni.blogspot.com/2014/03/collection-widget-with-event-handling.html
 */

public class MyWidgetProvider extends AppWidgetProvider
{
    public static final String ACTION_TOAST = "annekenl.nanobaking.widget.MyWidgetProvider.ACTION_TOAST";
    //public static final String WIDGET_LIST_ITEM_KEY = "my_widget_list_item";  //error with using parcellable here...
    public static final String WIDGET_RECIPE_NAME = "widget_recipe_name";
    public static final String WIDGET_INGREDS_STR = "widget_ingreds_string";

    public MyWidgetProvider() { }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(ACTION_TOAST))
        {
            //RecipeItem recipeItem = intent.getExtras().getParcelable(WIDGET_LIST_ITEM_KEY);
            //Toast.makeText(context, intent.getExtras().getString(WIDGET_RECIPE_NAME), Toast.LENGTH_LONG).show();

            int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
            //Toast.makeText(context, "widgetID "+widgetId, Toast.LENGTH_LONG).show();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // Update remote view - after choosing a recipe the widget will convert to it's main purpose of showing
            // a list of ingredients for a desired recipe
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.my_widget_list_layout);
            remoteView.setTextViewText(R.id.myWidgetListTitle, intent.getExtras().getString(WIDGET_RECIPE_NAME));

            remoteView.setViewVisibility(R.id.myWidgetListView, View.GONE);
            remoteView.setViewVisibility(R.id.myWidgetRecipeIngredients, View.VISIBLE);
            remoteView.setTextViewText(R.id.myWidgetRecipeIngredients, intent.getExtras().getString(WIDGET_INGREDS_STR));


            appWidgetManager.updateAppWidget(widgetId, remoteView);
        }

        super.onReceive(context, intent);
    }


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

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_list_layout);
                                                                            //R.layout.my_widget_basic_layout
            // setting an empty view in case of no data
            remoteViews.setEmptyView(R.id.myWidgetListView, R.id.myWidgetListEmptyView);

            //connects the remoteviews service as the adaptor for the widget list
            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            remoteViews.setRemoteAdapter(R.id.myWidgetListView, intent);

            //set the title
            remoteViews.setTextViewText(R.id.myWidgetListTitle,"Pick A Recipe");

            // Adding collection list item handler
            // intent template to handle the click action for each item  -getView() 'fills in' each item's intent~
            final Intent onItemClick = new Intent(context, MyWidgetProvider.class);

            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            onItemClick.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.myWidgetListView,
                    onClickPendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }


    /* Called in response to the ACTION_APPWIDGET_OPTIONS_CHANGED broadcast when this widget has been layed out at a new size. */
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager,
                                          int appWidgetId, android.os.Bundle newOptions) { }


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
