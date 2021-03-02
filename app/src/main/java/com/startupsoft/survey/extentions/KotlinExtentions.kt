package com.startupsoft.survey.extentions

fun String.toDoubleOrZero(): Double = toDoubleOrNull() ?: 0.0

fun String.insert(content: String, position: Int): String {
    return substring(0, position) + content + substring(position, length);
}