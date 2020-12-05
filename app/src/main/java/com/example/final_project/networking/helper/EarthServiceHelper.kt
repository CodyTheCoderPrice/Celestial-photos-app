package com.example.final_project.networking.helper

/**
 * Checks if date is valid
 */
fun checkValidDate(date: String) {
    if (date.isNullOrEmpty()) {
        throw Exception("Date must be filled in")
    }

    // Make sure date conforms to a DD/MM/YYYY format
    val regex = "^(0?[1-9]|[12][0-9]|3[01])[\\/](0?[1-9]|1[012])[\\/]\\d{4}\$".toRegex()
    if (!regex.matches(date)) {
        throw Exception("Date does not match DD/MM/YYYY format")
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