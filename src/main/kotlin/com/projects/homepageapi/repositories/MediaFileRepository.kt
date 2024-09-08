package com.projects.homepageapi.repositories

import com.projects.homepageapi.models.MediaFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaFileRepository : JpaRepository<MediaFile, Int> {
    @Query("SELECT mf.name FROM MediaFile mf WHERE UPPER(mf.name) LIKE CONCAT('%', :criteria, '%')")
    fun findByCriteria(@Param("criteria") criteria: String): List<String>
}

