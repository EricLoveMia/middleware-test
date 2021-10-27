package com.loveprogramer.middleware.server.rabbitmq.publisher;/**
 * Created by Administrator on 2019/3/31.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loveprogramer.middleware.server.rabbitmq.entity.EventInfo;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 消息模型-生产者
 *
 * @Author:debug (SteadyJack)
 * @Date: 2019/3/31 21:52
 **/
@Component
public class ModelPublisher {

    private static final Logger log = LoggerFactory.getLogger(ModelPublisher.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    /**
     * 发送消息-基于FanoutExchange消息模型
     *
     * @param info
     */
    public void sendMsgFanout(EventInfo info) {
        if (info != null) {
            try {
                // 定义传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                // 设置广播室交换机
                rabbitTemplate.setExchange(env.getProperty("mq.fanout.exchange.name"));
                // 创建消息实例
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                // 发送消息
                rabbitTemplate.convertAndSend("", msg);

                log.info("消息模型fanoutExchange-生产者-发送消息：{} ", info);
            } catch (Exception e) {
                log.error("消息模型fanoutExchange-生产者-发送消息发生异常：{} ", info, e.fillInStackTrace());
            }
        }
    }

    public void sendMsgFanoutTwo(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            try {
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setContentEncoding("UTF-8");
                Message message = new Message(msg.getBytes(), messageProperties);
                // 发送消息
                rabbitTemplate.send(env.getProperty("mq.fanout.exchange.name"), "", message);

                log.info("消息模型fanoutExchange-生产者-发送消息：{} ", msg);
            } catch (Exception e) {
                log.error("消息模型fanoutExchange-生产者-发送消息发生异常：{} ", msg, e.fillInStackTrace());
            }
        }
    }

    /**
     * 发送消息-基于DirectExchange消息模型-one
     *
     * @param info
     */
    public void sendMsgDirectOne(EventInfo info) {
        if (info != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.one.name"));

                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);

                log.info("消息模型DirectExchange-one-生产者-发送消息：{} ", info);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-one-生产者-发送消息发生异常：{} ", info, e.fillInStackTrace());
            }
        }
    }

    /**
     * 发送消息-基于DirectExchange消息模型-two
     *
     * @param info
     */
    public void sendMsgDirectTwo(EventInfo info) {
        if (info != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));

                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);

                log.info("消息模型DirectExchange-two-生产者-发送消息：{} ", info);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-two-生产者-发送消息发生异常：{} ", info, e.fillInStackTrace());
            }
        }
    }


    /**
     * 发送消息-基于TopicExchange消息模型
     *
     * @param msg
     */
    public void sendMsgTopic(String msg, String routingKey) {
        //判断对象是否为null
        if (!Strings.isNullOrEmpty(msg) && !Strings.isNullOrEmpty(routingKey)) {
            try {
                //设置消息的传输格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定交换机
                rabbitTemplate.setExchange(env.getProperty("mq.topic.exchange.name"));
                //指定路由的实际取值，根据不同取值，RabbitMQ将自行进行匹配通配符，从而路由到不同的队列中
                rabbitTemplate.setRoutingKey(routingKey);

                //创建消息
                Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).build();
                //发送消息
                rabbitTemplate.convertAndSend(message);

                //打印日志
                log.info("消息模型TopicExchange-生产者-发送消息：{}  路由：{} ", msg, routingKey);
            } catch (Exception e) {
                log.error("消息模型TopicExchange-生产者-发送消息发生异常：{} ", msg, e.fillInStackTrace());
            }
        }
    }


}



















