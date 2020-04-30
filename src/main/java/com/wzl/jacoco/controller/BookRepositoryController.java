package com.wzl.jacoco.controller;

import com.wzl.jacoco.entity.BookBasicInfo;
import com.wzl.jacoco.entity.BookStorageInfo;
import com.wzl.jacoco.storage.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "repository")
@Slf4j
public class BookRepositoryController {

    @GetMapping("get_book")
    public BookBasicInfo getBook(@RequestParam(value = "bookName") String bookName) {
        BookStorageInfo bookStorageInfo = BookRepository.getBookStorageInfo(bookName);
        log.info("我觉得我们需要加点什么功能在这里");
        //我是注释啦啦啦
        /*
        我还是注释啦
         */
        return new BookBasicInfo(bookStorageInfo.getBookName(), bookStorageInfo.getStock());
    }

    @GetMapping("list_books")
    public List<BookBasicInfo> listBooks() {
        log.info("我觉得我们需要加点什么功能在这里");
        log.info("我觉得我们需要加点什么功能在这里");
        List<BookStorageInfo> bookStorageInfos = BookRepository.listBookStorageInfo();
        return bookStorageInfos.stream()
                .map(item -> new BookBasicInfo(item.getBookName(), item.getStock()))
                .collect(Collectors.toList());
    }



}
