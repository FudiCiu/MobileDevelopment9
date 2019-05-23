package com.latihanandroid.videoyoutube;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final  int RECORVERY_DIALOG_REQUEST=1;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        youTubePlayerView= (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(Config.DEVELOPER_KEY,this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_KEY);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,RECORVERY_DIALOG_REQUEST).show();
        }
        else {
            String errorMessage= String.format(getString(R.string.error_player)+ youTubeInitializationResult.toString());
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==RECORVERY_DIALOG_REQUEST){
            getYoutubePlayerProvider().initialize(Config.DEVELOPER_KEY,this);
        }
    }

    private YouTubePlayer.Provider getYoutubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
