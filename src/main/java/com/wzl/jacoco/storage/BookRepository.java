package com.wzl.jacoco.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzl.jacoco.entity.BookStorageInfo;
import com.wzl.jacoco.entity.IOU;
import com.wzl.jacoco.entity.ReturnLog;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class BookRepository {

    private static final Map<String, BookStorageInfo> storage;

    static {
        storage = new HashMap<>();
        storage.put("数学之美", new BookStorageInfo("数学之美", 3, new LinkedList<>(), new LinkedList<>()));
        storage.put("算法导论", new BookStorageInfo("算法导论", 2, new LinkedList<>(), new LinkedList<>()));
    }

    /**
     * 获取全部书本信息
     * @return
     */
    public static List<BookStorageInfo> listBookStorageInfo() {
        return JSONArray.parseArray(JSONArray.toJSONString(storage.values()), BookStorageInfo.class);
    }

    /**
     * 获取指定书本信息
     * @return
     */
    public static BookStorageInfo getBookStorageInfo(String bookName) {
        assert storage.containsKey(bookName) : "book not exist!";
        return JSONObject.parseObject(JSONObject.toJSONString(storage.get(bookName)), BookStorageInfo.class);
    }

    /**
     * 新书入库
     * @param bookName
     * @param stock
     */
    public static void addBook(String bookName, Integer stock) {
        synchronized (BookRepository.class) {
            if (storage.containsKey(bookName)) {
                BookStorageInfo existBookInfo = storage.get(bookName);
                existBookInfo.setStock(existBookInfo.getStock() + stock);
                storage.put(bookName, existBookInfo);
            } else {
                BookStorageInfo bookStorageInfo = new BookStorageInfo();
                bookStorageInfo.setStock(stock);
                bookStorageInfo.setBookName(bookName);
                bookStorageInfo.setIouList(new LinkedList<>());
                storage.put(bookName, bookStorageInfo);
            }
        }
    }

    /**
     * 借书
     * @param bookName
     * @param borrower
     * @param amount
     * @throws Exception
     */
    public static void borrowBook(String bookName, String borrower, Integer amount) {
        log.info("我觉得我们需要加点什么功能在这里");
        assert storage.containsKey(bookName) : "book should exist in storage!";
        assert amount > 0 : "borrow amount should large then zero!";
        synchronized (BookRepository.class) {
            BookStorageInfo existBookInfo = storage.get(bookName);
            IOU someoneUnReturnIOU = findSomeoneUnReturnIOU(borrower, existBookInfo.getIouList());
            assert someoneUnReturnIOU == null : "can`t borrow the same book before you return it!";
            assert existBookInfo.getStock() >= amount : "borrow amount can`t large than book stock!";
            existBookInfo.setStock(existBookInfo.getStock() - amount);
            IOU iou = new IOU(borrower, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount, amount);
            existBookInfo.getIouList().addFirst(iou);
            storage.put(bookName, existBookInfo);
        }
    }

    /**
     * 还书
     * @param bookName
     * @param borrower
     * @param amount
     * @throws Exception
     */
    public static void returnBook(String bookName, String borrower, Integer amount) throws Exception {
        log.info("我觉得我们需要加点什么功能在这里");
        if (!storage.containsKey(bookName)) {
            throw new Exception("book info not exist in storage");
        }
        assert amount > 0 : "return amount can`t large than book stock!";
        synchronized (BookRepository.class) {
            BookStorageInfo existBookInfo = storage.get(bookName);
            IOU someoneUnReturnIOU = findSomeoneUnReturnIOU(borrower, existBookInfo.getIouList());
            assert someoneUnReturnIOU != null : "you haven`t borrow the book yet!";
            existBookInfo.setStock(existBookInfo.getStock() + amount);
            someoneUnReturnIOU.setNeedReturnAmount(someoneUnReturnIOU.getNeedReturnAmount() - amount);
            existBookInfo.getReturnLogs()
                    .addFirst(new ReturnLog(borrower, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), amount));
            storage.put(bookName, existBookInfo);
        }
    }

    private static IOU findSomeoneUnReturnIOU(String borrowerName, LinkedList<IOU> iouList) {
        for (IOU iou : iouList) {
            if (iou.getName().equals(borrowerName)) {
                if (iou.getNeedReturnAmount() > 0) {
                    return iou;
                }
            }
        }
        return null;
    }

}