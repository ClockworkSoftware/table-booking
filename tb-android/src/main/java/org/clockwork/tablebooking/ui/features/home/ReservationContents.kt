package org.clockwork.tablebooking.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.clockwork.tablebooking.dto.establishment.EstablishmentView
import org.clockwork.tablebooking.dto.place.PlaceView
import org.clockwork.tablebooking.dto.reservation.ReservationView
import org.clockwork.tablebooking.dto.user.UserRole
import org.clockwork.tablebooking.dto.user.UserView
import org.clockwork.tablebooking.ui.components.BodyText
import org.clockwork.tablebooking.ui.components.HeaderText
import org.clockwork.tablebooking.ui.components.RowWithExpandButton
import org.clockwork.tablebooking.ui.theme.AppTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    .withZone(ZoneId.systemDefault())
    .withLocale(Locale.getDefault())
val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    .withZone(ZoneId.systemDefault())
    .withLocale(Locale.getDefault())
val dateFormatterExt = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    .withZone(ZoneId.systemDefault())
    .withLocale(Locale.getDefault())

@Composable
fun ReservationCard(
    reservation: ReservationView,
    isCardExpanded: Boolean,
    onCardToggle: (Boolean) -> Unit
) {
    if (isCardExpanded) {
        ExpandedReservationCard(reservation) { onCardToggle(false) }
    } else {
        CompactReservationCard(reservation) { onCardToggle(true) }
    }
}

@Composable
fun CompactReservationCard(
    reservation: ReservationView,
    onExpand: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(15.dp)
    ) {
        RowWithExpandButton(
            modifier = Modifier.padding(5.dp),
            isCrossButton = false,
            onButtonClick = onExpand
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
            ) {
                HeaderText(
                    timeFormatter.format(reservation.start),
                    modifier = Modifier
                        .alignByBaseline()
                )

                BodyText(
                    dateFormatter.format(reservation.start),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCompactReservationCard() {
    AppTheme {
        CompactReservationCard(
            ReservationView(
                Instant.ofEpochMilli(1792535400000),
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
    }
}

@Composable
fun ExpandedReservationCard(
    reservation: ReservationView,
    onFold: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(5.dp)
    ) {
        RowWithExpandButton(
            buttonModifier = Modifier
                .fillMaxHeight(0.6f),
            isCrossButton = true,
            onButtonClick = onFold
        ) {
            Column {
                HeaderText(timeFormatter.format(reservation.start))
                BodyText(dateFormatterExt.format(reservation.start))
            }
        }

        Box(
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .height(2.dp)
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.onPrimary)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LabelledText("Адрес", reservation.place.establishment.address)
            LabelledText("Место", reservation.place.labelNumber.toString(), largeText = true)
        }
    }
}

@Preview
@Composable
fun PreviewExpandedReservationCard() {
    AppTheme {
        ExpandedReservationCard(
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
            )
        )
    }
}

@Composable
fun LabelledText(label: String, text: String, largeText: Boolean = false) {
    Row {
        BodyText(
            label,
            modifier = Modifier
                .align(BiasAlignment.Vertical(0f))
        )

        Spacer(Modifier.width(10.dp))

        if (largeText) {
            HeaderText(text, isBold = true)
        } else {
            BodyText(text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLabelledText() {
    AppTheme {
        Column {
            LabelledText("Адрес", "Москва, ул. Арбатская, д.21")
            LabelledText("Место", "33", true)
        }
    }
}