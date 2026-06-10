package org.clockwork.tablebooking.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeInputField(
    fieldState: StateFlow<String>,
    onUpdate: (Instant) -> Unit
) {
    val pickerStates = object {
        val DATE = "DATE"
        val TIME = "TIME"
        val CLOSED = "CLOSED"
    }

    // Состояния для видимости диалогов
    var pickerState by remember { mutableStateOf(pickerStates.CLOSED) }

    // Временное хранение выбранной даты (в миллисекундах)
    var tempDateMillis by remember { mutableStateOf<Long?>(null) }

    // Состояния для компонентов Material 3
    val datePickerState = rememberDatePickerState()
    val calendar = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
        is24Hour = true // 24-часовой формат времени
    )

    Box {
        OutlinedTextField(
            value = fieldState.collectAsState().value,
            onValueChange = { },
            label = { Text("Дата и время") },
            leadingIcon = {
                Box(
                    modifier = Modifier.fillMaxWidth(0.1f)
                ) {
                    CalendarIcon()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { pickerState = pickerStates.DATE } // Сначала запускаем календарь
        )
    }

    // 1. Диалоговое окно выбора ДАТЫ
    if (pickerState == pickerStates.DATE) {
        DatePickerDialog(
            onDismissRequest = { pickerState = pickerStates.CLOSED },
            confirmButton = {
                TextButton(onClick = {
                    tempDateMillis = datePickerState.selectedDateMillis
                    pickerState = if (tempDateMillis != null)
                        pickerStates.TIME
                    else
                        pickerStates.CLOSED
                }) {
                    Text("Далее")
                }
            },
            dismissButton = {
                TextButton(onClick = { pickerState = pickerStates.CLOSED }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // 2. Диалоговое окно выбора ВРЕМЕНИ
    if (pickerState == pickerStates.TIME) {
        // Используем стандартный AlertDialog для обертки TimePicker
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { pickerState = pickerStates.CLOSED },
            confirmButton = {
                TextButton(onClick = {
                    if (tempDateMillis != null) {
                        // Объединяем дату и время через объект Calendar
                        val result = Calendar.getInstance().apply {
                            timeInMillis = tempDateMillis!!
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                        }

                        onUpdate(result.toInstant())

                        // Форматируем итоговый результат
//                        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
//                        selectedDateTimeText = formatter.format(finalCalendar.time)

                        pickerState = pickerStates.CLOSED
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { pickerState = pickerStates.CLOSED }) {
                    Text("Назад") // Позволяет вернуться или закрыть
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}