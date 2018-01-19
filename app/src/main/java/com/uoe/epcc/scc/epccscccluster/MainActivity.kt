package com.uoe.epcc.scc.epccscccluster

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        img_cluster.setOnClickListener { showAllOptions() }
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

    enum class Option {
        TEAM, CPU, GPU, NETWORK, CHASSIS, COOLING, STORAGE, MEMORY
    }
}
