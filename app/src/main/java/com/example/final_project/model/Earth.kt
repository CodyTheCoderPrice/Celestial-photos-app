package com.example.final_project.model

import com.google.gson.annotations.SerializedName

data class Earth(
    @SerializedName("date") val date: String,
    @SerializedName("id") val id: String,
    @SerializedName("resource") val resource: EarthResource,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("url") val url: String
)

data class EarthResource(
    @SerializedName("dataset") val dataset: String,
    @SerializedName("planet") val planet: String
)

data class EarthError(
    @SerializedName("msg") val message: String,
    @SerializedName("service_version") val serviceVersion: String
)