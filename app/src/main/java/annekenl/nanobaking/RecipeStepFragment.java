package annekenl.nanobaking;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import annekenl.nanobaking.recipedata.StepItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Displays and controls data for a single recipe step.
 */

public class RecipeStepFragment extends RecipeDetailNavFragment implements ExoPlayer.EventListener
{
    public static final String RECIPE_STEP = "recipe_step";

    private StepItem mRecipeStep;

    public RecipeStepFragment() {}

    private SimpleExoPlayer mExoPlayer;

    //private SimpleExoPlayerView mPlayerView;
    //private ImageView mImageView;
    @BindView(R.id.stepPlayerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.stepImageView) ImageView mImageView;
    private Unbinder unbinder;

    private String testUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb88_6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake/6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake.mp4";
    private String droidChefUrl = "http://cdn04.androidauthority.net/wp-content/uploads/2012/08/Android-chef.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_STEP)) {
            mRecipeStep = getArguments().getParcelable(RECIPE_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_step, container, false);
        unbinder = ButterKnife.bind(this,rootView);

        /* RECIPE STEP TEXT */
        View cardView = inflater.inflate(R.layout.recipe_card_details_square, container, false);
        TextView genericTV = new TextView(getActivity());

        /* let's see step data to start */
        /*genericTV.setText("ID: " + mRecipeStep.getId()+ "\n" +
                "Short Desc: " + mRecipeStep.getShortDesc() + "\n" +
                "Desc: " + mRecipeStep.getDescription() + "\n" +
                "Video Url: " + mRecipeStep.getVideoUrl() + "\n" +
                "Thumbnail Url: " + mRecipeStep.getThumbnailUrl());*/

        genericTV.setText(mRecipeStep.getShortDesc() + "\n\n" +
                mRecipeStep.getDescription() + "\n");

        if(RecipeDetailNavFragment.mTwoPane)
            setTextAppearance(getContext(),R.style.TextAppearance_AppCompat_Medium,genericTV);
        else
            setTextAppearance(getContext(),R.style.TextAppearance_AppCompat_Small,genericTV);

        ((ViewGroup) cardView).addView(genericTV);
        ((ViewGroup) rootView).addView(cardView);

        /* VIDEO AND IMAGE */
        String vidUrl = mRecipeStep.getVideoUrl();

        if(!vidUrl.isEmpty())
            initializePlayer(Uri.parse(vidUrl));  // Initialize the player.
        else {
            //default img ...
            mPlayerView.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
            String imgUrl = mRecipeStep.getThumbnailUrl();

            if(!imgUrl.isEmpty())
                Picasso.with(getContext()).load(imgUrl).noFade()
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .into(mImageView);
            else {
                Picasso.with(getContext()).load(droidChefUrl).noFade()
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .into(mImageView);
            }
        }
        //mPlayerView.setDefaultArtwork(
        //BitmapFactory.decodeResource(getResources(), R.drawable.question_mark));

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setupNavigationButtons(getArguments().getInt(RecipeDetailNavFragment.RECIPE_PART_NAV_ID));
    }


    //Exoplayer -- modify some sample code from Classic Music Quiz Activity project from Udacity Nanodegree
    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            //LoadControl loadControl = new DefaultLoadControl();  //deprecated
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "NanoBaking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        //mNotificationManager.cancelAll();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    // ExoPlayer Event Listeners
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) /**/
    {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }


    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            releasePlayer();
            //mMediaSession.setActive(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
