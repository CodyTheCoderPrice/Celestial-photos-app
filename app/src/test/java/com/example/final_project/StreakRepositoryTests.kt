package com.example.final_project

import com.example.final_project.database.compareDates
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Tests for the helper functions in the StreakRepository class
 */
class StreakRepositoryTests {
    @Test
    fun testCompareDatesFalse() {
        assertFalse(compareDates(Date(), Date(0)))
    }

    @Test
    fun testCompareDatesTrue() {
        assertTrue(compareDates(Date(0), Date()))
    }
}