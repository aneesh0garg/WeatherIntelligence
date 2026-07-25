package com.aneesh.weather.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aneesh.weather.presentation.theme.LocalWeatherPalette

@Composable
fun WeatherSearchBar(
    modifier: Modifier = Modifier,
    initialValue: String,
    onSearch: (String) -> Unit
) {
    val palette = LocalWeatherPalette.current

    var city by remember(initialValue) {
        mutableStateOf(initialValue)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = city,
        onValueChange = {
            city = it
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        label = {
            Text("Search city")
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                null,
                tint = palette.content
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (city.isNotBlank()) {
                        onSearch(city.trim())
                    }
                }
            ) {
                Icon(
                    Icons.Default.Search,
                    "Search",
                    tint = palette.content
                )
            }
        },

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                if (city.isNotBlank()) {
                    onSearch(city.trim())
                }
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = palette.content,
            unfocusedTextColor = palette.content,
            focusedContainerColor = palette.cardContainer,
            unfocusedContainerColor = palette.cardContainer,
            focusedLabelColor = palette.content,
            unfocusedLabelColor = palette.mutedContent,
            focusedLeadingIconColor = palette.content,
            unfocusedLeadingIconColor = palette.mutedContent,
            focusedTrailingIconColor = palette.content,
            unfocusedTrailingIconColor = palette.mutedContent,
            focusedIndicatorColor = palette.content,
            unfocusedIndicatorColor = palette.mutedContent,
            cursorColor = palette.content
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    MaterialTheme {
        WeatherSearchBar(
            initialValue = "London",
            onSearch = {}
        )
    }
}
