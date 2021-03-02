package com.easypoi.pojo;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2517:35
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
 * @createDate 2021/2/25 17:35
 * @updateDate 2021/2/25 17:35     
 * @version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripArrange {
    @Column(name = "trip_id")
    private String tripId;

    @Column(name = "trip_day")
    private String tripDay;

    @Column(name = "trip_datetime")
    private String tripDatetime;

    @Column(name = "trip_desc")
    private String tripDesc;

    @Column(name = "trip_meals")
    private String tripMeals;

    @Column(name = "trip_hotel")
    private String tripHotel;
}
