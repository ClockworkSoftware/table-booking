package org.clockwork.tablebooking.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.clockwork.tablebooking.dto.establishment.EstablishmentView
import org.clockwork.tablebooking.network.establishment.EstablishmentRepository
import org.clockwork.tablebooking.network.reservation.ReservationRepository
import org.clockwork.tablebooking.ui.components.BodyText
import org.clockwork.tablebooking.ui.components.BuildingIcon
import org.clockwork.tablebooking.ui.components.DateTimeInputField
import org.clockwork.tablebooking.ui.components.HeaderText
import org.clockwork.tablebooking.ui.components.RoundedSquareButton
import org.clockwork.tablebooking.ui.theme.AppTheme
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ReservationCreationViewModel @Inject constructor(
    val establishmentRepository: EstablishmentRepository,
    val reservationRepository: ReservationRepository
) : ViewModel() {
    private lateinit var establishments: List<EstablishmentView>

    val addressFieldState = TextFieldState()

    private val _dateFieldState = MutableStateFlow("")
    val dateFieldState = _dateFieldState.asStateFlow()

    init {
        viewModelScope.launch {
            establishments = establishmentRepository.getAll()
        }
    }

    fun filterAddresses() {

    }

    fun create() {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationCreationContent(
    addressSearchState: TextFieldState,
    refreshEstablishmentResults: (String) -> Unit,
    addressSearchResults: List<String>,

    dateFieldState: StateFlow<String>,
    onDateChanged: (Instant) -> Unit
) {
    Column {
        var searchBarExpanded by rememberSaveable { mutableStateOf(false) }

        DockedSearchBar(
            shape = RoundedCornerShape(5.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            inputField = {
                OutlinedTextField(
                    label = { Text("Адрес заведения") },
                    state = addressSearchState,
                    leadingIcon = {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.1f)
                        ) {
                            BuildingIcon()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

//                SearchBarDefaults.InputField(
//                    leadingIcon = {
//                        Box(
//                            modifier = Modifier.fillMaxWidth(0.1f)
//                        ) {
//                            BuildingIcon()
//                        }
//                    },
//                    state = establishmentSearchState,
//                    onSearch = {
//                        refreshEstablishmentResults()
//                        searchBarExpanded = false
//                    },
//                    expanded = searchBarExpanded,
//                    onExpandedChange = { searchBarExpanded = it },
//                    placeholder = { BodyText("Адрес заведения") }
//                )
            },
            expanded = searchBarExpanded,
            onExpandedChange = { searchBarExpanded = it }
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                addressSearchResults.forEach { result ->
                    ListItem(
                        headlineContent = { BodyText(result) },
                        modifier = Modifier
                            .clickable {
                                addressSearchState.edit {
                                    replace(0, length, result)
                                }
                                searchBarExpanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }

        LaunchedEffect(addressSearchState) {
            snapshotFlow { addressSearchState.text.toString() }
                .distinctUntilChanged()
                .collect { refreshEstablishmentResults(it) }
        }

        DateTimeInputField(
            dateFieldState,
            onDateChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReservationCreationContent() {
    AppTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderBar()

            Box(Modifier.padding(5.dp)) {
                val dateState = MutableStateFlow("")
                ReservationCreationContent(
                    TextFieldState(),
                    {},
                    listOf(
                        "ул. свободы"
                    ),
                    dateState.asStateFlow()
                ) { newDateTime ->
                    dateState.update { newDateTime.toString() }
                }
            }

            Spacer(Modifier.weight(1f))

            RoundedSquareButton(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                roundTopHalfOnly = true
            ) {
                HeaderText("Забронировать")
            }
        }
    }
}