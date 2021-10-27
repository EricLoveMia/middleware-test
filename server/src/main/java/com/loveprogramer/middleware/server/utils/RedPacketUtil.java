package com.loveprogramer.middleware.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName RedPacketUtil
 * @Description: TODO
 * @Author YCKJ2725
 * @Date 2021/10/10
 * @Version V1.0
 **/
public class RedPacketUtil {

    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {

        List<Integer> amountList = new ArrayList<>();

        // 判断合法性
        if (totalAmount > 0 && totalPeopleNum > 0) {
            Integer restAmount = totalAmount;

            Integer restPeople = totalPeopleNum;

            // 随机对象
            Random random = new Random();

            for (int i = 0; i < totalPeopleNum - 1; i++) {

                int amount = random.nextInt(restAmount / restPeople * 2 - 1) + 1;

                restAmount -= amount;

                restPeople--;

                amountList.add(amount);

            }
            amountList.add(restAmount);


        }
        return amountList;
    }
}
