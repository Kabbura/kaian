package com.narbase.kabbura.kaian.web.network.crud

import com.narbase.kabbura.kaian.web.network.ServerCaller
import com.narbase.kabbura.kaian.web.storage.StorageManager
import com.narbase.kabbura.kaian.web.utils.DataResponse

/*
 * Copyright 2017-2020 Narbase technologies and contributors. Use of this source code is governed by the MIT License.
 */

@Suppress("unused")
open class PrivilegedCrudServerCaller<T, D>(baseEndPoint: String, endEndPoint: String) {
    protected val accessToken
        get() = StorageManager.accessToken

    private val publicEndPoint = "$baseEndPoint/$endEndPoint"
    private val privilegedEndPoint = "$baseEndPoint/edit_${endEndPoint.trimStart('/')}"

    suspend fun getList(dto: CrudDto.GetList.Request<D>) =
        ServerCaller.synchronousPost<DataResponse<CrudDto.GetList.Response<T>>>(
            url = "$publicEndPoint/list",
            headers = mapOf("Authorization" to "Bearer $accessToken"),
            body = dto
        )

    suspend fun getDetails(dto: DataResponse<CrudDto.GetItem.Request>) =
        ServerCaller.synchronousPost<DataResponse<CrudDto.GetItem<T>.Response>>(
            url = "$publicEndPoint/details",
            headers = mapOf("Authorization" to "Bearer $accessToken"),
            body = dto
        )

    suspend fun add(dto: T) =
        ServerCaller.synchronousPost<DataResponse<Unit>>(
            url = "$privilegedEndPoint/add",
            headers = mapOf("Authorization" to "Bearer $accessToken"),
            body = dto
        )

    suspend fun update(dto: T) =
        ServerCaller.synchronousPost<DataResponse<Unit>>(
            url = "$privilegedEndPoint/update",
            headers = mapOf("Authorization" to "Bearer $accessToken"),
            body = dto
        )

    suspend fun delete(dto: CrudDto.Delete.Request) =
        ServerCaller.synchronousPost<DataResponse<Unit>>(
            url = "$privilegedEndPoint/delete",
            headers = mapOf("Authorization" to "Bearer $accessToken"),
            body = dto
        )
}
