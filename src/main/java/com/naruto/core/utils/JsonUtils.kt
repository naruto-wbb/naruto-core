package com.naruto.core.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

object JsonUtils {

    private val sGon = GsonBuilder()
        .registerTypeAdapter(Int::class.javaPrimitiveType,
            JsonDeserializer<Int> { json: JsonElement, _: Type?, _: JsonDeserializationContext? ->
                return@JsonDeserializer try {
                    json.asInt
                } catch (e: NumberFormatException) {
                    0
                }
            }
        )
        .create()
    private var sGonExpose: Gson? = null

    fun <T> bean2Json(t: T): String {
        return sGon.toJson(t)
    }

    fun <T> bean2JsonExpose(t: T): String {
        if (sGonExpose == null) {
            sGonExpose = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
        }
        return sGonExpose!!.toJson(t)
    }

    fun <T> json2Bean(json: String, clazz: Class<T>): T {
        return sGon.fromJson(json, clazz)
    }

    fun <T> json2Bean(json: String, typeToken: Type): T {
        return sGon.fromJson(json, typeToken)
    }

    fun <T> json2Array(json: String, clazz: Class<Array<T>>): List<T> {
        val array: Array<T> = sGon.fromJson(json, clazz)
        return listOf(*array)
    }

    fun <T> json2List(json: String, cls: Class<T>): List<T> {
        val list: MutableList<T> = ArrayList()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(sGon.fromJson(elem, cls))
        }
        return list
    }

    fun <T> json2List(json: String, type: Type): List<T> {
        val list: MutableList<T> = ArrayList()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(sGon.fromJson(elem, type))
        }
        return list
    }

    fun <T> json2ListMaps(json: String): ArrayList<Map<String, T>> {
        return sGon.fromJson(json, object : TypeToken<List<Map<String, T>>>() {}.type)
    }

    fun <T> json2Maps(json: String): Map<String, T> {
        return sGon.fromJson(json, object : TypeToken<Map<String, T>>() {}.type)
    }

    fun <T> map2Json(map: Map<String, T>): String {
        return sGon.toJson(map)
    }

    fun <T> list2Json(list: List<T>): String {
        return sGon.toJson(list)
    }
}