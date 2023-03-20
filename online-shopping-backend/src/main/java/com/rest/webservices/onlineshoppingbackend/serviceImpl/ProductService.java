package com.rest.webservices.onlineshoppingbackend.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.webservices.onlineshoppingbackend.entity.Product;
import com.rest.webservices.onlineshoppingbackend.service.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void addProduct(Product product) {
		productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public List<Product> searchProductByName(String productName) {
		return productRepository.findByNameContaining(productName);
	}

	public Product addProduct(String productName, Product product) {
		product.setName(productName);
		return productRepository.save(product);
	}

	public Product updateProductStatus(String productName, Long id, String status) {
		Optional<Product> optionalProduct = productRepository.findByIdAndName(id, productName);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setStatus(status);
			return productRepository.save(product);
		}
		return null;
	}

	public void deleteProduct(String productName, Long id) {
		List<Product> products = productRepository.findAllByName(productName);
		if (products != null) {
			Optional<Product> productToRemove = products.stream().filter(product -> product.getId().equals(id))
					.findFirst();
			if (productToRemove.isPresent()) {
				products.remove(productToRemove.get());
			}
		}
		productRepository.deleteById(id);
	}

//	public int getQuantityForProduct(String productName) {
//        Product product = productRepository.findByProductName(productName);
//        if (product == null) {
//            throw new ResourceNotFoundException("Product with name " + productName + " not found");
//        }
//        return product.getQuantity();
//    }

//	public int getNumOrdersForProduct(String productName) {
//	    int numOrders = 0;
//	    for (Order order : orders) {
//	        for (OrderItem orderItem : order.getOrderItems()) {
//	            if (orderItem.getProduct().getName().equals(productName)) {
//	                numOrders += orderItem.getQuantity();
//	                break;
//	            }
//	        }
//	    }
//	    return numOrders;
//	}

}
