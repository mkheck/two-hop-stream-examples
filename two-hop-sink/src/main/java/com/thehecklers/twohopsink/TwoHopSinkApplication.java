package com.thehecklers.twohopsink;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;

import java.time.Instant;

@EnableBinding(Sink.class)
@SpringBootApplication
public class TwoHopSinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoHopSinkApplication.class, args);
    }

}

@MessageEndpoint
class EndOfLine {

    @StreamListener(Sink.INPUT)
    private void showMessage(Subscriber subscriber) {
        System.out.println(subscriber.toString());
    }

}


@Value
class Subscriber {
    private final String id, lastName, firstName;
    private final Instant subscribeDate;
}