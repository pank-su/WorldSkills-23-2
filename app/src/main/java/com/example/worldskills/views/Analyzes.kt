package com.example.worldskills.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.worldskills.viewmodel.AnalyzesViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun Analyzes(navController: NavController, analyzesViewModel: AnalyzesViewModel) {
    // val analyzesViewModel: AnalyzesViewModel = viewModel()
    val scrollState = rememberScrollState()
    val toolbarHeightDp = 250.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.roundToPx().toFloat() }
    // println(with(LocalDensity.current) { toolbarHeightPx.toDp() })
    var toolbarOffsetHeightPx by remember { mutableStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                //println(toolbarOffsetHeightPx)
                coroutineScope.launch {
                    scrollState.scrollTo(toolbarOffsetHeightPx.toInt())
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        AnalyzesCards(
            analyzesViewModel = analyzesViewModel,
            toolbarOffsetHeightPx,
            toolbarHeightDp
        )
        var isActive by remember {
            mutableStateOf(false)
        }
        SearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = isActive,
            onActiveChange = { isActive = !isActive },
            modifier = Modifier.padding(20.dp, 0.dp)
        ) {
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(toolbarHeightDp)
                .padding(0.dp, 66.dp, 0.dp, 0.dp)
                .offset(y = with(LocalDensity.current) {
                    toolbarOffsetHeightPx.toDp()
                })

        ) {


            Text(
                text = "Акции и новости",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 17.sp,
                color = Color(0xFF939396),
                modifier = Modifier.padding(20.dp, 0.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(analyzesViewModel.adds) {
                    androidx.compose.material3.Card(
                        Modifier
                            .size(300.dp, 152.dp)
                            .padding(
                                if (analyzesViewModel.adds.indexOf(it) == 0) 16.dp else 0.dp,
                                0.dp,
                                0.dp,
                                0.dp
                            )
                    ) {
                        val painter = rememberAsyncImagePainter(it.image)
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size((300 / 3 * 1.3).dp, 152.dp)
                                    .align(
                                        Alignment.BottomEnd
                                    ),
                                contentScale = ContentScale.FillHeight
                            )
                            Box(
                                modifier = Modifier
                                    .width((300 / 3 * 1.7).dp)
                                    .height(152.dp)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier.align(Alignment.TopStart),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                                    Text(
                                        text = it.description,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White
                                    )
                                    Text(
                                        text = it.price,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }


                        }
                    }
                }
            }

        }

        if (analyzesViewModel.cart.isNotEmpty()) {

            Button(
                onClick = { navController.navigate("cart") },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(76.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A6FEE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "В корзину")
                    Text(text = "${analyzesViewModel.cart.sumOf { a -> a.price.toInt() }} ₽")
                }

            }
        }

        if (analyzesViewModel.modalBottomSheet) {
            val analyze = analyzesViewModel.filteredAnalyzes[analyzesViewModel.selectedAnalyze]
            ModalBottomSheet(
                onDismissRequest = { analyzesViewModel.modalBottomSheet = false },
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(text = analyze.name, style = MaterialTheme.typography.titleSmall)
                    Text(text = "Описание", style = MaterialTheme.typography.labelSmall)
                    Text(text = analyze.description)
                    Text(
                        text = "Подготовка",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Text(text = analyze.preparation)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 57.dp, 0.dp, 0.dp)
                    ) {
                        item {
                            Text(
                                text = "Результаты через:",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        item {
                            Text(text = "Биоматериал:", style = MaterialTheme.typography.labelSmall)
                        }
                        item {
                            Text(text = analyze.time_result)
                        }
                        item {
                            Text(text = analyze.bio)
                        }
                    }
                    Button(
                        onClick = {
                            analyzesViewModel.cart.add(analyze)
                            analyzesViewModel.modalBottomSheet = false
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .padding(20.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(text = "Добавить за ${analyze.price} ₽")
                    }

                }

            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzesCards(
    analyzesViewModel: AnalyzesViewModel,
    offset: Float,
    toolbarHeight: Dp
) {

    val localDensity = LocalDensity.current

    Column(
        modifier = Modifier
            .padding(
                20.dp,
                toolbarHeight - with(localDensity) { -offset.toDp() } + 25.dp,
                20.dp,
                0.dp)


    ) {
        Text(
            text = "Каталог Анализов",
            style = MaterialTheme.typography.titleSmall,
            fontSize = 17.sp,
            color = Color(0xFF939396),
            modifier = Modifier.padding(7.dp, 10.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(analyzesViewModel.catigories.toList()) {
                FilterChip(
                    onClick = {
                        if (analyzesViewModel.selectedCategory == it) analyzesViewModel.selectedCategory =
                            ""
                        else analyzesViewModel.selectedCategory = it
                    },
                    label = { Text(text = it) },
                    selected = analyzesViewModel.selectedCategory == it,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1A6FEE),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF5F5F9),
                        labelColor = Color(0xFF7E7E9A)
                    ),
                    border = FilterChipDefaults.filterChipBorder(borderWidth = 0.dp),
                    modifier = Modifier.height(48.dp)
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(1000.dp)
                .padding(0.dp, 24.dp, 0.dp, 0.dp)
            // .nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(analyzesViewModel.filteredAnalyzes) { index, it ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 16.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = it.time_result,
                                    style = MaterialTheme.typography.labelSmall
                                )
                                Text(text = it.price + " ₽")
                            }
                            val isContain by remember {
                                derivedStateOf { !analyzesViewModel.cart.contains(it) }
                            }
                            if (isContain)
                                Button(
                                    onClick = {
                                        analyzesViewModel.modalBottomSheet = true
                                        analyzesViewModel.selectedAnalyze = index
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF1A6FEE),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(text = "Добавить", color = Color.White)
                                }
                            else {
                                OutlinedButton(
                                    onClick = {
                                        analyzesViewModel.cart.remove(it)
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(
                                            0xFF1A6FEE
                                        )
                                    ),
                                    border = BorderStroke(1.dp, Color(0xFF1A6FEE))

                                ) {
                                    Text(text = "Убрать")
                                }
                            }
                        }
                    }


                }
            }
        }
    }


}