package com.startupsoft.survey.utils

import java.text.DecimalFormat

class NumberUtils {

    fun formatNumber(number: Double): String = DecimalFormat("######0.00").format(number)
}