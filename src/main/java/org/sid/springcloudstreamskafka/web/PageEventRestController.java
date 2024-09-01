package org.sid.springcloudstreamskafka.web;

import lombok.AllArgsConstructor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.*;
import org.sid.springcloudstreamskafka.entities.PageEvent;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@AllArgsConstructor
public class PageEventRestController {

    private StreamBridge streamBridge;
    private InteractiveQueryService interactiveQueryService;

    @GetMapping("/publish/{topic}/{name}")
    public PageEvent publishPageEvent(@PathVariable String topic, @PathVariable String name) {

        PageEvent pageEvent = new PageEvent( name , Math.random()>0.5?"U1":"U2" , new Date() , new Random().nextInt(9000));
        streamBridge.send( topic , pageEvent );

        return pageEvent;
    }

//    @GetMapping(path="/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Map<String,Long>> analytics() {
//        return Flux.interval(Duration.ofSeconds(1))
//            .map(sequence ->{
//                Map<String,Long> stringLongMap = new HashMap<>();
//                ReadOnlyWindowStore<String,Long> windowStore = interactiveQueryService.getQueryableStore("page-count", QueryableStoreTypes.windowStore());
//                Instant now = Instant.now();
//                Instant from = now.minusMillis(5000);
//                KeyValueIterator<Windowed<String>, Long> fetchAll = windowStore.fetchAll(from,now);
////                    WindowStoreIterator<Long> fetch = windowStore.fetch("P1",from,now);
//                while (fetchAll.hasNext()) {
//                    KeyValue<Windowed<String>, Long> next = fetchAll.next();
//                    stringLongMap.put(next.key.key(),next.value);
//                }
//                return stringLongMap;
//            }).share();
//    }
}
