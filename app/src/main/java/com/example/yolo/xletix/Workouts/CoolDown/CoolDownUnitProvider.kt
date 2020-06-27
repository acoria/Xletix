package com.example.yolo.xletix.Workouts.CoolDown

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails
import com.acoria.unittimer.unittimer_api.units.IUnit
import com.acoria.unittimer.unittimer_api.units.MainUnit
import com.acoria.unittimer.unittimer_api.units.UnitProvider

class CoolDownUnitProvider(reps: Int, names: List<IExerciseDetails>) : UnitProvider(reps, names) {
    override fun initialize() {
        val names = getUnitNames()
        for (rep in 1..getReps()) {
            for (i in names.indices) {
                val exerciseDetails = names[i]
                val trainingUnit: IUnit = MainUnit.Exercise(exerciseDetails.getName() ?: "Exercise", 20, exerciseDetails.isOneSided())
                trainingUnit.setInfoImage(exerciseDetails.getImageResource())
                addUnit(trainingUnit, rep)
                if (!isLastExercise(rep, names.size, i)) {
                    addUnitToStack(MainUnit.Break(length = 10))
                }
            }
        }
    }
}