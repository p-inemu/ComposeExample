package app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoTask (
    val name: String,
    val done: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)