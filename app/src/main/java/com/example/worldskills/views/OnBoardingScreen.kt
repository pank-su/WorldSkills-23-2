package com.example.worldskills.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.worldskills.R
import com.example.worldskills.model.Page
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.sign


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyHorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageCount: Int = pagerState.pageCount,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
    borderColor: Color
) {

    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)
                .border(1.dp, borderColor, CircleShape)

            repeat(pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffset
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(
                            0f,
                            (pageCount - 1)
                                .coerceAtLeast(0)
                                .toFloat()
                        )

                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .then(
                    if (pageCount > 0) Modifier.background(
                        color = activeColor,
                        shape = indicatorShape,
                    )
                    else Modifier
                )
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavHostController, pages: List<Page> = listOf(
        Page("Анализы", "Экспресс сбор и получение проб", R.drawable.onboardone),
        Page("Уведомления", "Вы быстро узнаете о результатах", R.drawable.onboardtwo),
        Page(
            "Мониторинг", "Наши врачи всегда наблюдают \n" +
                    "за вашими показателями здоровья", R.drawable.onboardthree
        )
    )
) {

    val counter = rememberPagerState()
    val currentPage by remember {
        derivedStateOf { counter.currentPage }
    }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = {
                if (currentPage != pages.size - 1)
                    coroutineScope.launch {
                        counter.animateScrollToPage(pages.size - 1)
                    }
                else navController.navigate("auth"){
                    popUpTo("OnBoardingScreen"){
                        inclusive = true
                    }
                }
            }, modifier = Modifier.semantics { contentDescription = "skip_button" }) {
                Text(if (currentPage != pages.size - 1) "Пропустить" else "Завершить")
            }
            Image(
                painter = painterResource(id = R.drawable.shape),
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .size(160.dp)
            )
        }


        HorizontalPager(
            count = pages.size,
            state = counter,
            modifier = Modifier.padding(0.dp, 100.dp).semantics { contentDescription="pager" }
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    alignment = Alignment.CenterVertically
                ), horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pages[it].name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics { contentDescription="title" }
                )
                Text(
                    text = pages[it].description,
                    modifier = Modifier.padding(19.dp).semantics { contentDescription="description" },
                    style = MaterialTheme.typography.displaySmall
                )


            }
        }


    }
    Box(modifier = Modifier.fillMaxSize()) {
        MyHorizontalPagerIndicator(
            pagerState = counter,
            modifier = Modifier.align(Alignment.Center), //.border(1.dp, Color(0xFF57A9FF), CircleShape),
            indicatorHeight = 12.dp,
            indicatorWidth = 12.dp,
            inactiveColor = Color.White,
            activeColor = Color(0xFF57A9FF),
            borderColor = Color(0xFF57A9FF)

        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        val imageId by remember {
            derivedStateOf { pages[counter.currentPage].imageId }
        }
        AnimatedContent(targetState = imageId) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }

}