package com.github.anddd7.controller

import com.github.anddd7.entity.Product
import com.github.anddd7.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService) {
  @GetMapping
  fun products(): Flux<Product> = productService.findAll()
}
