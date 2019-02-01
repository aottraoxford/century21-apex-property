package com.century21.century21cambodia.service.api_post_event;

import java.sql.Timestamp;

public interface PostEventService {
    Integer postEvent(String title, String description,String eventDate, String banner);
}
