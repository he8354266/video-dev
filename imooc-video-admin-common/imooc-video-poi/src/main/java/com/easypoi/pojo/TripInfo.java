package com.easypoi.pojo;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2515:37
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/25 15:37
 * @updateDate 2021/2/25 15:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripInfo {
    @Id
    private String id;

    @Column(name = "trip_id")
    private String tripId;

    @Column(name = "line_name")
    private String lineName;

    @Column(name = "trip_number")
    private String tripNumber;

    @Column(name = "people_num")
    private Integer peopleNum;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "customer_contact")
    private String customerContact;

    @Column(name = "customer_phone")
    private String customerPhone;


}
