package edu.team3.onlineshop.service;

import edu.team3.onlineshop.domain.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender{

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${product.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${product.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Product product) {
		rabbitTemplate.convertAndSend(exchange, routingkey, product);
		System.out.println("Send msg = " + product);
	    
	}
}
