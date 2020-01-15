package com.github.anddd7.service

import com.github.anddd7.entity.Product
import com.github.anddd7.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProductService(private val productRepository: ProductRepository) {
  fun findAll(): Flux<Product> = productRepository.findAll()
}
