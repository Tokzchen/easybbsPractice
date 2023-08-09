package com.example.easybbsweb.config;

import com.example.easybbsweb.common.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    // 定义一个交换器
    @Bean
    public DirectExchange esExchange() {
        return new DirectExchange(RabbitMQConstants.ES_EXCHANGE);
    }

    // 定义一个队列
    @Bean
    public Queue esSaveQueue() {
        return new Queue(RabbitMQConstants.ES_SAVE_QUEUE);
    }

    @Bean
    public Queue esUpdateQueue() {
        return new Queue(RabbitMQConstants.ES_UPDATE_QUEUE);
    }
    @Bean
    public Queue esUpdateLikeQueue() {
        return new Queue(RabbitMQConstants.ES_UPDATE_LIKE_QUEUE);
    }

    @Bean
    public Queue esDeleteQueue() {
        return new Queue(RabbitMQConstants.ES_DELETE_QUEUE);
    }
    @Bean
    public Queue likeLikeQueue() {
        return new Queue(RabbitMQConstants.LIKE_LIKE_QUEUE);
    }

    // 将队列绑定到交换器，并指定路由键
    @Bean
    public Binding ESSaveBinding() {
        return new Binding(
                RabbitMQConstants.ES_SAVE_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitMQConstants.ES_EXCHANGE,
                RabbitMQConstants.ES_SAVE_KEY,
                null
        );
    }

    @Bean
    public Binding ESUpdateBinding() {
        return new Binding(
                RabbitMQConstants.ES_UPDATE_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitMQConstants.ES_EXCHANGE,
                RabbitMQConstants.ES_UPDATE_KEY,
                null
        );
    }

    @Bean
    public Binding ESDeleteBinding() {
        return new Binding(
                RabbitMQConstants.ES_DELETE_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitMQConstants.ES_EXCHANGE,
                RabbitMQConstants.ES_DELETE_KEY,
                null
        );
    }
    @Bean
    public Binding ESUpdateLikeBinding() {
        return new Binding(
                RabbitMQConstants.ES_UPDATE_LIKE_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitMQConstants.ES_EXCHANGE,
                RabbitMQConstants.ES_UPDATE_LIKE_KEY,
                null
        );
    }
}