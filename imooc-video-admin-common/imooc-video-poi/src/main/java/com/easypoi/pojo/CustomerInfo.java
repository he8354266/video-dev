package com.easypoi.pojo;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/269:01
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/26 9:01
 * @updateDate 2021/2/26 9:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    @Column(name = "trip_id")
    private String tripId;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_phone")
    private String emergencyPhone;
}
