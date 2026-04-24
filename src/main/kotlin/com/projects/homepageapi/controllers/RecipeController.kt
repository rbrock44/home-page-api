package com.projects.homepageapi.controllers

import com.projects.homepageapi.*
import com.projects.homepageapi.models.PendingRecipe
import com.projects.homepageapi.repositories.PendingRecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("recipe/pending")
@CrossOrigin(
    origins = [
        angularOrigin,
        wildcardOrigin,
        addFamilyRecipeOrigin,
        homePageOrigin,
        "https://github.com/"
    ],
    methods = [
        org.springframework.web.bind.annotation.RequestMethod.GET, 
        org.springframework.web.bind.annotation.RequestMethod.POST, 
        org.springframework.web.bind.annotation.RequestMethod.DELETE
    ],
    allowedHeaders = ["*"],
    allowCredentials = "true",
    maxAge = maxAge
)
class RecipeController(
    @Autowired private val pendingRecipeRepository: PendingRecipeRepository
) {
    
    @PostMapping("")
    fun createPendingRecipe(@RequestBody jsonPayload: Map<String, Any>): ResponseEntity<PendingRecipe> {
        val jsonString = jsonPayload.toString()
        val pendingRecipe = PendingRecipe(jsonPayload = jsonString)
        val saved = pendingRecipeRepository.save(pendingRecipe)
        return ResponseEntity(saved, HttpStatus.CREATED)
    }
    
    @DeleteMapping("/{id}")
    fun deletePendingRecipe(@PathVariable id: Long): ResponseEntity<Void> {
        if (pendingRecipeRepository.existsById(id)) {
            pendingRecipeRepository.deleteById(id)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
    
    @GetMapping("")
    fun getAllPendingRecipes(): ResponseEntity<List<PendingRecipe>> {
        val recipes = pendingRecipeRepository.findAll()
        return ResponseEntity(recipes, HttpStatus.OK)
    }
}
