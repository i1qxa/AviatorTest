package com.i1qxa.aviatortest.ui.game

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.i1qxa.aviatortest.domain.ANIM_DURATION_IN_MILS
import com.i1qxa.aviatortest.domain.START_AMOUNT_AIRPLANES
import com.i1qxa.aviatortest.domain.game_data.AirplaneCard
import com.i1qxa.aviatortest.domain.game_data.AirplaneCoordinate
import com.i1qxa.aviatortest.domain.game_data.EnumAirplanes
import com.i1qxa.aviatortest.domain.game_data.MoveDirections

class GameViewModel : ViewModel() {

    private var topLeftCorner = AirplaneCoordinate(0F, 0F)
    private var bottomRightCorner = AirplaneCoordinate(0F, 0F)
    private var listOfAirplanes = mutableListOf<AirplaneCard>()
    private var listFirstLvl =
        mutableListOf(EnumAirplanes.AIRPLANE11, EnumAirplanes.AIRPLANE12, EnumAirplanes.AIRPLANE13)
    private val _listOfAirplanesLD = MutableLiveData<List<AirplaneCard>>()
    val listOfAirplanesLD: LiveData<List<AirplaneCard>>
        get() = _listOfAirplanesLD

    fun setupListOfAirplanes(listViews: List<ImageView>) {
        topLeftCorner = AirplaneCoordinate(listViews[0].x, listViews[0].y)
        bottomRightCorner = AirplaneCoordinate(listViews[15].x, listViews[15].y)
        var counter = 0
        listViews.shuffled().map {
            if (counter < START_AMOUNT_AIRPLANES) {
                listOfAirplanes.add(
                    AirplaneCard(
                        listFirstLvl.shuffled()[0],
                        it
                    )
                )
                counter++
            } else {
                listOfAirplanes.add(
                    AirplaneCard(
                        null,
                        it
                    )
                )
            }
        }
        _listOfAirplanesLD.value = listOfAirplanes
    }

    private fun addAirplane() {
        var isCardAdded = false
        listOfAirplanes.shuffled().map { airplaneCard ->
            if (!isCardAdded) {
                if (airplaneCard.airplane == null) {
                    val newCard = airplaneCard.copy(airplane = listFirstLvl.shuffled()[0])
                    listOfAirplanes.remove(airplaneCard)
                    listOfAirplanes.add(newCard)
                    newCard.updateRes()
                    isCardAdded = true
                }
            }
        }
    }

    fun moveCard(moveDirection: MoveDirections, cardView: ImageView) {
        if (canMakeMove(moveDirection, cardView)) {
            val airplaneCard = listOfAirplanes.find { it.airplaneView == cardView }
            if (airplaneCard != null) {
                val startCoordinate =
                    AirplaneCoordinate(airplaneCard.airplaneView.x, airplaneCard.airplaneView.y)
                val targetAirplaneCard = findTargetAirplaneTargetCard(moveDirection, airplaneCard)
                if (airplaneCard.airplane == targetAirplaneCard.airplane) {
                    val newAirplane = airplaneCard.increaseLvl()
                    if (newAirplane != null) {
                        airplaneCard.airplaneView.animate().apply {
                            duration = ANIM_DURATION_IN_MILS
                            x(targetAirplaneCard.airplaneView.x)
                            y(targetAirplaneCard.airplaneView.y)
                            withEndAction {
                                listOfAirplanes.remove(airplaneCard)
                                listOfAirplanes.add(newAirplane)
                                newAirplane.updateRes()
                                targetAirplaneCard.airplaneView.x = startCoordinate.x
                                targetAirplaneCard.airplaneView.y = startCoordinate.y
                                listOfAirplanes.remove(targetAirplaneCard)
                                listOfAirplanes.add(targetAirplaneCard.removeAirplane())
                                addAirplane()
                            }
                        }
                    }
                }else {
                    airplaneCard.airplaneView.animate().apply {
                        duration = ANIM_DURATION_IN_MILS
                        x(targetAirplaneCard.airplaneView.x)
                        y(targetAirplaneCard.airplaneView.y)
                    }
                    targetAirplaneCard.airplaneView.animate().apply {
                        duration = ANIM_DURATION_IN_MILS
                        x(startCoordinate.x)
                        y(startCoordinate.y)
                        withEndAction {
                            addAirplane()
                        }
                    }
                }
            }
        }
    }

    private fun canMakeMove(moveDirection: MoveDirections, view: View): Boolean {
        when (moveDirection) {
            MoveDirections.UP -> {
                return if (view.y > topLeftCorner.y) true
                else false
            }

            MoveDirections.DAWN -> {
                return if (view.y < bottomRightCorner.y) true
                else false
            }

            MoveDirections.LEFT -> {
                return if (view.x > topLeftCorner.x) true
                else false
            }

            MoveDirections.RIGHT -> {
                return if (view.x < bottomRightCorner.x) true
                else false
            }
        }
    }

    private fun findTargetAirplaneTargetCard(
        moveDirection: MoveDirections,
        airplaneCard: AirplaneCard
    ): AirplaneCard {
        return when (moveDirection) {
            MoveDirections.UP -> {
                listOfAirplanes.filter { it.airplaneView.x == airplaneCard.airplaneView.x && it.airplaneView.y < airplaneCard.airplaneView.y }
                    .sortedByDescending { it.airplaneView.y }[0]
            }

            MoveDirections.DAWN -> {
                listOfAirplanes.filter { it.airplaneView.x == airplaneCard.airplaneView.x && it.airplaneView.y > airplaneCard.airplaneView.y }
                    .sortedBy { it.airplaneView.y }[0]
            }

            MoveDirections.LEFT -> {
                listOfAirplanes.filter { it.airplaneView.y == airplaneCard.airplaneView.y && it.airplaneView.x < airplaneCard.airplaneView.x }
                    .sortedByDescending { it.airplaneView.x }[0]
            }

            MoveDirections.RIGHT -> {
                listOfAirplanes.filter { it.airplaneView.y == airplaneCard.airplaneView.y && it.airplaneView.x > airplaneCard.airplaneView.x }
                    .sortedBy { it.airplaneView.x }[0]
            }
        }
    }

}