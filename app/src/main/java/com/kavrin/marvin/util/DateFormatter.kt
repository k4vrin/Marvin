package com.kavrin.marvin.util

import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun dateFormatter(date: String): String {
    val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatPattern = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    return LocalDate.parse(date, parsePattern).format(formatPattern)
}