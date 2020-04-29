package com.wzl.jacoco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IOU {

    private String name;

    private String borrowTime;

    private Integer amount;

    private Integer needReturnAmount;


}
