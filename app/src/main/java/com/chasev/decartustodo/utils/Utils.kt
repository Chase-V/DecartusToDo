package com.chasev.decartustodo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateToLocal(date: Date): String =
    SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(date)