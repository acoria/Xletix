package com.example.xletix.Workouts;

import com.example.xletix.R;

public enum TrainingUnitName{
    JUMPING_JACKS(0,"Jumping Jacks", R.drawable.jumping_jacks),
    GRIP_STRENGTH(1,"Grip Strength", R.drawable.grip_strength),
    SHOULDER_MOBILITY(2,"Shoulder Mobility", R.drawable.shoulder_mobility),
    HIGH_KNEES(3,"High Knees", R.drawable.high_knees),
    HORIZONTAL_LUNGE_STRETCH(4,"Horizontal Lunge Stretch", R.drawable.horizontal_lunge_stretch),
    SIDE_TOUCHES(5,"Side Touches", R.drawable.side_touches),
    HIGH_KNEE_STRETCHES(6,"High Knee Stretches", R.drawable.high_knee_stretch),
    VERTICAL_LUNGE_STRETCH(7,"Vertical Lunge Stretch", R.drawable.vertical_lunge_stretch),
    SPRINT_ON_SPOT(8,"Sprint on Spot", R.drawable.sprin_on_spot),
    SQUAT_JUMPS(9,"Squat Jumps", R.drawable.squat_jumps),
    SIDE_PLANK(10,"Side Plank", R.drawable.side_plank),
    X_BURPEES(11,"X-Burpees", R.drawable.x_burpees),
    LUNGE_JUMPS(12,"Lunge Jumps", R.drawable.lunge_jumps),
    KNEE_RAISES(13,"Knee Raises", R.drawable.knee_raises),
    SHOULDER_TAPS(14,"Shoulder Taps", R.drawable.shoulder_taps),
    BICYCLE_CRUNCH(15,"Bicycle Crunch",R.drawable.bicycle_crunch),
    X_SUPERMAN(16,"X-Superman", R.drawable.x_superman),
    WALL_SIT(17,"Wall Sit",R.drawable.wall_sit),
    PUSH_UPS(18, "Push Ups", R.drawable.push_ups),
    SPLIT_SQUAT(19,"Split Squat", R.drawable.split_squat),
    HIGH_PLANK(20,"High Plank", R.drawable.high_plank);

    private int value;
    private String name;
    private int imageResource;

    TrainingUnitName(int value, String name, int imageResource){
        this.value = value;
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName(){
        return name;
    }

    public int getImageResource(){
        return imageResource;
    }
}
