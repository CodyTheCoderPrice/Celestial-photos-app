package com.example.final_project

import com.example.final_project.networking.EarthService
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

    @Test(expected = Exception::class)
    fun testInvalidFormatDate1() {
        checkValidDate("10-11-2020")
    }

    @Test(expected = Exception::class)
    fun testInvalidFormatDate2() {
        checkValidDate("10/20/2020")
    }

    @Test
    fun testSetUrl() {
        assertEquals(EarthService.instance.setUrl(), "https://api.nasa.gov/planetary/earth/assets?lat=0.0&lon=0.0&date=2020-10-31&api_key=DEMO_KEY")
    }
}