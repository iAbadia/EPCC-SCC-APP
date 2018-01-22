package com.uoe.epcc.scc.epccscccluster

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load locale
        loadLocale()

        // Load layout
        setContentView(R.layout.activity_main)

        // Enter immersive mode
        hideSystemUI()

        // Register onClick for Menu Buttons
        opt_team.setOnClickListener { showDetail(Option.TEAM) }
        opt_cpu.setOnClickListener { showDetail(Option.CPU) }
        opt_gpu.setOnClickListener { showDetail(Option.GPU) }
        opt_network.setOnClickListener { showDetail(Option.NETWORK) }
        opt_chassis.setOnClickListener { showDetail(Option.CHASSIS) }
        opt_cooling.setOnClickListener { showDetail(Option.COOLING) }
        opt_storage.setOnClickListener { showDetail(Option.STORAGE) }
        opt_memory.setOnClickListener { showDetail(Option.MEMORY) }

        // DEBUG. Register show options onClick for image
        //img_cluster.setOnClickListener { showAllOptions() }
        img_cluster.setOnClickListener {
            // Get screen size
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getRealSize(size)
            Log.i("OnClick", "Screen size is: x = ${size.x} and y = ${size.y}")

            // Set visible but flat (width = 0dp)
            frg_info_view.visibility = View.VISIBLE

            // Give it 1/3 of screen width
            frg_info_view.layoutParams.width = size.x / 3
            frg_info_view.requestLayout()
        }

        // Set onClick for Lang buttons
        btn_locale_en.setOnClickListener { updateLang(Language.ENG) }
        btn_locale_es.setOnClickListener { updateLang(Language.ESP) }
    }

    /**
     * Enter Immersive mode. Hides status and navigation bars, both
     * will be still reachable by swiping up/down on the edge of the
     * screen
     * */
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    /**
     * Load correct language
     * */
    private fun loadLocale() {
        // Get language from SharedPreferences
        val languagepref = getSharedPreferences("language", Context.MODE_PRIVATE)
        val lang = languagepref.getString("languageToLoad", "en")

        // Set locale
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,
                baseContext.resources.displayMetrics)
    }

    /**
     * Show detail for given Option
     * */
    private fun showDetail(opt: Option) {
        // Hide all buttons
        hideAllOptions()
        // TODO Decide what layout to inflate based on opt
    }

    /**
     * Hide all Options buttons
     * */
    private fun hideAllOptions() {
        opt_team.visibility = View.GONE
        opt_cpu.visibility = View.GONE
        opt_gpu.visibility = View.GONE
        opt_network.visibility = View.GONE
        opt_chassis.visibility = View.GONE
        opt_cooling.visibility = View.GONE
        opt_storage.visibility = View.GONE
        opt_memory.visibility = View.GONE
    }

    /**
     * Show all Options buttons
     * */
    private fun showAllOptions() {
        opt_team.visibility = View.VISIBLE
        opt_cpu.visibility = View.VISIBLE
        opt_gpu.visibility = View.VISIBLE
        opt_network.visibility = View.VISIBLE
        opt_chassis.visibility = View.VISIBLE
        opt_cooling.visibility = View.VISIBLE
        opt_storage.visibility = View.VISIBLE
        opt_memory.visibility = View.VISIBLE
    }

    /**
     * Change app language
     * */
    private fun updateLang(lang: Language) {
        // Choose language locale
        val languageToLoad = when (lang) {
            Language.ENG -> "en"
            Language.ESP -> "es"
        }

        // Load SharedPreferences
        val languagepref = getSharedPreferences("language", Context.MODE_PRIVATE)

        // Check if we really need to update language
        if (languagepref.getString("languageToLoad", "") != languageToLoad) {
            // Change locale
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,
                    baseContext.resources.displayMetrics)

            // Save to SharedPreferences

            val editor = languagepref.edit()
            editor.putString("languageToLoad", languageToLoad)
            editor.commit()

            // Realod activity
            finish()
            startActivity(intent)
        }


    }


    enum class Option { TEAM, CPU, GPU, NETWORK, CHASSIS, COOLING, STORAGE, MEMORY }
    enum class Language { ENG, ESP }
}
