package com.makelick.nitrixtest.data.local.converters

import androidx.room.TypeConverter

class DatabaseListConverter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")

    }
}