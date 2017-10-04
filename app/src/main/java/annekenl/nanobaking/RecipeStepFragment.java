package annekenl.nanobaking;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import annekenl.nanobaking.recipedata.StepItem;

/**
 * Displays and controls data for a single recipe step.
 */

public class RecipeStepFragment extends RecipeDetailNavFragment implements ExoPlayer.EventListener
{
    public static final String RECIPE_STEP = "recipe_step";

    private StepItem mRecipeStep;

    public RecipeStepFragment() {}

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private String testUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb88_6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake/6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake.mp4";

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

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        View cardView = inflater.inflate(R.layout.recipe_card_details_square, container, false);

        /* let's see step data to start */
        TextView genericTV = new TextView(getActivity());

        genericTV.setText("ID: " + mRecipeStep.getId()+ "\n" +
                "Short Desc: " + mRecipeStep.getShortDesc() + "\n" +
                "Desc: " + mRecipeStep.getDescription() + "\n" +
                "Video Url: " + mRecipeStep.getVideoUrl() + "\n" +
                "Thumbnail Url: " + mRecipeStep.getThumbnailUrl());

        ((ViewGroup) cardView).addView(genericTV);
        ((ViewGroup) rootView).addView(cardView);

        // Initialize the player.
        initializePlayer(Uri.parse(testUrl));

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
        releasePlayer();
        //mMediaSession.setActive(false);
    }
}
