package com.outdoorweather.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.outdoorweather.app.domain.model.Location

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String
) {
    fun toDomain(): Location = Location(
        id = id,
        name = name,
        displayName = displayName,
        latitude = latitude,
        longitude = longitude,
        timezone = timezone
    )

    companion object {
        fun fromDomain(location: Location): LocationEntity = LocationEntity(
            id = location.id,
            name = location.name,
            displayName = location.displayName,
            latitude = location.latitude,
            longitude = location.longitude,
            timezone = location.timezone
        )
    }
}
