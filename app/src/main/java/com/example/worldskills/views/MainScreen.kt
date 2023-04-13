package com.example.worldskills.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.worldskills.R
import com.example.worldskills.model.Screen
import com.example.worldskills.ui.theme.WorldSkillsTheme
import com.example.worldskills.viewmodel.AnalyzesViewModel

@Composable
fun MainScreen(analyzesViewModel: AnalyzesViewModel, navController: NavHostController) {

    val items = listOf(Screen("Анализы", R.drawable.analyses) {
        Analyzes(
            navController,
            analyzesViewModel
        )
    },
        Screen("Результаты", R.drawable.results) { Text(text = "Результаты") },
        Screen("Поддержка", R.drawable.support) { Text(text = "Поддержка") },
        Screen("Профиль", R.drawable.user) { Profile() })
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        NavigationBar(tonalElevation = 0.dp) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                NavigationBarItem(icon = {
                    Icon(
                        painterResource(id = screen.drawableRes), contentDescription = null
                    )
                },
                    label = { Text(screen.route) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color(0xFF939396),
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color(0xFF939396)
                    ),
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    })
            }
        }
    }) { innerPadding ->
        NavHost(
            navController, startDestination = items[0].route, Modifier.padding(innerPadding)
        ) {
            for (el in items) {
                composable(el.route) { el.composable() }
            }

        }
    }


}


@Preview
@Composable
fun TestBottomNav() {
    WorldSkillsTheme {
        // MainScreen(analyzesViewModel, navController)
    }

}