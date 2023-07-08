package com.example.composeplayground

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SimpleLazyVerticalGridRecomposable() {
    val list = (1..100).map { it.toString() }.toImmutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(items = list, key = { it }) { item ->

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

@Composable
fun SimpleLazyVerticalGridFix1() {
    val list = (1..100).map { it.toString() }.toImmutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(items = list, key = { it }) { item ->

            var isSelected by rememberSaveable { mutableStateOf(false) }

            Card(
                modifier = remember {
                    Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable {
                            Log.d("LazyGrid::Card", "onClickable")
                            isSelected = !isSelected
                        }
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
                        modifier = remember {
                            Modifier
                                .clickable {
                                    Log.d("LazyGrid::IconToggleButton", "onClickable")
                                    isSelected = !isSelected
                                }
                        },
                        tint = if (isSelected) Color.Magenta else Color.LightGray // icon color
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleLazyVerticalGridFix2() {
    val list = (1..100).map { it.toString() }.toImmutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(items = list, key = { it }) { item ->

            var isSelected by rememberSaveable { mutableStateOf(false) }

            SimpleLazyVerticalGridItem(
                item = item,
                isSelected = isSelected,
                onCardClicked = { isSelected = !isSelected },
                onIconClicked = { isSelected = !isSelected },
                onCheckedChanged = { isSelected = it }
            )
        }
    }
}

@Composable
private fun SimpleLazyVerticalGridItem(
    item: String,
    isSelected: Boolean,
    onCardClicked: () -> Unit,
    onIconClicked: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                Log.d("LazyGrid::Card", "onClickable")
                onCardClicked.invoke()
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

        Row {
            IconToggleButton(
                checked = isSelected,
                onCheckedChange = {
                    onCheckedChanged(it)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Item",
                    modifier = Modifier
                        .clickable {
                            Log.d("LazyGrid::IconToggleButton", "onClickable")
                            onIconClicked.invoke()
                        },
                    tint = if (isSelected) Color.Magenta else Color.LightGray // icon color
                )
            }
        }
    }
}


@Composable
fun LazyVerticalGridRecomposable(paddingValues: PaddingValues) {
    val list = (1..100).map { it.toString() }.toImmutableList()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = paddingValues
    ) {
        items(items = list, key = { it }) { item ->

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

@Composable
fun LazyVerticalGridFix(
    paddingValues: PaddingValues,
    isEditable: Boolean
) {
    val list = (1..100).map { it.toString() }.toImmutableList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = paddingValues
    ) {
        items(items = list, key = { it }) { item ->

            var isSelected by rememberSaveable { mutableStateOf(false) }

            LazyVerticalGridItem(
                item = item,
                isSelected = isSelected,
                isEditable = isEditable,
                onCardClicked = { isSelected = !isSelected },
                onIconClicked = { isSelected = !isSelected },
                onCheckedChanged = { isSelected = it }
            )
        }
    }
}

@Composable
fun LazyVerticalGridFix(
    items: ImmutableList<Item>,
    paddingValues: PaddingValues,
    isEditable: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        contentPadding = paddingValues
    ) {
        items(items = items, key = { it.value }) { item ->

            var isSelected by rememberSaveable { mutableStateOf(false) }

            LazyVerticalGridItem(
                item = item.value,
                isSelected = isSelected,
//                isSelected = item.isFavorite,
                isEditable = isEditable,
                onCardClicked = { isSelected = !isSelected },
                onIconClicked = { isSelected = !isSelected },
                onCheckedChanged = { isSelected = it }
            )
        }
    }
}

@Composable
private fun LazyVerticalGridItem(
    item: String,
    isSelected: Boolean,
    isEditable: Boolean,
    onCardClicked: () -> Unit,
    onIconClicked: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit
) {
    Card(
        modifier = remember {
            Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    Log.d("LazyGrid::Card", "onClickable")
                    onCardClicked.invoke()
                }
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

        Row {
            IconToggleButton(
                checked = isSelected,
                onCheckedChange = {
                    onCheckedChanged(it)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Item",
                    modifier = remember {
                        Modifier
                            .clickable {
                                Log.d("LazyGrid::IconToggleButton", "onClickable")
                                onIconClicked.invoke()
                            }
                    },
                    tint = if (isSelected) Color.Magenta else Color.LightGray // icon color
                )
            }

            AnimatedVisibility(visible = isEditable) {
                IconToggleButton(
                    checked = isSelected,
                    onCheckedChange = {
                        // invoke callback
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Check Item",
                        modifier = Modifier
                            .clickable {
                                // invoke callback
                            }
                    )
                }
            }
        }
    }
}
