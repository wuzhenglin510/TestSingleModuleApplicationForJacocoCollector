package com.wzl.jacoco.controller;

import com.wzl.jacoco.entity.BookBasicInfo;
import com.wzl.jacoco.entity.BookStorageInfo;
import com.wzl.jacoco.storage.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "repository")
public class BookRepositoryController {

    @GetMapping("get_book")
    public BookBasicInfo getBook(@RequestParam(value = "bookName") String bookName) {
        BookStorageInfo bookStorageInfo = BookRepository.getBookStorageInfo(bookName);
        //我是注释啦啦啦
        /*
        我还是注释啦
         */
        return new BookBasicInfo(bookStorageInfo.getBookName(), bookStorageInfo.getStock());
    }

    @GetMapping("list_books")
    public List<BookBasicInfo> listBooks() {
        List<BookStorageInfo> bookStorageInfos = BookRepository.listBookStorageInfo();
        return bookStorageInfos.stream()
                .map(item -> new BookBasicInfo(item.getBookName(), item.getStock()))
                .collect(Collectors.toList());
    }



}
