package com.thehecklers.twohopprocessor;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.annotation.PostConstruct;
import java.time.Instant;

@EnableBinding(Processor.class)
@SpringBootApplication
public class TwoHopProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoHopProcessorApplication.class, args);
    }

}

@MessageEndpoint
class MessageProcessor {
    private final Processor processor;

    MessageProcessor(Processor processor) {
        this.processor = processor;
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Subscriber doSomethingToMessage(Subscriber subscriber) {
        Subscriber sub = new Subscriber(subscriber.getId(),
                subscriber.getLastName().toUpperCase(),
                subscriber.getFirstName().toUpperCase(),
                subscriber.getSubscribeDate());
        System.out.println(sub);
        return sub;
    }
}

@Value
class Subscriber {
    private final String id, lastName, firstName;
    private final Instant subscribeDate;
}