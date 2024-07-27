package com.i1qxa.aviatortest.domain

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

const val AVIATOR_DS = "aviator_ds"
const val MAIN_BACKGROUND = "main_background"
const val IS_VIBRATION_ON = "is_vibration_on"
const val HIGH_SCORE = "high_score"

val backgroundIdKey = intPreferencesKey(MAIN_BACKGROUND)
val highScoreKey = intPreferencesKey(HIGH_SCORE)
val isVibrationOnKey = booleanPreferencesKey(IS_VIBRATION_ON)