package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.TypeConverter
import java.time.LocalTime

class LocalTimeConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromLocalTime(value : LocalTime?) : Int? {
            return value?.toSecondOfDay()
        }

        @TypeConverter
        @JvmStatic
        fun toLocalTime(value : Int) : LocalTime {
            return LocalTime.ofSecondOfDay(value.toLong())
        }
    }
}