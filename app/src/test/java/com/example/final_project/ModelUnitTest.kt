package com.example.final_project

import com.example.final_project.model.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class ModelUnitTest {
    @Test
    fun testStreak() {
        val id = UUID.randomUUID()
        val date = Date()
        val streak = Streak(id, date, 1)

        assertEquals(id, streak.id)
        assertEquals(date, streak.date)
    }

    @Test
    fun testManipulatingStreak() {
        val streak = Streak(UUID.randomUUID(), Date(), 1)
        streak.streak++
        assertEquals(2, streak.streak)
    }

    @Test
    fun testNotification() {
        val notification = Notification("a", "b", "c", "d", "e")
        assertEquals("c", notification.messageURL)
    }

    @Test
    fun testApod() {
        val apod = ApodObject(UUID.randomUUID(), Date().toString(), "a", "b", "c", "d", "e")
        assertEquals("a", apod.explanation)
    }

    @Test
    fun testEarth() {
        val earth = Earth("a", "b", EarthResource("c", "d"), "e", "f")
        assertEquals("a", earth.date)
    }

    @Test
    fun testEarthResource() {
        val earthResource = EarthResource("a", "b")
        assertEquals("a", earthResource.dataset)
    }
}