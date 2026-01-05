package com.example.cleanarchitecture.data.repository

import com.example.cleanarchitecture.data.remote.ApiService
import com.example.cleanarchitecture.data.remote.RetrofitInstance
import com.example.cleanarchitecture.domain.model.DomainModel
import com.example.cleanarchitecture.domain.repository.ImagesRepository

class ImageRepoImpl:ImagesRepository {

    val apiService  by lazy { RetrofitInstance.getApiService()}
    override suspend fun getImages(q: String): Result<List<DomainModel>> {


        return try {

            val response = apiService.getImage(query = q)
            val listOgDomainModel = response.hits.map {
                DomainModel(imageUrl = it.largeImageURL)
            }
            Result.success(listOgDomainModel)

        } catch (e:Exception){

            Result.failure(e)
        }
    }
}