package com.example.composeplayground.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeplayground.Constants
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun SimpleLazyRowRecomposableScreen() {
    val list = (1..Constants.LIST_SIZE).map { it.toString() }

    ComposePlaygroundTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(
                left = 10.dp,
                top = 10.dp,
                right = 10.dp,
                bottom = 10.dp
            )
        ) { paddingValues ->
            LazyRow(
                contentPadding = paddingValues
            ) {
                items(list) { item ->

                    var isSelected by rememberSaveable { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable {
                                Log.d("LazyGrid::Card", "onClickable")
                                isSelected = !isSelected
                            }
                    ) {
                        Text(
                            text = item,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(32.dp)
                        )

                        IconToggleButton(
                            checked = isSelected,
                            onCheckedChange = {
                                isSelected = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite Item",
                                modifier = Modifier
                                    .clickable {
                                        Log.d("LazyGrid::IconToggleButton", "onClickable")
                                        isSelected = !isSelected
                                    },
                                tint = if (isSelected) Color.Magenta else Color.LightGray // icon color
                            )
                        }
                    }
                }
            }
        }
    }
}
