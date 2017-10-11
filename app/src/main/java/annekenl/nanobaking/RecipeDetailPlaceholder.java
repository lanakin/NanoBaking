package annekenl.nanobaking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * On tablet view -this fragment is loaded at start up to  show a background image in the
 * detail container before a recipe is chosen.
 */

public class RecipeDetailPlaceholder extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_list_startup_bkgndimg, container, false);

        return rootView;
    }

}
