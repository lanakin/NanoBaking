package annekenl.nanobaking.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import annekenl.nanobaking.R;
import annekenl.nanobaking.recipedata.RecipeItem;


/**
 * Created by annekenl
 *
 * References: https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
 * Udacity Android Nanodegree widget lessons
 * http://www.vogella.com/tutorials/AndroidWidgets/article.html
 * https://laaptu.wordpress.com/2013/07/24/populate-appwidget-listview-with-remote-datadata-from-web/
 * and others listed in MyWidgetProvider.
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context mContext;

    private ArrayList<RecipeItem> mWidgetListItems;

    private MyWidgetDataHelper mDataHelper;


    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    //called when the appwidget is created for the first time.
    @Override
    public void onCreate() {
        mDataHelper = new MyWidgetDataHelper(mContext);
        mWidgetListItems = mDataHelper.getRecipeItems();
    }

    @Override
    public void onDestroy() { }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        //to do...
        //Log.e("notify","test");
        //mWidgetListItems = mDataHelper.getRecipeItems();
    }

    @Override
    public int getCount() {
        return mWidgetListItems.size();
    }

    //processes & returns a RemoteViews object - here a single list item.
    @Override
    public RemoteViews getViewAt(int position)
    {
        if (position == AdapterView.INVALID_POSITION) {
            return null;
        }

        RecipeItem currListItem = mWidgetListItems.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.my_widget_list_row);

        rv.setTextViewText(R.id.myWidgetListItemTV, currListItem.getName()); //recipe names

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(MyWidgetProvider.ACTION_TOAST);

        //widget will change to show ingredients for the chosen recipe
        final Bundle bundle = new Bundle();
        bundle.putString(MyWidgetProvider.WIDGET_RECIPE_NAME,currListItem.getName());

        String ingredsListAsAString = MyWidgetDataHelper.getIngredientsListString(currListItem);
        bundle.putString(MyWidgetProvider.WIDGET_INGREDS_STR,ingredsListAsAString);

        fillInIntent.putExtras(bundle);

        rv.setOnClickFillInIntent(R.id.myWidgetListItemRow, fillInIntent); //set on root view of item layout

        return rv;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
