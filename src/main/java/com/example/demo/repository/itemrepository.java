package com.example.demo.repository;

import com.example.demo.entity.cart;
import com.example.demo.entity.item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface itemrepository extends JpaRepository<item, Integer> {
}




