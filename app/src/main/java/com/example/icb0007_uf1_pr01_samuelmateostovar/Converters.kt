package com.example.icb0007_uf1_pr01_samuelmateostovar

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDimension(dimension: Dimension?): String {
        return gson.toJson(dimension)
    }

    @TypeConverter
    fun toDimension(dimensionJson: String): Dimension? {
        val type = object : TypeToken<Dimension>() {}.type
        return gson.fromJson(dimensionJson, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}