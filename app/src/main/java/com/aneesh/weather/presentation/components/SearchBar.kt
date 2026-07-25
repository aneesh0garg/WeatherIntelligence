package com.aneesh.weather.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aneesh.weather.R
import com.aneesh.weather.domain.model.CitySuggestion
import com.aneesh.weather.presentation.theme.LocalWeatherPalette
import com.aneesh.weather.presentation.theme.Dimens

@Composable
fun WeatherSearchBar(
    modifier: Modifier = Modifier,
    initialValue: String,
    suggestions: List<CitySuggestion>,
    isSearching: Boolean,
    areSuggestionsVisible: Boolean,
    onQueryChanged: (String) -> Unit,
    onSuggestionSelected: (CitySuggestion) -> Unit,
    onSuggestionsDismissed: () -> Unit,
    onSearch: (String) -> Unit
) {
    val palette = LocalWeatherPalette.current

    var city by remember(initialValue) {
        mutableStateOf(initialValue)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Space16),
            value = city,
            onValueChange = {
                city = it
                onQueryChanged(it)
            },
            singleLine = true,
            shape = RoundedCornerShape(Dimens.Radius16),
            label = {
                Text(stringResource(R.string.search_city))
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

        if (areSuggestionsVisible && (isSearching || suggestions.isNotEmpty())) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.Space16, vertical = Dimens.Space4),
                colors = CardDefaults.cardColors(containerColor = palette.cardContainer)
            ) {

                if (isSearching) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = palette.content,
                        trackColor = palette.mutedContent
                    )
                }

                if (suggestions.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(onClick = onSuggestionsDismissed) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close_suggestions),
                                tint = palette.content
                            )
                        }
                    }
                }
                suggestions.forEach { suggestion ->
                    val pressFeedback = rememberPressFeedback(
                        pressedColor = palette.content.copy(alpha = 0.16f)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(Dimens.Radius12))
                            .background(pressFeedback.containerColor)
                            .clickable(
                                interactionSource = pressFeedback.interactionSource,
                                indication = null
                            ) {
                                city = suggestion.city
                                onSuggestionSelected(suggestion)
                            }
                            .padding(
                                start = Dimens.Space16,
                                end = Dimens.Space16,
                                bottom = Dimens.Space16
                            )
                    ) {
                        Text(text = suggestion.city, color = palette.content)
                        if (suggestion.subtitle.isNotBlank()) {
                            Text(
                                text = suggestion.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = palette.mutedContent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    MaterialTheme {
        WeatherSearchBar(
            initialValue = "London",
            suggestions = emptyList(),
            isSearching = false,
            areSuggestionsVisible = false,
            onQueryChanged = {},
            onSuggestionSelected = {},
            onSuggestionsDismissed = {},
            onSearch = {}
        )
    }
}
