package com.wzl.jacoco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStorageInfo {

    private String bookName;

    private Integer stock;

    private LinkedList<IOU> iouList;

    private LinkedList<ReturnLog> returnLogs;

}
