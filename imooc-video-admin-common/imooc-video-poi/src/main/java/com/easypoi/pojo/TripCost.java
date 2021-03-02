package com.easypoi.pojo;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2517:28
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @description zkjy
 * @updateRemark
 * @author zkjyCoding
 * @updateUser
 * @createDate 2021/2/25 17:28
 * @updateDate 2021/2/25 17:28     
 * @version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripCost {
    @Column(name = "trip_id")
    private String tripId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "contain_cost")
    private String containCost;

    @Column(name = "exclude_cost")
    private String excludeCost;

    @Column(name = "give")
    private String give;
}
