package com.keanesf.bakingapp.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.keanesf.bakingapp.R;
import com.keanesf.bakingapp.models.RecipeStep;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment {

    private RecipeStep recipeStep;
    @BindView(R.id.step_long_description) TextView description;
    @BindView(R.id.no_video_text_view) TextView noVideoTxt;
    @BindView(R.id.media_player) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.step_image) ImageView mImageView;
    private SimpleExoPlayer mExoPlayer;
    private long mPlaybackPosition = 0;
    private boolean mVideoPlayingState;
    private static final String SAVED_PLAYBACK_POSITION = "playback_position";
    private static final String VIDEO_PLAYING_STATE_KEY = "playing_state";

    public RecipeStepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (!recipeStep.getVideoURL().equals("") && (networkInfo != null && networkInfo.isConnected())) {
                noVideoTxt.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                initializePlayer(Uri.parse(recipeStep.getVideoURL()));
            }
            else if(!recipeStep.getThumbnailURL().equals("")){
                noVideoTxt.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.GONE);
                Picasso.with(mImageView.getContext())
                        .load(recipeStep.getThumbnailURL()).into(mImageView);

            }
            else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        if (savedInstanceState != null) {
            recipeStep = (RecipeStep) savedInstanceState.getSerializable("recipeStep");
            if (!recipeStep.getVideoURL().equals("")) {
                noVideoTxt.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
                mPlaybackPosition = savedInstanceState.getLong(SAVED_PLAYBACK_POSITION, 0);
                mVideoPlayingState = savedInstanceState.getBoolean(VIDEO_PLAYING_STATE_KEY);
                initializePlayer(Uri.parse(recipeStep.getVideoURL()));
            }
            else if(!recipeStep.getThumbnailURL().equals("")){
                noVideoTxt.setVisibility(View.GONE);
                mPlayerView.setVisibility(View.GONE);
                Picasso.with(mImageView.getContext())
                        .load(recipeStep.getThumbnailURL()).into(mImageView);
            }
            else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        description.setText(recipeStep.getDescription());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Baking app");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);

            // Resume playing state and playing position
            if (mPlaybackPosition != 0) {
                mExoPlayer.seekTo(mPlaybackPosition);
                mExoPlayer.setPlayWhenReady(mVideoPlayingState);
            } else {
                // Otherwise, if position is 0, the video never played and should start by default
                mExoPlayer.setPlayWhenReady(true);
            }

        }
    }

    private void releasePlayer() {
        if (!recipeStep.getVideoURL().equals("") && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("recipeStep", recipeStep);
        outState.putLong(SAVED_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putBoolean(VIDEO_PLAYING_STATE_KEY, mVideoPlayingState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoPlayingState = mExoPlayer.getPlayWhenReady();
        mPlaybackPosition = mExoPlayer.getCurrentPosition();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    public void setRecipeStep(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
    }

}
