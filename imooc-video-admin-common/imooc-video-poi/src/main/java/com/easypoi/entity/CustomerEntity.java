package com.easypoi.entity;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2615:48
 */

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/26 15:48
 * @updateDate 2021/2/26 15:48
 **/
@Data
public class CustomerEntity {
    private String line_name;

    private String trip_number;
    private String people_number;
    private String start_date;
    private String end_date;
    private String customer_contact;
    private String customer_phone;
    private String remarks;
    private String contain_cost;
    private String exclude_cost;
    private String give;
    private List<Map> customer_arr;

    private List<Map> trip_info;
}
