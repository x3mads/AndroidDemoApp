package com.x3mads.demo

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

class DemoActivity : AppCompatActivity() {

    private val viewModel: DemoViewModel by viewModels()

    private lateinit var btnInit: Button
    private lateinit var btnLoadBan: Button
    private lateinit var btnShowBan: Button
    private lateinit var btnLoadItt: Button
    private lateinit var btnShowItt: Button
    private lateinit var btnLoadRew: Button
    private lateinit var btnShowRew: Button
    private lateinit var etPlacementId: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        TopActivityLifecycleCallbacks.registerWith(this)
        // Initialize buttons
        btnInit = findViewById(R.id.btn_init)
        btnLoadBan = findViewById(R.id.btn_load_ban)
        btnShowBan = findViewById(R.id.btn_show_ban)
        btnLoadItt = findViewById(R.id.btn_load_itt)
        btnShowItt = findViewById(R.id.btn_show_itt)
        btnLoadRew = findViewById(R.id.btn_load_rew)
        btnShowRew = findViewById(R.id.btn_show_rew)
        etPlacementId = findViewById(R.id.et_placement_id)

        // Set click listeners
        btnInit.setOnClickListener { viewModel.onInitButtonClick(this) }
        btnLoadBan.setOnClickListener { viewModel.onLoadBanner() }
        btnShowBan.setOnClickListener { viewModel.onShowBanner(findViewById(R.id.banner_footer)) }
        btnLoadItt.setOnClickListener { viewModel.onLoadItt() }
        btnShowItt.setOnClickListener { viewModel.onShowItt(this) }
        btnLoadRew.setOnClickListener { viewModel.onLoadRew() }
        btnShowRew.setOnClickListener { viewModel.onShowRew(this) }
        etPlacementId.doOnTextChanged { text, start, before, count ->
            viewModel.setPlacementId(text.toString())
        }

        // Observe the state of load actions
        viewModel.isInitialized.observe(this) { isInitialized ->
            btnInit.isEnabled = !isInitialized
            btnLoadItt.isEnabled = isInitialized
            btnLoadRew.isEnabled = isInitialized
            btnLoadBan.isEnabled = isInitialized
        }

        viewModel.isIttLoaded.observe(this) { isLoaded ->
            btnShowItt.isEnabled = isLoaded
        }

        viewModel.isRewLoaded.observe(this) { isLoaded ->
            btnShowRew.isEnabled = isLoaded
        }

        viewModel.isBanLoaded.observe(this) { isLoaded ->
            btnShowBan.isEnabled = isLoaded
        }
    }

    fun getTopActivity(): Activity? {
        return TopActivityLifecycleCallbacks.topActivity
    }

}