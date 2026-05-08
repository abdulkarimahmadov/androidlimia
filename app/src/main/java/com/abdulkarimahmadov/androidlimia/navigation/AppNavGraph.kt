package com.abdulkarimahmadov.androidlimia.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdulkarimahmadov.androidlimia.features.contacts.ContactsScreen
import com.abdulkarimahmadov.androidlimia.features.dialer.DialerScreen
import com.abdulkarimahmadov.androidlimia.features.recents.RecentsScreen
import com.abdulkarimahmadov.androidlimia.features.settings.SettingsScreen
import com.abdulkarimahmadov.androidlimia.ui.components.MetroScaffold
import com.abdulkarimahmadov.androidlimia.ui.components.PermissionGate

@Composable
fun LumiaApp() {
    val tabs = AppDestination.entries
    var selected by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(initialPage = selected, pageCount = { tabs.size })

    LaunchedEffect(selected) { pagerState.animateScrollToPage(selected) }
    LaunchedEffect(pagerState.currentPage) { selected = pagerState.currentPage }

    PermissionGate {
        MetroScaffold(
            header = "phone",
            tabs = tabs.map { it.title },
            selected = selected,
            onTabSelected = { selected = it }
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize().padding(top = 8.dp)) { page ->
                AnimatedContent(
                    targetState = page,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "page-transition"
                ) { index ->
                    when (tabs[index]) {
                        AppDestination.DIALER -> DialerScreen()
                        AppDestination.CONTACTS -> ContactsScreen()
                        AppDestination.RECENTS -> RecentsScreen()
                        AppDestination.SETTINGS -> SettingsScreen()
                    }
                }
            }
        }
    }
}
