package com.example.demo.controller;

import com.example.demo.entity.cart;
import com.example.demo.entity.item;
import com.example.demo.service.itemservice;
import com.example.demo.service.cartservice;

import jakarta.persistence.Id;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class itemcontroller {

      //final 쓰는 이유 : 해당 instance가 변경 되는 것을 막음
    private final itemservice itemservice;

    private final cartservice cartservice;

    @Autowired
    public itemcontroller(itemservice itemservice, cartservice cartservice  ) {
        this.itemservice = itemservice;
        this.cartservice = cartservice;

    }


    @GetMapping("/demo/add")
    public String showitemadd() {

        return "itemadd"; // 상품 등록 폼으로 이동
    }

   /* @PostMapping("/demo/add1")
    public String showitemadd(item item, Model model,MultipartFile file) throws IOException {

        itemservice.add(item,file);


        return "redirect:/demo/list";
    }*/

     @PostMapping("/demo/add1")
    public String showitemadd(item item,
                              @RequestParam("imgFile") MultipartFile file,
                              Model model) {
        try {
            // 이미지 업로드 서비스 호출
            itemservice.uploadItem(item,file);
            model.addAttribute("message", "상품이 성공적으로 등록되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "상품 등록 중 오류가 발생했습니다.");
        }

        model.addAttribute("message" , "상품이 성공적으로 등록되었습니다.");
        model.addAttribute("searchUrl", "/demo/list");


        return "message";
      //  return "redirect:/demo/list";
    }



    @GetMapping("/demo/list")
    public String showitemlist(item item, Model model,@PageableDefault(page=0,size = 5, sort = "id"
            ,direction = Sort.Direction.DESC) Pageable pageable) {

       // List<item> itemlist = itemservice.getAllitem(pageable);

        Page<item> itemlist = itemservice.getAllitem(pageable);


        int nowPage = itemlist.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4,1);
        int endPage = Math.min(nowPage+4,itemlist.getTotalPages());


        model.addAttribute("items", itemlist);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "itemlist"; // item.html로 이동
    }

    @GetMapping("/demo/view")
    public String itemview(@RequestParam(name = "id") Integer id, Model model) {


        model.addAttribute("items",itemservice.getitem(id));



        return "itemview";
    }

    @PostMapping("/demo/cart")
    public String addToCart(@RequestParam("productId") Long productId,Model model) {
        // productId를 사용하여 상품을 조회합니다.
        item product = itemservice.getItemById(Math.toIntExact(productId));

        // 장바구니에 상품을 추가합니다.
        cartservice.addToCart(product);

        model.addAttribute("message" , "장바구니가 담았습니다.");
        model.addAttribute("searchUrl", "/cart");


        // 장바구니 페이지로 리다이렉트합니다.
        /*return "redirect:/cart";*/
        return "message";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<cart> cartItems = cartservice.getAllItems(); // 장바구니에 담긴 모든 상품을 가져옴
        model.addAttribute("cartItems", cartItems); // 모델에 장바구니 상품 목록 추가

        return "itemcart"; // cart.html로 이동
    }


    @GetMapping("/cart/delete")
    public String cartdelete( Model model){


        cartservice.delete();

        model.addAttribute("message" , "장바구니가 비워졌습니다.");
        model.addAttribute("searchUrl", "/cart");



        return "message";
    }


}








