package com.example.getinline.domain.event;

import com.example.getinline.domain.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

// entity 하위 또는 동일 패키지에 있어야 함
// 보여줄 정보를 지정 가능
@Projection(name = "eventInfo", types = {Event.class})
public interface EventResource {

    @Value("#{target.id}")
    Long getId();

    @Value("#{target.eventName}")
    String getEventName();
}
