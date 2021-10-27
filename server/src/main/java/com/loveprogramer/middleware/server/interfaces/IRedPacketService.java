package com.loveprogramer.middleware.server.interfaces;

import com.loveprogramer.middleware.server.dto.RedPacketDto;

import java.math.BigDecimal;

/**
 * @author: zhonglinsen
 * @date: 2019/3/15
 */
public interface IRedPacketService {

    /**
     * @MethodName: handOut
     * @Description: 发红包
     * @Param: [dto]
     * @Return: java.lang.String
     * @Author: YCKJ2725
     * @Date: 2021/10/10 15:17
     **/
    String handOut(RedPacketDto dto) throws Exception;

    /**
     * @MethodName: rob
     * @Description: 抢红包
     * @Param: [userId, redId]
     * @Return: java.math.BigDecimal
     * @Author: YCKJ2725
     * @Date: 2021/10/10 15:17
     **/
    BigDecimal rob(Integer userId, String redId) throws Exception;
}
