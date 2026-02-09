package iti.student.foodo.features.utils;

import android.text.TextUtils;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtils {

    public static void loadVideoPaused(
            YouTubePlayerView youTubePlayerView,
            String videoId
    ) {
        youTubePlayerView.addYouTubePlayerListener(
                new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer player) {
                        youTubePlayerView.setTag(player);
                        player.cueVideo(videoId, 0f);
                    }
                }
        );
    }

    public static void playVideo(YouTubePlayerView view) {
        YouTubePlayer player = getPlayer(view);
        if (player != null) player.play();
    }

    public static void pauseVideo(YouTubePlayerView view) {
        YouTubePlayer player = getPlayer(view);
        if (player != null) player.pause();
    }

    public static void stopVideo(YouTubePlayerView view) {
        YouTubePlayer player = getPlayer(view);
        if (player != null) {
            player.pause();
            player.seekTo(0f);
        }
    }

    public static String getVideoId(String url) {
        if (TextUtils.isEmpty(url)) return "";

        String regex =
                "(?:youtube\\.com/(?:[^/\\n\\s]+/\\S+/|(?:v|e(?:mbed)?)/|.*[?&]v=)|youtu\\.be/)([a-zA-Z0-9_-]{11})";

        Matcher matcher = Pattern.compile(regex).matcher(url);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static YouTubePlayer getPlayer(YouTubePlayerView view) {
        Object tag = view.getTag();
        return tag instanceof YouTubePlayer ? (YouTubePlayer) tag : null;
    }
}
