package lk.printxpress.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import lk.printxpress.data.local.entity.Category
import lk.printxpress.data.local.entity.Product
import lk.printxpress.data.repository.ProductRepository

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val categories: StateFlow<List<Category>> = productRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategoryId = MutableStateFlow<Long?>(null) // null = all
    val selectedCategoryId: StateFlow<Long?> = _selectedCategoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val allProducts = productRepository.getAllProducts()

    val filteredProducts: StateFlow<List<Product>> = combine(
        allProducts,
        _selectedCategoryId,
        _searchQuery
    ) { products, categoryId, query ->
        products.filter { product ->
            val matchesCategory = (categoryId == null || product.categoryId == categoryId)
            val matchesQuery = (query.isBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true))
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    fun selectCategory(categoryId: Long?) {
        _selectedCategoryId.value = categoryId
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadProduct(productId: Long) {
        viewModelScope.launch {
            _selectedProduct.value = productRepository.getProductById(productId)
        }
    }
}
