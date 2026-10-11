package lk.printxpress.data.repository

import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.AppDatabase
import lk.printxpress.data.local.entity.Category
import lk.printxpress.data.local.entity.Product

class ProductRepository(private val database: AppDatabase) {

    fun getCategories(): Flow<List<Category>> {
        return database.productDao().getAllCategories()
    }

    fun getAllProducts(): Flow<List<Product>> {
        return database.productDao().getAllProducts()
    }

    fun getProductsByCategory(categoryId: Long): Flow<List<Product>> {
        return database.productDao().getProductsByCategory(categoryId)
    }

    suspend fun getProductById(productId: Long): Product? {
        return database.productDao().getProductById(productId)
    }

    fun observeProductById(productId: Long): Flow<Product?> {
        return database.productDao().observeProductById(productId)
    }
}
