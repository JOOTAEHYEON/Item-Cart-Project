package com.example.demo.service;

import com.example.demo.entity.item;
import com.example.demo.repository.itemrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class itemservice {

    @Autowired
    private itemrepository itemrepository;



    // 상품 이미지 업로드 처리
    /*public void uploadItem(item item, MultipartFile file) throws IOException {
        // 상품 이미지를 저장할 디렉토리 경로
        String uploadDir = "src/main/resources/static/images/";
       // "C:/abc/"


        // 랜덤한 UUID를 생성하여 파일 이름에 포함시킴 (중복을 방지하기 위함)
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 파일을 디렉토리에 저장
        File saveFile = new File(uploadDir, fileName);
        file.transferTo(saveFile);

        // item 엔티티에 파일 이름과 경로 설정
        item.setFilename(fileName);
        item.setFilepath("/images/"+ fileName);

      //  "C:/abc/"

        // 상품 정보 저장
        itemrepository.save(item);
    }*/


    public void uploadItem(item item, MultipartFile file) throws IOException {
        // 상품 이미지를 저장할 디렉토리 경로
        String uploadDir = "src/main/resources/static/images/";

        // 랜덤한 UUID를 생성하여 파일 이름에 포함시킴 (중복을 방지하기 위함)
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        // 파일을 디렉토리에 저장
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // item 엔티티에 파일 이름과 경로 설정
        item.setFilename(fileName);
        item.setFilepath("/images/" + fileName); // 이미지의 상대 경로로 설정

        // 상품 정보 저장
        itemrepository.save(item);
    }


   /* public void add(item item){


        itemrepository.save(item); //sava 내장함수



    }*/

    // public List<item> getAllitem(Pageable pageable) {






    public Page<item> getAllitem(Pageable pageable) {

        return itemrepository.findAll(pageable);

    }



    //상품 조회
    public item getitem(Integer id){


        return itemrepository.findById(id).get();
    }


    //장바구니 조회
    public item getItemById(long productId) {

        return itemrepository.findById((int) productId).get();


    }
}



