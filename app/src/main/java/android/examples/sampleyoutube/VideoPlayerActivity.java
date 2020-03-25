package android.examples.sampleyoutube;
import android.content.Intent;
import android.examples.sampleyoutube.fragments.ChannelFragment;
import android.examples.sampleyoutube.model.YouTubeDataModel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AIzaSyArVRpq8XfgbKDUjlN0KpmrzMt9sE3Ar0s";
    private YouTubePlayerView playerView = null;
    private YouTubeDataModel youTubeDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        youTubeDataModel = getIntent().getParcelableExtra(YouTubeDataModel.class.toString());
        Log.e("Kill", youTubeDataModel.getVideoId());

        playerView = findViewById(R.id.youtube_player);
        playerView.initialize(API_KEY, VideoPlayerActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getYouTubePlayerProvider().initialize(API_KEY, VideoPlayerActivity.this);
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(youTubeDataModel.getVideoId());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    }
}
