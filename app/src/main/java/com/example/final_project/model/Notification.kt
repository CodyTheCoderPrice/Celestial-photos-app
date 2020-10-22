package com.example.final_project.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("messageType") val messageType: String,
    @SerializedName("messageID") val messageID: String,
    @SerializedName("messageURL") val messageURL: String,
    @SerializedName("messageIssueTime") val messageIssueTime: String,
    @SerializedName("messageBody") val messageBody: String
)