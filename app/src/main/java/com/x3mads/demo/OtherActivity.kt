package com.x3mads.demo

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class OtherActivity : AppCompatActivity() {

    private val viewModel: DemoViewModel by viewModels()


    private lateinit var btnShowBan: Button
    private lateinit var btnShowItt: Button
    private lateinit var btnShowRew: Button


    private lateinit var btnDebuggingSuite: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)


        btnShowBan = findViewById(R.id.btn_show_ban)
        btnShowItt = findViewById(R.id.btn_show_itt)
        btnShowRew = findViewById(R.id.btn_show_rew)


        btnDebuggingSuite = findViewById(R.id.btn_debugging_suite)


        btnShowBan.setOnClickListener { viewModel.onShowBanner(findViewById(R.id.banner_footer)) }
        btnShowItt.setOnClickListener { viewModel.onShowItt(this) }

        btnDebuggingSuite.setOnClickListener { viewModel.onDebuggingSuiteButtonClick(this) }

        viewModel.subscribeEvents()

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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribeEvents()
    }

}