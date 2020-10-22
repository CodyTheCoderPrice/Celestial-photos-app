package com.example.final_project

class ApodObject(
    val date: String,
    val explanation: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
) {
    override fun toString(): String {
        return "[date: ${this.date}, explanation: ${this.explanation}, media_type: ${this.media_type}, service_version: ${this.service_version}, title: ${this.title}, url: ${this.url}]"
    }
}