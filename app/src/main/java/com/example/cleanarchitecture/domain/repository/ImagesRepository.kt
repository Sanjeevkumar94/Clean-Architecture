package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.domain.model.DomainModel

interface ImagesRepository {
    suspend fun getImages(q:String):Result<List<DomainModel>>
}