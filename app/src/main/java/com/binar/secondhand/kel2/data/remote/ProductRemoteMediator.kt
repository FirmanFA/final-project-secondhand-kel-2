package com.binar.secondhand.kel2.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import com.binar.secondhand.kel2.data.local.room.database.AppDatabase
import com.binar.secondhand.kel2.data.local.room.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val apiHelper: ApiHelper,
    private val appDatabase: AppDatabase,
    private val category: Int? = null
) : RemoteMediator<Int, GetProductResponseItem>() {

    private val startPageIndex = 1

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GetProductResponseItem>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: startPageIndex
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse =
                apiHelper.getProduct(categoryId = category, page = page, itemsPerPage = state.config.pageSize)
            val products = apiResponse.body()?.productResponseItem
            val endOfPaginationReached = products?.isEmpty()

            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                    appDatabase.productDao().clearProducts()
                }
                val prevKey = if (page == startPageIndex) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = products?.map {
                    RemoteKeys(productId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                if (keys != null) {
                    appDatabase.remoteKeysDao().insertAll(keys)
                }
                if (products != null) {
                    products.forEach {product->
                        val categoryString = product.categories.joinToString { it.name }
                        product.categoryString = categoryString
                    }
                    appDatabase.productDao().insertAll(products)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GetProductResponseItem>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysProductId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GetProductResponseItem>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysProductId(product.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GetProductResponseItem>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { productId ->
                appDatabase.remoteKeysDao().remoteKeysProductId(productId)
            }
        }
    }

}