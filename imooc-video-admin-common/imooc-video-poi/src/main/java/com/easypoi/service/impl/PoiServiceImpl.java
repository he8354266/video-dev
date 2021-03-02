package com.easypoi.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2610:46
 */

import com.easypoi.entity.CustomerEntity;
import com.easypoi.mapper.CustomerInfoMapper;
import com.easypoi.mapper.TripArrangeMapper;
import com.easypoi.mapper.TripCostMapper;
import com.easypoi.mapper.TripInfoMapper;
import com.easypoi.pojo.CustomerInfo;
import com.easypoi.pojo.TripArrange;
import com.easypoi.pojo.TripCost;
import com.easypoi.pojo.TripInfo;
import com.easypoi.service.PoiService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/26 10:46
 * @updateDate 2021/2/26 10:46
 **/
@Service
@Slf4j
public class PoiServiceImpl implements PoiService {
    @Autowired
    private TripInfoMapper tripInfoMapper = null;
    @Autowired
    private TripCostMapper tripCostMapper = null;
    @Autowired
    private TripArrangeMapper tripArrangeMapper = null;
    @Autowired
    private CustomerInfoMapper customerInfoMapper = null;
    private String TripId = null;

    @Override
    @Transactional
    public String addCustomer(CustomerEntity customerEntity) throws ParseException {
        System.out.println(customerEntity);

        System.out.println(customerEntity.getCustomer_arr());


        try {
            this.TripId = String.valueOf(UUID.randomUUID());
            TripInfo tripInfo = new TripInfo();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            tripInfo.setTripId(this.TripId);
            tripInfo.setLineName(String.valueOf(customerEntity.getLine_name()));
            tripInfo.setTripNumber(String.valueOf(customerEntity.getTrip_number()));
            tripInfo.setPeopleNum(Integer.parseInt(customerEntity.getPeople_number()));
            tripInfo.setCreateTime(simpleDateFormat.parse(customerEntity.getStart_date()));
            tripInfo.setEndTime(simpleDateFormat.parse(customerEntity.getEnd_date()));
            tripInfo.setCustomerContact(String.valueOf(customerEntity.getCustomer_contact()));
            tripInfo.setCustomerPhone(String.valueOf(customerEntity.getCustomer_phone()));
            int insert = tripInfoMapper.insert(tripInfo);

            TripCost tripCost = new TripCost();
            tripCost.setTripId(this.TripId);
            tripCost.setRemarks(customerEntity.getRemarks());
            tripCost.setContainCost(customerEntity.getContain_cost());
            tripCost.setExcludeCost(customerEntity.getExclude_cost());
            tripCost.setGive(customerEntity.getGive());
            tripCostMapper.insert(tripCost);

            CustomerInfo customerInfo = null;

            for (Map map : customerEntity.getCustomer_arr()) {
                customerInfo = new CustomerInfo();
                customerInfo.setTripId(this.TripId);
                customerInfo.setName(String.valueOf(map.get("name")));
                customerInfo.setSex(String.valueOf(map.get("sex")));
                customerInfo.setPhone(String.valueOf(map.get("phone")));
                customerInfo.setIdCard(String.valueOf(map.get("id_card")));
                customerInfo.setEmergencyContact(String.valueOf(map.get("emergency_contact")));
                customerInfo.setEmergencyPhone(String.valueOf(map.get("emergency_phone")));
                customerInfoMapper.insert(customerInfo);
            }

            TripArrange tripArrange = null;
            System.out.println(customerEntity.getTrip_info());
            for (Map map : customerEntity.getTrip_info()) {
                tripArrange = new TripArrange();
                tripArrange.setTripId(this.TripId);
                tripArrange.setTripDay(String.valueOf(map.get("trip_day")));
                tripArrange.setTripDesc(String.valueOf(map.get("trip_desc")));
                tripArrange.setTripHotel(String.valueOf(map.get("trip_hotel")));
                tripArrange.setTripMeals(String.valueOf(map.get("trip_meals")));
                tripArrange.setTripDatetime(String.valueOf(map.get("trip_datetime")));
                tripArrangeMapper.insert(tripArrange);

            }
            return this.TripId;
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("error:{}", e);

        }


        return this.TripId;
    }

    @Override
    public JSONObject customerInfo(String resultId) {
        Example example = new Example(TripInfo.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tripId", resultId);
        List<TripInfo> tripInfos = tripInfoMapper.selectByExample(example);
        List<TripCost> tripCosts = tripCostMapper.selectByExample(example);
        JSONObject jsonObject = new JSONObject();
        for (TripInfo tripInfo : tripInfos) {
            jsonObject.put("tripId", tripInfo.getTripId());
            jsonObject.put("lineName", tripInfo.getLineName());
            jsonObject.put("tripNumber", tripInfo.getTripNumber());
            jsonObject.put("peopleNum", tripInfo.getPeopleNum());

            JSONObject jsonDate = JSONObject.fromObject(tripInfo.getCreateTime());
            String createDate =   new SimpleDateFormat("yyyy/MM/dd").format((java.util.Date)JSONObject.toBean(jsonDate,Date.class));

            JSONObject jsonObject1 = JSONObject.fromObject(tripInfo.getEndTime());
            String endDate =   new SimpleDateFormat("yyyy/MM/dd").format((java.util.Date)JSONObject.toBean(jsonObject1,Date.class));

            jsonObject.put("createTime", createDate   );
            jsonObject.put("endTime", endDate);
            jsonObject.put("customerContact", tripInfo.getCustomerContact());
            jsonObject.put("customerPhone", tripInfo.getCustomerPhone());
        }
        for (TripCost tripCost : tripCosts) {
            jsonObject.put("remarks", tripCost.getRemarks());
            jsonObject.put("containCost", tripCost.getContainCost());
            jsonObject.put("excludeCost", tripCost.getExcludeCost());
            jsonObject.put("give", tripCost.getGive());
        }
        List<TripArrange> tripArranges = tripArrangeMapper.selectByExample(example);
        jsonObject.put("tripArranges", tripArranges.toArray());
        List<CustomerInfo> customerInfos = customerInfoMapper.selectByExample(example);
        jsonObject.put("customerInfos", customerInfos.toArray());
        return jsonObject;
    }
}
