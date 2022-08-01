package com.example.composeproj

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeproj.ui.theme.colorArray
import kotlinx.coroutines.delay

@Composable
fun LoadingBallsAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = Color(0xFFFF5722),
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 40.dp
) {

    val balls = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )
    balls.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 400L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1600
                        0.0f at 0
                        1.0f at 800
                        0.0f at 1600
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val topSlider = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = topSlider) {
        delay(800L)
        topSlider.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1600
                    0.0f at 0 with LinearOutSlowInEasing
                    0.33f at 400 with LinearOutSlowInEasing
                    0.66f at 800 with LinearOutSlowInEasing
                    1f at 1200 with LinearOutSlowInEasing
                    0.0f at 1600 with LinearOutSlowInEasing
                }
            )
        )
    }

    val bottomSlider = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = bottomSlider) {
        bottomSlider.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1600
                    0.0f at 0 with LinearOutSlowInEasing
                    0.33f at 400 with LinearOutSlowInEasing
                    0.66f at 800 with LinearOutSlowInEasing
                    1f at 1200 with LinearOutSlowInEasing
                    0.0f at 1600 with LinearOutSlowInEasing
                }
            )
        )
    }

    // Animation to change color of balls and slider with animation
    // Balls color gets changed when comes in contact with bottom slider
    // Need to improvise it more

    /*val colorAnimOfBall = listOf(
        remember { Animatable(initialValue = colorArray[0]) },
        remember { Animatable(initialValue = colorArray[0]) },
        remember { Animatable(initialValue = colorArray[0]) },
        remember { Animatable(initialValue = colorArray[0]) }
    )
    colorAnimOfBall.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 400L)
            animatable.animateTo(
                targetValue = colorArray[5],
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        // Duration = balls * colors - 100
                        durationMillis = 9600
                        colorArray[0] at 0
                        colorArray[1] at 1600
                        colorArray[2] at 3200
                        colorArray[3] at 4800
                        colorArray[4] at 6400
                        colorArray[5] at 8000
                        colorArray[0] at 9600
                    }
                )
            )
        }
    }

    val infiniteTransitionSlider = rememberInfiniteTransition()
    val colorAnimOfSlider by infiniteTransitionSlider.animateColor(
        initialValue = colorArray[0],
        targetValue = colorArray[5],
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 9600
                colorArray[0] at 0
                colorArray[1] at 1600
                colorArray[2] at 3200
                colorArray[3] at 4800
                colorArray[4] at 6400
                colorArray[5] at 8000
                colorArray[0] at 9600
            }
        )
    )*/

    val circleValues = balls.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val cirDia = with(LocalDensity.current) { circleSize.toPx() }
    val space = with(LocalDensity.current) { spaceBetween.toPx() }

    Column() {
        Box(
            modifier = Modifier
                .width(circleSize)
                .height(1.dp)
                .graphicsLayer {
                    translationX = topSlider.value *
                            (((circleValues.size - 1) * cirDia) + ((circleValues.size - 1) * space))
                }
                .background(color = circleColor)
        )
        Spacer(modifier = Modifier.height(travelDistance))
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
        ) {
            circleValues.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .size(circleSize)
                        .graphicsLayer {
                            translationY = -value * distance
                        }
                        .background(
                            color = circleColor,
                            shape = CircleShape
                        )
                )
            }
        }
        Box(
            modifier = Modifier
                .width(circleSize)
                .height(1.dp)
                .graphicsLayer {
                    translationX = bottomSlider.value *
                            (((circleValues.size - 1) * cirDia) + ((circleValues.size - 1) * space))
                }
                .background(color = circleColor)
        )
    }

}