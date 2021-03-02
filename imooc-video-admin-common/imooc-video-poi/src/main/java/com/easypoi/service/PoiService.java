package com.easypoi.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2610:45
 */

import com.easypoi.entity.CustomerEntity;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.util.Map;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/26 10:45
 * @updateDate 2021/2/26 10:45
 **/
public interface PoiService {
    String addCustomer(CustomerEntity customerEntity) throws ParseException;

    JSONObject customerInfo(String resultId);
}
