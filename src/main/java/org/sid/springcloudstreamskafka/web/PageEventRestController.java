package org.sid.springcloudstreamskafka.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sid.springcloudstreamskafka.entities.PageEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
@AllArgsConstructor
public class PageEventRestController {

    private StreamBridge streamBridge;

    @GetMapping("/publish/{topic}/{name}")
    public PageEvent publishPageEvent(@PathVariable String topic, @PathVariable String name) {

        PageEvent pageEvent = new PageEvent( name , Math.random()>0.5?"U1":"U2" , new Date() , new Random().nextInt(9000));
        streamBridge.send( topic , pageEvent );

        return pageEvent;
    }

}
