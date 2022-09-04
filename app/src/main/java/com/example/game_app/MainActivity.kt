package com.example.game_app

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.game_app.customViews.CanvasView
import com.example.game_app.interfaces.IMainActivity
import com.example.game_app.presenters.MainActivityPresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity(), IMainActivity {
    var presenter = MainActivityPresenter(this)
    private val fps = 60
    private var gameStarted = true
    override lateinit var canvasView: CanvasView
    override lateinit var windowView: View
    lateinit var restartBtn: Button
    lateinit var restartView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.canvasView = findViewById(R.id.canvasView)
        this.windowView = findViewById(R.id.windowView)
        this.restartBtn = findViewById(R.id.restartBtn)
        this.restartView = findViewById(R.id.restartView)

        windowView.setOnClickListener {
            presenter.windowViewOnClick()
        }
        canvasView.setOnClickListener {
            presenter.canvasViewOnClick()
        }

        restartBtn.setOnClickListener {
            this.startGame()
        }

        this.startGame()

        MobileAds.initialize(this) {}

        var adView: AdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun finishGame() {
        canvasView.score = 0
        canvasView.difficultCoef = 1
        canvasView.visibility = View.INVISIBLE
        this.gameStarted = false

        restartView.visibility = View.VISIBLE

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {}

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAd.show(this@MainActivity)
            }
        })
    }

    override fun startGame() {
        canvasView.visibility = View.VISIBLE
        restartView.visibility = View.INVISIBLE
        this.gameStarted = true

        // every interval change canvas view
        Thread {
            var delay = (1000 / fps).toLong()
            while (gameStarted) {
                this.runOnUiThread {
                    createFrame()
                }

                Thread.sleep(delay)
            }
        }.start()

    }

    // redraw view
    override fun createFrame() {
        canvasView.invalidate()
    }

    override fun makeSound(soundSourceId: Int) {
        var player: MediaPlayer? = MediaPlayer.create(this, soundSourceId) ?: return
        player?.start()
    }
}