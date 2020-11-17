package com.example.final_project.networking.helper

/**
 * Checks if date is valid
 */
fun checkValidDate(date: String) {
    if (date.isNullOrEmpty()) {
        throw Exception("Date must be filled in")
    }
}

/**
 * Checks if latitude and longitude are valid numbers
 */
fun checkValidLatLong(lat: String, long: String) {
    if (lat.isNullOrEmpty() || long.isNullOrEmpty()) {
        throw Exception("Latitude or Longitude must be filled in")
    }

    val lat = lat.toFloat()
    val long = long.toFloat()
    if (lat < -90 || lat > 90) {
        throw Exception("Latitude is not valid")
    }
    if (long < -180 || long > 180) {
        throw Exception("Longitude is not valid")
    }
}