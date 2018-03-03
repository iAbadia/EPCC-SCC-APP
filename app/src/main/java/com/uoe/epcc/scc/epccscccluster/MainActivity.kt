package com.uoe.epcc.scc.epccscccluster

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_view.*
import kotlinx.android.synthetic.main.info_view.view.*
import java.util.*
import android.animation.ValueAnimator
import android.view.ViewGroup










class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load locale
        loadLocale()

        // Load layout
        setContentView(R.layout.activity_main)

        // Load Lang FAV
        loadFAV()

        // Load server image
        Glide.with(this).load(R.drawable.sample_server).into(img_cluster)

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

        // Set onClick for Detail view
        frg_detail_view.setOnClickListener {hideDetail()}

        // Set onClick for Lang buttons
        fab_item_1.setOnClickListener { updateFAB(it as FloatingActionButton) }
        fab_item_2.setOnClickListener { updateFAB(it as FloatingActionButton) }
        fab_item_3.setOnClickListener { updateFAB(it as FloatingActionButton) }

    }

    override fun onResume() {
        super.onResume()
        // Load locale
        loadLocale()
        // Enter immersive mode
        hideSystemUI()
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

        // Check if language preferences have been initialized
        if (languagepref.getString("languageToLoad", null) == null)
            initializeLangPrefs()

        // Get language preferences
        val lang = languagepref.getString("languageToLoad", null)

        // Set locale
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,
                baseContext.resources.displayMetrics)
    }

    /**
     * Load Lang FAV images correctly
     * */
    private fun loadFAV() {
        // Get language from SharedPreferences
        val languagepref = getSharedPreferences("language", Context.MODE_PRIVATE)

        // Get language preferences
        val lang = languagepref.getString("languageToLoad", null)
        val l1 = languagepref.getString("languageToLoad_1", null)
        val l2 = languagepref.getString("languageToLoad_2", null)
        val l3 = languagepref.getString("languageToLoad_3", null)

        // Update FAB (assign img src and tag correctly)
        fab_menu.menuIconView.setImageResource(localeToResource(lang))
        fab_menu.tag = lang
        fab_item_1.setImageResource(localeToResource(l1))
        fab_item_1.tag = l1
        fab_item_2.setImageResource(localeToResource(l2))
        fab_item_2.tag = l2
        fab_item_3.setImageResource(localeToResource(l3))
        fab_item_3.tag = l3
    }

    /**
     * Initialize language-related SharedPreferences
     * */
    private fun initializeLangPrefs() {
        val languagepref = getSharedPreferences("language", Context.MODE_PRIVATE)
        // Save to SharedPreferences
        val editor = languagepref.edit()
        editor.putString("languageToLoad", Language.EN.lang)
        editor.putString("languageToLoad_1", Language.ES.lang)
        editor.putString("languageToLoad_2", Language.EL.lang)
        editor.putString("languageToLoad_3", Language.IN.lang)
        editor.commit()
    }

    /**
     * Show detail for given Option
     * */
    private fun showDetail(opt: Option) {
        // Hide all buttons
        //hideAllOptions()

        // Blur BG animate
        val va = ValueAnimator.ofInt(1, 12)
        val mDuration = 250 //in millis
        va.duration = mDuration.toLong()
        va.addUpdateListener { animation -> blurLayout.setBlurRadius(animation.animatedValue as Int) }
        va.repeatCount = 0
        va.start()
        // Activate blur layer
        blurLayout.visibility = View.VISIBLE

        // Get screen size
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        Log.i("OnClick", "Screen size is: x = ${size.x} and y = ${size.y}")

        // Set visible but flat (width = 0dp)
        frg_info_view.visibility = View.VISIBLE
        frg_detail_view.visibility = View.VISIBLE


        // Give it 1/3 of screen width
        frg_info_view.layoutParams.width = size.x * 2 / 5 + size.x % 5
        frg_detail_view.layoutParams.width = size.x * 3 / 5
        frg_info_view.requestLayout()
        frg_detail_view.requestLayout()

        // Animate (Slide in)
        frg_info_view.translationX = (size.x * 2 / 5 + size.x % 5).toFloat()
        frg_info_view.animate().translationX(0F)

        frg_detail_view.translationX = -(size.x * 3 / 5).toFloat()
        frg_detail_view.animate().translationX(0F)


        // Load DETAIL Image
        when(opt) {
            Option.TEAM -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.CPU -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.GPU -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.NETWORK -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.CHASSIS -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.COOLING -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.STORAGE -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
            Option.MEMORY -> Glide.with(this).load(R.drawable.detail_team).into(detail_image)
        }


        // Load INFO Title
        frg_info_view.txt_info_title.text = when(opt) {
            Option.TEAM -> resources.getText(R.string.str_the_team)
            Option.CPU -> resources.getString(R.string.str_cpu)
            Option.GPU -> resources.getString(R.string.str_gpu)
            Option.NETWORK -> resources.getString(R.string.str_network)
            Option.CHASSIS -> resources.getString(R.string.str_chassis)
            Option.COOLING -> resources.getString(R.string.str_cooling)
            Option.STORAGE -> resources.getString(R.string.str_storage)
            Option.MEMORY -> resources.getString(R.string.str_memory)
        }

        // Load INFO Description
        frg_info_view.txt_info_description.text = when(opt) {
            Option.TEAM -> resources.getText(R.string.info_team)
            Option.CPU -> resources.getString(R.string.info_cpu)
            Option.GPU -> resources.getString(R.string.info_gpu)
            Option.NETWORK -> resources.getString(R.string.info_network)
            Option.CHASSIS -> resources.getString(R.string.info_chassis)
            Option.COOLING -> resources.getString(R.string.info_cooling)
            Option.STORAGE -> resources.getString(R.string.info_storage)
            Option.MEMORY -> resources.getString(R.string.info_memory)
        }
    }

    /**
     * Show detail for given Option
     * */
    private fun hideDetail() {
        // Get screen size
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        Log.i("OnClick", "Screen size is: x = ${size.x} and y = ${size.y}")

        // Animate slide out
        //frg_info_view.animate().translationX((size.x * 2 / 5 + size.x % 5).toFloat())//.withEndAction { frg_info_view.visibility = View.GONE }
        //frg_detail_view.animate().translationX(-(size.x * 3 / 5).toFloat())//.withEndAction { frg_detail_view.visibility = View.GONE }
        frg_info_view.animate().translationX((size.x * 2 / 5 + size.x % 5).toFloat())
        frg_detail_view.animate().translationX(-(size.x * 3 / 5).toFloat())

        // Disable overlay
        //frg_info_view.visibility = View.GONE
        //frg_detail_view.visibility = View.GONE
        //showAllOptions()

        // Disable blur
        val va = ValueAnimator.ofInt(12, 1)
        val mDuration = 250 //in millis
        va.duration = mDuration.toLong()
        va.addUpdateListener {
            animation -> blurLayout.setBlurRadius(animation.animatedValue as Int)
                         if(animation.animatedValue as Int == 1){ blurLayout.visibility = View.GONE }
        }
        va.repeatCount = 0
        va.start()
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

        fab_menu.visibility = View.GONE
    }

    /**
     * Show all Options buttons
     * */
    private fun showAllOptions() {
        // Enable options button
        opt_team.visibility = View.VISIBLE
        opt_cpu.visibility = View.VISIBLE
        opt_gpu.visibility = View.VISIBLE
        opt_network.visibility = View.VISIBLE
        opt_chassis.visibility = View.VISIBLE
        opt_cooling.visibility = View.VISIBLE
        opt_storage.visibility = View.VISIBLE
        opt_memory.visibility = View.VISIBLE

        // Disable lang buttons
        fab_menu.visibility = View.VISIBLE
    }

    /**
     * Change app language
     * */
    private fun updateLang(lang: String, old: String) {
        // Load SharedPreferences
        val languagepref = getSharedPreferences("language", Context.MODE_PRIVATE)

        // Check if we really need to update language
        if (languagepref.getString("languageToLoad", "") != lang) {
            // Change locale
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,
                    baseContext.resources.displayMetrics)

            // Save to SharedPreferences
            val editor = languagepref.edit()
            editor.putString("languageToLoad", lang)
            // Save the old lang
            for (pref in languagepref.all) {
                if (pref.value == lang) {
                    editor.putString(pref.key, old)
                }
            }
            // Commit changes
            editor.commit()

            // Realod activity
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    /**
     * Update FAB options and language
     * */
    private fun updateFAB(view: FloatingActionButton) {
        // Save current locale
        val saveLocale = fab_menu.tag.toString()

        // Set fab_menu tag and resource
        fab_menu.tag = view.tag.toString()
        fab_menu.menuIconView.setImageResource(localeToResource(view.tag.toString()))

        // Set view tag and resource
        view.tag = saveLocale
        view.setImageResource(localeToResource(saveLocale))

        // Collapse FAB
        fab_menu.close(true)

        // Update language
        updateLang(fab_menu.tag.toString(), view.tag.toString())
    }

    /**
     * Return the resource that matches the received locale code
     * */
    private fun localeToResource(lang: String): Int {
        return when (lang) {
            Language.EN.lang -> R.mipmap.locale_en_56dp
            Language.ES.lang -> R.mipmap.locale_es_56dp
            Language.EL.lang -> R.mipmap.locale_el_56dp
            Language.IN.lang -> R.mipmap.locale_in_56dp
            else -> R.mipmap.locale_en_56dp
        }
    }


    enum class Option { TEAM, CPU, GPU, NETWORK, CHASSIS, COOLING, STORAGE, MEMORY }
    enum class Language(val lang: String) { EN("en"), ES("es"), IN("in"), EL("el") }
}
