package com.easypoi.util;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2415:38
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/24 15:38
 * @updateDate 2021/2/24 15:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object data;
}
