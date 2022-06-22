package com.kavrin.marvin.data.local

import androidx.room.TypeConverter

class DatabaseConverter {

	private val separator = ","

	@TypeConverter
	fun convertIntListToString(list: List<Int>): String {
		val stringBuilder = StringBuilder()

		for (item in list)
			stringBuilder.append(item).append(separator)

		if (stringBuilder.isNotEmpty())
			stringBuilder.setLength(stringBuilder.length - separator.length)
		return stringBuilder.toString()
	}

	@TypeConverter
	fun convertStringToIntList(string: String): List<Int> {
		return string.split(separator).map {
			it.toInt()
		}
	}

	@TypeConverter
	fun convertListToString(list: List<String>): String {
		val stringBuilder = StringBuilder()

		for (item in list)
			stringBuilder.append(item).append(separator)

		stringBuilder.setLength(stringBuilder.length - separator.length)
		return stringBuilder.toString()
	}

	@TypeConverter
	fun convertStringToList(string: String): List<String> {
		return string.split(separator)
	}
}