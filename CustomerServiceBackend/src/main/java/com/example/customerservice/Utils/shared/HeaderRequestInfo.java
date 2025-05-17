package com.example.customerservice.Utils.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;


@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
public class HeaderRequestInfo {
    private String departmentcode;
    private String branchcode;
    private String costcentercode;
    private String membercode;
}
