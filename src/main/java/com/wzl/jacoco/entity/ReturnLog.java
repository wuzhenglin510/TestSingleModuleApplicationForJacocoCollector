package com.wzl.jacoco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnLog {

    private String name;

    private String returnTime;

    private Integer amount;

}
