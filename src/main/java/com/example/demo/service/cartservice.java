package com.example.demo.service;

import com.example.demo.entity.cart;
import com.example.demo.entity.item;
import com.example.demo.repository.cartrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cartservice {

    @Autowired
    private  cartrepository cartrepository;

    public void addToCart(item product) {
        // 새로운 cart 엔티티 생성
        cart newCartItem = new cart();
        // 상품 정보를 cart 엔티티에 설정
        newCartItem.setName(product.getName());
        newCartItem.setDescription(product.getDescription());
        newCartItem.setPrice(product.getPrice());
        newCartItem.setFilename(product.getFilename());
        newCartItem.setFilepath(product.getFilepath());
        // 장바구니에 상품 저장
        cartrepository.save(newCartItem);
    }

    public List<cart> getAllItems() {


        return cartrepository.findAll();
    }

    public void delete() {

        cartrepository.deleteAll();

    }
}

  /*  public void addToCart(item product) {

        cartrepository.save(product);

    }*/
