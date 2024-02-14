package com.space_invaders

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

class SoundPlayer
    constructor(private val context: Context) {

    private val soundPool: SoundPool = SoundPool
        .Builder()
        .setMaxStreams(10)
        .setAudioAttributes(
            AudioAttributes
                .Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        .build()

    init {
        try {
            val assetManager = this.context.assets

            playerDown = this.soundPool.load(assetManager.openFd("player_down.ogg"), 0)
            invaderDown = this.soundPool.load(assetManager.openFd("invader_down.ogg"), 0)
            shieldDamage = this.soundPool.load(assetManager.openFd("shield_damage.ogg"), 0)
            shot = this.soundPool.load(assetManager.openFd("shot.ogg"), 0)
        }
        catch (e: IOException) {
            Log.e("error", "Cannot load sound files!")
        }
    }

    fun play (sound_id: Int) {
        this.soundPool.play(sound_id, 1f, 1f, 0, 0, 1f)
    }

    companion object {
        var playerDown = -1
        var invaderDown = -1
        var shieldDamage = -1
        var shot = -1
        var oh = -1
        var uh = -1
    }
}