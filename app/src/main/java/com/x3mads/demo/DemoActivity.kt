package com.x3mads.demo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {

    private val viewModel: DemoViewModel by viewModels()

    private lateinit var btnInit: Button
    private lateinit var btnLoadBan: Button
    private lateinit var btnShowBan: Button
    private lateinit var btnLoadItt: Button
    private lateinit var btnShowItt: Button
    private lateinit var btnLoadRew: Button
    private lateinit var btnShowRew: Button
    private lateinit var spMediator: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        // Initialize buttons
        btnInit = findViewById(R.id.btn_init)
        btnLoadBan = findViewById(R.id.btn_load_ban)
        btnShowBan = findViewById(R.id.btn_show_ban)
        btnLoadItt = findViewById(R.id.btn_load_itt)
        btnShowItt = findViewById(R.id.btn_show_itt)
        btnLoadRew = findViewById(R.id.btn_load_rew)
        btnShowRew = findViewById(R.id.btn_show_rew)
        spMediator = findViewById(R.id.sp_mediation)

        // Set click listeners
        btnInit.setOnClickListener { viewModel.onInitButtonClick(this) }
        btnLoadBan.setOnClickListener { viewModel.onLoadBanner() }
        btnShowBan.setOnClickListener { viewModel.onShowBanner(findViewById(R.id.banner_footer)) }
        btnLoadItt.setOnClickListener { viewModel.onLoadItt() }
        btnShowItt.setOnClickListener { viewModel.onShowItt(this) }
        btnLoadRew.setOnClickListener { viewModel.onLoadRew() }
        btnShowRew.setOnClickListener { viewModel.onShowRew(this) }

        ArrayAdapter.createFromResource(
            this,
            R.array.mediators,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMediator.adapter = adapter
        }

        spMediator.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent.getItemAtPosition(position)?.let {
                    val mediator = it.toString()
                    if (mediator.isNotBlank()) {
                        viewModel.onMediatorSelected(mediator)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

        }
        // Observe the state of load actions
        viewModel.isInitialized.observe(this) { isInitialized ->
            spMediator.isEnabled = !isInitialized
            btnInit.isEnabled = !isInitialized
        }

        viewModel.isIttLoaded.observe(this) { isLoaded ->
            btnLoadItt.isEnabled = isLoaded
            btnShowItt.isEnabled = isLoaded
        }

        viewModel.isRewLoaded.observe(this) { isLoaded ->
            btnLoadRew.isEnabled = isLoaded
            btnShowRew.isEnabled = isLoaded
        }

        viewModel.isBanLoaded.observe(this) { isLoaded ->
            btnLoadBan.isEnabled = isLoaded
            btnShowBan.isEnabled = isLoaded
        }
    }

}