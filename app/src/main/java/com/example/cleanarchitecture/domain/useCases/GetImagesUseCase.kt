package com.example.cleanarchitecture.domain.useCases

import com.example.cleanarchitecture.data.repository.ImageRepoImpl
import com.example.cleanarchitecture.domain.model.DomainModel
import com.example.cleanarchitecture.domain.repository.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetImagesUseCase {

    private val imagesRepository:ImagesRepository by lazy {
        ImageRepoImpl()
    }

    operator fun invoke(q:String) = flow<Result<List<DomainModel>>> {
        val response = imagesRepository.getImages(q)
        emit(response)
    } .catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}