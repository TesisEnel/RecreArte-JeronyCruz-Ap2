package edu.ucne.recrearte.data.repository

import edu.ucne.recrearte.data.remote.RemoteDataSource
import edu.ucne.recrearte.data.remote.Resource
import edu.ucne.recrearte.data.remote.dto.TechniquesDto
import edu.ucne.recrearte.data.remote.dto.WorksDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class WorkRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getWorks(): Flow<Resource<List<WorksDto>>> = flow {
        try {
            emit(Resource.Loading())
            val work = remoteDataSource.getWorks()
            emit(Resource.Success(work))
        }catch (e: HttpException){
            emit(Resource.Error("Internet error: ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.message}"))
        }
    }

    suspend fun getWorkById(id: Int): Resource<WorksDto> {
        return try {
            val work = remoteDataSource.getByIdWork(id)
            Resource.Success(work)
        } catch (e: HttpException) {
            Resource.Error("Internet error: ${e.message()}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.message}")
        }
    }

    suspend fun createWork(workDto: WorksDto) = remoteDataSource.createWork(workDto)

    suspend fun updateWork(id: Int, workDto: WorksDto) = remoteDataSource.updateWork(id, workDto)

    suspend fun deleteWork(id: Int) = remoteDataSource.deleteWork(id)

    fun getWorksByTechnique(techniqueId: Int): Flow<Resource<List<WorksDto>>> = flow {
        try {
            emit(Resource.Loading())
            val work = remoteDataSource.getWorksByTechnique(techniqueId)
            emit(Resource.Success(work))
        }catch (e: HttpException){
            emit(Resource.Error("Internet error: ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.message}"))
        }
    }

    fun getWorksByArtist(artistId: Int): Flow<Resource<List<WorksDto>>> = flow {
        try {
            emit(Resource.Loading())
            val work = remoteDataSource.getWorksByArtist(artistId)
            emit(Resource.Success(work))
        }catch (e: HttpException){
            emit(Resource.Error("Internet error: ${e.message()}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.message}"))
        }
    }
}