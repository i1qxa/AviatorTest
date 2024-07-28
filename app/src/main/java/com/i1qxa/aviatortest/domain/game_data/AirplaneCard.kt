package com.i1qxa.aviatortest.domain.game_data

import android.widget.ImageView

data class AirplaneCard(
    val airplane:EnumAirplanes?,
    val airplaneView:ImageView,
){
    fun increaseLvl():AirplaneCard?{
        val newLvlAndRes = when(airplane){
            EnumAirplanes.AIRPLANE11 -> EnumAirplanes.AIRPLANE21
            EnumAirplanes.AIRPLANE12 -> EnumAirplanes.AIRPLANE22
            EnumAirplanes.AIRPLANE13 -> EnumAirplanes.AIRPLANE23
            EnumAirplanes.AIRPLANE21 -> EnumAirplanes.AIRPLANE31
            EnumAirplanes.AIRPLANE22 -> EnumAirplanes.AIRPLANE32
            EnumAirplanes.AIRPLANE23 -> EnumAirplanes.AIRPLANE33
            else ->null
        }

        return if (newLvlAndRes!=null){
            this.copy(airplane = newLvlAndRes)
        }else null
    }


    fun updateRes(){
        this.airplane?.lvlAndRes?.let { this.airplaneView.setImageResource(it.resId) }
    }

    fun removeAirplane():AirplaneCard{
        this.airplaneView.setImageDrawable(null)
        return this.copy(airplane = null)
    }

}