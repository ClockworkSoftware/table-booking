package org.clockwork.tablebooking.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.clockwork.tablebooking.dto.establishment.EstablishmentView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.network.reservation.ReservationRepository
import org.clockwork.tablebooking.ui.components.BodyText
import org.clockwork.tablebooking.ui.components.HeaderText
import org.clockwork.tablebooking.ui.components.LogoSmall
import org.clockwork.tablebooking.ui.components.LogoutIcon
import org.clockwork.tablebooking.ui.theme.AppTheme
import org.clockwork.tablebooking.ui.util.LoadableUiState
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    val reservationRepo: ReservationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoadableUiState<out Any>>(LoadableUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun loadReservations() {
        viewModelScope.launch {
            _uiState.update { LoadableUiState.Loading }
            reservationRepo.getAllOwned()
                .onSuccess { result -> _uiState.update { LoadableUiState.Success(result) } }
                .onFailure { result -> _uiState.update { LoadableUiState.Error(result.message ?: "error") } }
        }
    }
}

@Composable
fun HomeScreen(
    uiState: StateFlow<LoadableUiState<out Any>>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderBar()

        when (val state = uiState.collectAsState().value) {
            is LoadableUiState.Loading -> {
                Spacer(Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BodyText("Данные загружаются...")
                    CircularProgressIndicator()
                }
                Spacer(Modifier.weight(1f))
            }

            is LoadableUiState.Error -> {
                Spacer(Modifier.weight(1f))
                BodyText(
                    "Произошла ошибка. Уже разбираемся...",
                    center = true,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.weight(1f))
            }

            is LoadableUiState.Success<*> -> {
                (state.result as? List<ReservationView>)?.let {
                    Box(
                        Modifier.padding(10.dp)
                    ) {
                        ReservationsList(it)
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .padding(10.dp, 5.dp)
        ) {
            LogoSmall(modifier = Modifier.fillMaxWidth(0.15f))

            Box(
                Modifier
                    .width(15.dp)
                    .fillMaxHeight(0.7f)
                    .padding(5.dp, 0.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )

            HeaderText("Бронирования")

            Spacer(Modifier.weight(1f))

            LogoutIcon(modifier = Modifier.fillMaxHeight(0.8f))
        }
    }
}


@Composable
fun ReservationsList(
    reservations: List<ReservationView>
) {
    val cardExpandedStates = rememberSaveable {
        mutableListOf(
            true
        ).also { list ->
            reservations.forEach { list.add(false) }
        }.toMutableStateList()
    }

    Column {
        if (reservations.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ReservationCard(
                    reservations.first(),
                    cardExpandedStates.first()
                ) { cardExpandedStates[0] = it }

                for (index in 1..<reservations.size) {
                    val card = reservations[index]
                    ReservationCard(
                        card,
                        cardExpandedStates[index]
                    ) { cardExpandedStates[index] = it }
                }
            }
        } else {
            Spacer(Modifier.weight(1f))

            HeaderText("Предстоящих бронирований нет!")

            Spacer(Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    AppTheme {
        HomeScreen(
            MutableStateFlow(
                LoadableUiState.Success(
                    listOf(
                        ReservationView(
                            Instant.ofEpochMilli(1779316200000),
                            Instant.ofEpochMilli(1779330200000),
                            false,
                            UserView(
                                1,
                                "name",
                                "surname",
                                UserRole.CLIENT
                            ),
                            PlaceView(
                                1,
                                1,
                                100.0,
                                2,
                                EstablishmentView(
                                    1,
                                    "Москва, ул. Арбатская, д.21",
                                    UserView(
                                        1,
                                        "name",
                                        "surname",
                                        UserRole.CLIENT
                                    )
                                )
                            )
                        ),
                        ReservationView(
                            Instant.ofEpochMilli(1779330200000),
                            Instant.ofEpochMilli(1779330200000),
                            false,
                            UserView(
                                1,
                                "name",
                                "surname",
                                UserRole.CLIENT
                            ),
                            PlaceView(
                                1,
                                1,
                                100.0,
                                2,
                                EstablishmentView(
                                    1,
                                    "Москва, ул. Арбатская, д.21",
                                    UserView(
                                        1,
                                        "name",
                                        "surname",
                                        UserRole.CLIENT
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}