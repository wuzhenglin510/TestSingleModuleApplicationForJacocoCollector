package com.wzl.jacoco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBasicInfo {

    private String bookName;

    private Integer stock;

}
