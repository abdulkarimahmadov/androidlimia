package com.abdulkarimahmadov.androidlimia

import android.app.role.RoleManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abdulkarimahmadov.androidlimia.navigation.LumiaApp
import com.abdulkarimahmadov.androidlimia.theme.LumiaTheme
import com.abdulkarimahmadov.androidlimia.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val roleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestDialerRoleIfNeeded()
        setContent {
            val settings = viewModel.settings.collectAsStateWithLifecycle().value
            LumiaTheme(settings = settings) {
                LumiaApp()
            }
        }
    }

    private fun requestDialerRoleIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager != null && roleManager.isRoleAvailable(RoleManager.ROLE_DIALER) && !roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                roleLauncher.launch(intent)
            }
        }
    }
}
