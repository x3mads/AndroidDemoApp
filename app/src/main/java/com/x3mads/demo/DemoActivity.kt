package com.x3mads.demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {

    private val viewModel: DemoViewModel by viewModels()

    private lateinit var btnInit: Button
    private lateinit var btnShowBan: Button
    private lateinit var btnShowItt: Button
    private lateinit var btnShowRew: Button
    private lateinit var spMediator: Spinner
    private lateinit var ctvAutomaticCmp: CheckedTextView
    private lateinit var ctvFakeEeaRegion: CheckedTextView
    private lateinit var btnCmpShowForm: Button
    private lateinit var btnCmpReset: Button
    private lateinit var btnDebuggingSuite: Button
    private lateinit var clMediatorWarning: View
    private lateinit var btnResetApp: View
    private lateinit var clCmp: View
    private lateinit var btnOtherActivity: Button
    private lateinit var container: ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnInit = findViewById(R.id.btn_init)
        btnShowBan = findViewById(R.id.btn_show_ban)
        btnShowItt = findViewById(R.id.btn_show_itt)
        btnShowRew = findViewById(R.id.btn_show_rew)
        spMediator = findViewById(R.id.sp_mediation)
        ctvAutomaticCmp = findViewById(R.id.ctv_cmp_enabled)
        ctvFakeEeaRegion = findViewById(R.id.ctv_fake_region)
        btnCmpShowForm = findViewById(R.id.btn_show_form)
        btnCmpReset = findViewById(R.id.btn_reset_cmp)
        btnDebuggingSuite = findViewById(R.id.btn_debugging_suite)
        clMediatorWarning = findViewById(R.id.cl_mediator_warning)
        btnResetApp = findViewById(R.id.btn_reset_app)
        clCmp = findViewById(R.id.cl_cmp)
        btnOtherActivity = findViewById(R.id.btn_launch_other)
        container = findViewById(R.id.banner_footer)

        ctvFakeEeaRegion.isEnabled = false

        btnInit.setOnClickListener { viewModel.onInitButtonClick(this) }
        btnShowBan.setOnClickListener { viewModel.onShowBanner(container) }
        btnShowItt.setOnClickListener { viewModel.onShowItt(this) }
        btnShowRew.setOnClickListener { viewModel.onShowRew(this) }
        ctvAutomaticCmp.setOnClickListener {
            ctvAutomaticCmp.isChecked = !ctvAutomaticCmp.isChecked
            ctvFakeEeaRegion.isEnabled = ctvAutomaticCmp.isChecked
            clCmp.visibility = if (ctvAutomaticCmp.isChecked) View.VISIBLE else View.GONE
            if (ctvAutomaticCmp.isChecked.not()) {
                ctvFakeEeaRegion.isChecked = false
            }
            viewModel.onCmpEnabledChanged(ctvAutomaticCmp.isChecked)
        }
        ctvFakeEeaRegion.setOnClickListener {
            ctvFakeEeaRegion.isChecked = !ctvFakeEeaRegion.isChecked
            viewModel.onFakeEeaRegionChanged(ctvFakeEeaRegion.isChecked)
        }
        btnCmpShowForm.setOnClickListener { viewModel.onShowCmpForm(this) }
        btnCmpReset.setOnClickListener { viewModel.onResetCmp(this) }
        btnDebuggingSuite.setOnClickListener { viewModel.onDebuggingSuiteButtonClick(this) }
        btnResetApp.setOnClickListener { viewModel.resetApp(this) }
        btnOtherActivity.setOnClickListener { startActivity(Intent(this, OtherActivity::class.java)) }

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
                    if (mediator == "CUSTOM")
                        viewModel.onCustomMediatorSelected(this@DemoActivity) {}
                    else if (mediator.isNotBlank()) {
                        viewModel.onMediatorSelected(mediator)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

        }

        viewModel.subscribeEvents()

        viewModel.isInitialized.observe(this) { isInitialized ->
            spMediator.isEnabled = !isInitialized
            btnInit.isEnabled = !isInitialized
            ctvAutomaticCmp.isEnabled = !isInitialized
            ctvFakeEeaRegion.isEnabled = !isInitialized && ctvAutomaticCmp.isChecked
            val cmpProviderAvailable = viewModel.isCmpProviderAvailable(this)
            btnCmpShowForm.isEnabled = cmpProviderAvailable
            btnCmpReset.isEnabled = cmpProviderAvailable
            clMediatorWarning.visibility = if (isInitialized) View.VISIBLE else View.GONE
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

        viewModel.onMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(container)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribeEvents()
    }

}