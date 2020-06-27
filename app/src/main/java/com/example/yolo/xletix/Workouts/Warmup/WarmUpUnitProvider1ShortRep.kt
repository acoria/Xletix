package com.example.yolo.xletix.Workouts.Warmup

import com.acoria.unittimer.unittimer_api.units.IExerciseDetails
import com.acoria.unittimer.unittimer_api.units.IUnit
import com.acoria.unittimer.unittimer_api.units.MainUnit
import com.acoria.unittimer.unittimer_api.units.UnitProvider

class WarmUpUnitProvider1ShortRep(reps: Int, names: List<IExerciseDetails>) : UnitProvider(reps, names) {
    override fun initialize() {
        val names = getUnitNames()

        for (rep in 1..getReps()) {
            for (i in names.indices) {
                val exerciseDetails = names[i]
                val trainingUnit: IUnit = MainUnit.Exercise(exerciseDetails.getName() ?: "Exercise", 20, exerciseDetails.isOneSided())
                trainingUnit.setInfoImage(exerciseDetails.getImageResource())
                addUnit(trainingUnit, rep)
                addUnitToStack(MainUnit.Break(length = 10))
            }
        }
    }
}