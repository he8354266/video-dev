package com.easypoi.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2310:31
 */

import com.easypoi.entity.CustomerEntity;
import com.easypoi.mapper.TripInfoMapper;
import com.easypoi.pojo.TripInfo;
import com.easypoi.service.PoiService;
import com.easypoi.util.PDFUtil;
import com.easypoi.util.ResponseResult;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/23 10:31
 * @updateDate 2021/2/23 10:31
 **/
@Controller
public class PoiController {
    @Autowired
    private PoiService poiService;

    @RequestMapping("/poi")
    public String poi(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/form")
    public String form(HttpServletRequest request) {
        return "form";
    }

    @PostMapping("/postInfo")
    @ResponseBody
    public ResponseResult postInfo(HttpServletRequest request, CustomerEntity customerEntity) throws ParseException {
        String resultId = poiService.addCustomer(customerEntity);
        Map<String, String> map = new HashMap<>();
        map.put("resultId", resultId);
        if (resultId != null) {
            return new ResponseResult(1, "数据提交成功", map);
        } else {
            return new ResponseResult(0, "数据提交失败", "");
        }


    }

    @GetMapping("/customerInfo")
    @ResponseBody
    public ResponseResult customerInfo(@RequestParam String resultId) {
        JSONObject jsonObject = poiService.customerInfo(resultId);
        return new ResponseResult(1, "", jsonObject);
    }


    @ResponseBody
    @RequestMapping(value = "projectExport", method = RequestMethod.GET)
    public void projectExport(HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        map.put("test", "ceshi");
        try {
            ByteArrayOutputStream pdf = PDFUtil.createPDF("templates/index.html", map);
            PDFUtil.renderPdf(response, pdf.toByteArray(), "pdf文件");
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
