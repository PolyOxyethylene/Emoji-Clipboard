package com.oxyethylene.emojiclipboard.ui.activity.mainactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.oxyethylene.emojiclipboard.R
import com.oxyethylene.emojiclipboard.common.constants.AppRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar (
    title: String
) {

    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier.padding(start = 10.dp)
    )

}

@Composable
fun BottomNavBar (
    navController: NavController
) {

    var navSelected by rememberSaveable { mutableStateOf(AppRoutes.HOME_PAGE) }

    NavigationBar(
        containerColor = Color.Transparent
    ) {
        // 首页
        NavigationBarItem(
            selected = navSelected == AppRoutes.HOME_PAGE,
            onClick = {
                if (navSelected != AppRoutes.HOME_PAGE) {
                    navSelected = AppRoutes.HOME_PAGE
                    navController.popBackStack()
                    navController.navigate(AppRoutes.HOME_PAGE) {
//                    launchSingleTop = true
//                    popUpTo(AppRoutes.HOME_PAGE) {inclusive = true}
                    }
                }

            },
            icon = { Icon(painter = painterResource(id = R.mipmap.nav_ic_home), contentDescription = "", modifier = Modifier.size(24.dp)) },
            label = { Text(text = "首页")}
        )

        // 全部 emoji
        NavigationBarItem(
            selected = navSelected == AppRoutes.EMOJI_PAGE,
            onClick = {
                if (navSelected != AppRoutes.EMOJI_PAGE) {
                    navSelected = AppRoutes.EMOJI_PAGE
                    navController.popBackStack()
                    navController.navigate(AppRoutes.EMOJI_PAGE) {
//                    launchSingleTop = true
//                    popUpTo(AppRoutes.EMOJI_PAGE) {inclusive = true}
                    }
                }

            },
            icon = { Icon(painter = painterResource(id = R.mipmap.nav_ic_emoji), contentDescription = "", modifier = Modifier.size(24.dp)) },
            label = { Text(text = "总览")}
        )

        // 关于
        NavigationBarItem(
            selected = navSelected == AppRoutes.PROFILE_PAGE,
            onClick = {
                if (navSelected != AppRoutes.PROFILE_PAGE) {
                    navSelected = AppRoutes.PROFILE_PAGE
                    navController.popBackStack()
                    navController.navigate(AppRoutes.PROFILE_PAGE) {
//                    launchSingleTop = true
//                    popUpTo(AppRoutes.PROFILE_PAGE) {inclusive = true}
                    }
                }

            },
            icon = { Icon(painter = painterResource(id = R.mipmap.nav_ic_about), contentDescription = "", modifier = Modifier.size(24.dp)) },
            label = { Text(text = "关于")}
        )
    }
}