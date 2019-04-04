package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromLocalDate(value : LocalDate) : Long {
            return value.toEpochDay()
        }

        @TypeConverter
        @JvmStatic
        fun toLocalDate(value : Long) : LocalDate {
            return LocalDate.ofEpochDay(value)
        }
    }
}