package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class item {//vo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id; // 상품 순번
    private String name; // 상품 이름
    private String description; //상품 설명
    private String price; // 상품 가격
    private String filename; // 상품 이미지 파일 이름
    private String filepath; // 상품 이미지 파일 경로



    // 생성자, getter 및 setter 생략

}
