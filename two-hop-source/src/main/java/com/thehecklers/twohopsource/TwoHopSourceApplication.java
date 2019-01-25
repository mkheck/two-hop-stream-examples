package com.thehecklers.twohopsource;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@EnableBinding(Source.class)
@EnableScheduling
@SpringBootApplication
public class TwoHopSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoHopSourceApplication.class, args);
    }

}

@Component
class Sender {
    private final Source source;
    private final SubscriberGenerator generator;

    Sender(Source source, SubscriberGenerator generator) {
        this.source = source;
        this.generator = generator;
    }

    @Scheduled(fixedRate = 1000)
    private void sendMessage() {
        Subscriber sub = generator.generate();
        source.output().send(MessageBuilder.withPayload(sub).build());
        System.out.println(sub);
    }
}

@Component
class SubscriberGenerator {
    private String[] lastNames = "Adams, Barker, Coolidge, D'Angelo, Epstein, Frederick, Galloway".split(", ");
    private String[] firstNames = "Zachary, Yolande, Xavier, Wanda, Victor, Ursula, Terry".split(", ");

    Subscriber generate() {
        int i = new Random().nextInt(6);
        return new Subscriber(UUID.randomUUID().toString(), lastNames[i], firstNames[i], Instant.now());
    }
}

@Value
class Subscriber {
    private final String id, lastName, firstName;
    private final Instant subscribeDate;
}