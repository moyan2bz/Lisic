package com.musicplayer.elegant.service;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.musicplayer.elegant.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放控制器，负责处理音乐播放的核心逻辑
 */
public class PlayerController implements AudioManager.OnAudioFocusChangeListener {
    private final Context context;
    private ExoPlayer exoPlayer;
    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    
    private List<Song> playlist = new ArrayList<>();
    private int currentSongIndex = -1;
    private boolean isPlaying = false;
    
    // 播放模式
    public enum PlayMode {
        NORMAL,     // 顺序播放
        REPEAT_ONE, // 单曲循环
        REPEAT_ALL, // 列表循环
        SHUFFLE     // 随机播放
    }
    
    private PlayMode currentPlayMode = PlayMode.NORMAL;
    
    public PlayerController(@NonNull Context context) {
        this.context = context;
    }
    
    public void initialize() {
        initializePlayer();
        initializeAudioFocus();
    }
    
    private void initializePlayer() {
        exoPlayer = new SimpleExoPlayer.Builder(context).build();
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    handlePlaybackCompletion();
                }
            }
        });
    }
    
    private void initializeAudioFocus() {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setOnAudioFocusChangeListener(this)
                    .build();
        }
    }
    
    @Override
    public void onAudioFocusChange(int focusChange) {
        // 处理音频焦点变化
    }
    
    // 其他播放控制方法...
}