package com.example.final_project

import com.example.final_project.networking.helper.checkValidDate
import com.example.final_project.networking.helper.checkValidLatLong
import org.junit.Test

import org.junit.Assert.*

/**
 * Tests the EARTH Service helper functions for correctly
 * thrown exceptions
 */
class EarthUnitTests {
    @Test(expected = Exception::class)
    fun testEmptyStrings() {
        checkValidLatLong("", "")
    }

    @Test(expected = Exception::class)
    fun testOutOfBoundStrings() {
        checkValidLatLong("100", "190")
    }

    @Test(expected = Exception::class)
    fun testEmptyDate() {
        checkValidDate("")
    }
}