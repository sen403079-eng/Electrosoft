package com.electrosoft.documentapp.data.repository

import com.electrosoft.documentapp.data.api.DocumentApi
import com.electrosoft.documentapp.data.db.dao.DocumentDao
import com.electrosoft.documentapp.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class DocumentRepository @Inject constructor(
    private val api: DocumentApi,
    private val documentDao: DocumentDao
) {

    suspend fun uploadDocument(
        file: MultipartBody.Part,
        description: String? = null,
        tags: List<String>? = null
    ): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val response = api.uploadDocument(file, description, tags)
            if (response.success && response.data != null) {
                documentDao.insertDocument(mapToEntity(response.data))
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Upload failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDocuments(
        status: String = "active",
        page: Int = 1,
        limit: Int = 20
    ): Result<DocumentListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getDocuments(status, page, limit)
            if (response.success) {
                documentDao.insertDocuments(response.data.map { mapToEntity(it) })
                Result.success(response)
            } else {
                Result.failure(Exception(response.message ?: "Failed to fetch documents"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDocument(id: String): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val response = api.getDocument(id)
            if (response.success && response.data != null) {
                documentDao.insertDocument(mapToEntity(response.data))
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Failed to fetch document"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteDocument(id: String): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val response = api.deleteDocument(id)
            if (response.success) {
                documentDao.deleteDocumentById(id)
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Failed to delete document"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun shareDocument(id: String, userId: String, permission: String): Result<Document> =
        withContext(Dispatchers.IO) {
            try {
                val request = ShareRequest(userId, permission)
                val response = api.shareDocument(id, request)
                if (response.success && response.data != null) {
                    documentDao.insertDocument(mapToEntity(response.data))
                    Result.success(response.data)
                } else {
                    Result.failure(Exception(response.message ?: "Failed to share document"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun addToFavorites(id: String): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val response = api.addToFavorites(id)
            if (response.success && response.data != null) {
                documentDao.updateFavorite(id, true)
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Failed to add to favorites"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeFromFavorites(id: String): Result<Document> = withContext(Dispatchers.IO) {
        try {
            val response = api.removeFromFavorites(id)
            if (response.success && response.data != null) {
                documentDao.updateFavorite(id, false)
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Failed to remove from favorites"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getLocalDocuments(status: String = "active"): Flow<List<Document>> {
        return documentDao.getDocuments(status)
            .map { it.map { entity -> mapToDomain(entity) } }
    }

    fun getFavoritesLocal(): Flow<List<Document>> {
        return documentDao.getFavoriteDocuments()
            .map { it.map { entity -> mapToDomain(entity) } }
    }

    fun searchDocuments(query: String): Flow<List<Document>> {
        return documentDao.searchDocuments(query)
            .map { it.map { entity -> mapToDomain(entity) } }
    }

    private fun mapToEntity(document: Document) =
        com.electrosoft.documentapp.data.db.entities.DocumentEntity(
            id = document.id,
            filename = document.filename,
            originalName = document.originalName,
            fileType = document.fileType,
            owner = document.owner,
            fileSize = document.fileSize,
            filePath = "",
            mimeType = null,
            description = document.description,
            status = document.status,
            isShared = document.isShared,
            previewUrl = document.previewUrl,
            viewCount = document.viewCount,
            downloads = document.downloads,
            createdAt = document.createdAt,
            updatedAt = document.updatedAt
        )

    private fun mapToDomain(entity: com.electrosoft.documentapp.data.db.entities.DocumentEntity) =
        Document(
            id = entity.id,
            filename = entity.filename,
            originalName = entity.originalName,
            fileType = entity.fileType,
            owner = entity.owner,
            fileSize = entity.fileSize,
            description = entity.description,
            status = entity.status,
            isShared = entity.isShared,
            previewUrl = entity.previewUrl,
            viewCount = entity.viewCount,
            downloads = entity.downloads,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
}

// Extension function to map flow
private inline fun <T, R> Flow<List<T>>.map(crossinline transform: suspend (List<T>) -> List<R>): Flow<List<R>> =
    kotlinx.coroutines.flow.map { transform(it) }
