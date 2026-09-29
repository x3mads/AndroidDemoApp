package com.x3mads.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {

    private val viewModel: DemoViewModel by viewModels()
    private lateinit var btnInit: Button
    private lateinit var btnTrackPurchase: Button
    private lateinit var btnTrackAppEvent: Button
    private lateinit var btnShowBan: Button
    private lateinit var btnShowItt: Button
    private lateinit var btnShowRew: Button
    private lateinit var btnShowNat: Button
    private lateinit var btnShowApo: Button
    private lateinit var spMediator: Spinner
    private lateinit var ctvAutomaticCmp: CheckedTextView
    private lateinit var ctvFakeEeaRegion: CheckedTextView
    private lateinit var btnCmpShowForm: Button
    private lateinit var btnCmpReset: Button
    private lateinit var btnDebuggingSuite: Button
    private lateinit var clMediatorWarning: View
    private lateinit var btnResetApp: View
    private lateinit var clCmp: View
    private lateinit var bannerFooter: ViewGroup
    private lateinit var nativeFooter: ViewGroup
    private lateinit var etUserId: TextInputEditText
    private lateinit var btnSetUserId: Button
    private lateinit var btnSetInstallDate: Button
    private lateinit var btnSetPurchaseSummary: Button
    private lateinit var btnSetCustomProperties: Button
    private lateinit var btnRemoveCustomProperty: Button
    private lateinit var btnGetUserProperties: Button
    private lateinit var btnClearCustomProperties: Button
    private lateinit var btnClearUserProperties: Button
    private lateinit var tvUserProperties: TextView
    private lateinit var userPropertiesToggle: View
    private lateinit var userPropertiesCard: View
    private lateinit var userPropertiesChevron: ImageView

    private var hasMediatorSelected: Boolean = false
    private var userPropertiesExpanded: Boolean = false

    companion object {
        private const val KEY_USER_PROPERTIES_EXPANDED = "key_user_properties_expanded"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        btnInit = findViewById(R.id.btn_init)
        btnTrackPurchase = findViewById(R.id.btn_track_purchase)
        btnTrackAppEvent = findViewById(R.id.btn_track_app_event)
        btnShowBan = findViewById(R.id.btn_show_ban)
        btnShowItt = findViewById(R.id.btn_show_itt)
        btnShowRew = findViewById(R.id.btn_show_rew)
        btnShowNat = findViewById(R.id.btn_show_nat)
        btnShowApo = findViewById(R.id.btn_show_apo)
        spMediator = findViewById(R.id.sp_mediation)
        ctvAutomaticCmp = findViewById(R.id.ctv_cmp_enabled)
        ctvFakeEeaRegion = findViewById(R.id.ctv_fake_region)
        btnCmpShowForm = findViewById(R.id.btn_show_form)
        btnCmpReset = findViewById(R.id.btn_reset_cmp)
        btnDebuggingSuite = findViewById(R.id.btn_debugging_suite)
        clMediatorWarning = findViewById(R.id.cl_mediator_warning)
        btnResetApp = findViewById(R.id.btn_reset_app)
        clCmp = findViewById(R.id.cl_cmp)
        bannerFooter = findViewById(R.id.banner_footer)
        nativeFooter = findViewById(R.id.native_footer)
        etUserId = findViewById(R.id.et_user_id)
        btnSetUserId = findViewById(R.id.btn_set_user_id)
        btnSetInstallDate = findViewById(R.id.btn_set_install_date)
        btnSetPurchaseSummary = findViewById(R.id.btn_set_purchase_summary)
        btnSetCustomProperties = findViewById(R.id.btn_set_custom_properties)
        btnRemoveCustomProperty = findViewById(R.id.btn_remove_custom_property)
        btnGetUserProperties = findViewById(R.id.btn_get_user_properties)
        btnClearCustomProperties = findViewById(R.id.btn_clear_custom_properties)
        btnClearUserProperties = findViewById(R.id.btn_clear_user_properties)
        tvUserProperties = findViewById(R.id.tv_user_properties)
        userPropertiesToggle = findViewById(R.id.user_properties_toggle)
        userPropertiesCard = findViewById(R.id.user_properties_card)
        userPropertiesChevron = findViewById(R.id.user_properties_chevron)

        userPropertiesToggle.setOnClickListener {
            setUserPropertiesExpanded(!userPropertiesExpanded)
        }
        setUserPropertiesExpanded(
            savedInstanceState?.getBoolean(KEY_USER_PROPERTIES_EXPANDED) ?: false
        )

        ctvFakeEeaRegion.isEnabled = false

        btnInit.isEnabled = false
        btnInit.setOnClickListener { viewModel.onInitButtonClick(this) }
        btnTrackPurchase.setOnClickListener { viewModel.onTrackTestPurchase() }
        btnTrackAppEvent.setOnClickListener { viewModel.onTrackTestAppEvent() }
        btnShowBan.setOnClickListener {
            nativeFooter.removeAllViews()
            viewModel.onShowBanner(this, bannerFooter) }
        btnShowItt.setOnClickListener { viewModel.onShowItt(this) }
        btnShowRew.setOnClickListener { viewModel.onShowRew(this) }
        btnShowNat.setOnClickListener {
            bannerFooter.removeAllViews()
            viewModel.onShowNative(this, nativeFooter) }
        btnShowApo.setOnClickListener { viewModel.onShowApo(this) }
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
        btnSetUserId.setOnClickListener {
            viewModel.onSetUserId(etUserId.text?.toString().orEmpty())
            refreshUserProperties()
        }
        btnSetInstallDate.setOnClickListener {
            viewModel.onSetInstallDate()
            refreshUserProperties()
        }
        btnSetPurchaseSummary.setOnClickListener {
            viewModel.onSetPurchaseSummary()
            refreshUserProperties()
        }
        btnSetCustomProperties.setOnClickListener {
            viewModel.onSetSampleCustomProperties()
            refreshUserProperties()
        }
        btnRemoveCustomProperty.setOnClickListener {
            viewModel.onRemoveSampleCustomProperty()
            refreshUserProperties()
        }
        btnGetUserProperties.setOnClickListener { refreshUserProperties() }
        btnClearCustomProperties.setOnClickListener {
            viewModel.onClearCustomProperties()
            refreshUserProperties()
        }
        btnClearUserProperties.setOnClickListener {
            viewModel.onClearUserProperties()
            refreshUserProperties()
        }

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
                    hasMediatorSelected = mediator.isNotBlank()
                    btnInit.isEnabled = hasMediatorSelected && viewModel.isInitialized.value != true
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
            btnInit.isEnabled = !isInitialized && hasMediatorSelected
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

        viewModel.isNatLoaded.observe(this) { isLoaded ->
            btnShowNat.isEnabled = isLoaded
        }

        viewModel.isBanLoaded.observe(this) { isLoaded ->
            btnShowBan.isEnabled = isLoaded
        }

        viewModel.isApoLoaded.observe(this) { isLoaded ->
            btnShowApo.isEnabled = isLoaded
        }

        viewModel.onMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun refreshUserProperties() {
        tvUserProperties.text = viewModel.onGetUserProperties()
    }

    private fun setUserPropertiesExpanded(expanded: Boolean) {
        if (!expanded && etUserId.hasFocus()) {
            etUserId.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etUserId.windowToken, 0)
        }
        userPropertiesExpanded = expanded
        userPropertiesCard.visibility = if (expanded) View.VISIBLE else View.GONE
        userPropertiesChevron.rotation = if (expanded) 180f else 0f
        userPropertiesToggle.contentDescription = getString(
            if (expanded) R.string.collapse_user_properties
            else R.string.expand_user_properties
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_USER_PROPERTIES_EXPANDED, userPropertiesExpanded)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribeEvents()
    }

}