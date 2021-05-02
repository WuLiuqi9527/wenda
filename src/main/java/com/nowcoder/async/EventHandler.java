package com.nowcoder.async;

import java.util.List;

/**
 * @author hc
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
