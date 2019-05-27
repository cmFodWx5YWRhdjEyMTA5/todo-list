package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.persistence.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromLocalDate(value : LocalDate?) : Long? {
            return value?.toEpochDay()
        }

        @TypeConverter
        @JvmStatic
        fun toLocalDate(value : Long?) : LocalDate? = when(value) {
            null -> null
            else -> LocalDate.ofEpochDay(value)
        }
    }
}