package com.app.bamboo.data.database.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.bamboo.data.models.LanguageEntity

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(languageEntity: LanguageEntity)

    @Query("UPDATE language_entity SET language_code = :language WHERE id = 0")
    suspend fun updateLanguage(language: String)

    @Query("SELECT * FROM language_entity WHERE id = 0")
    suspend fun getLanguage(): LiveData<LanguageEntity?>
}