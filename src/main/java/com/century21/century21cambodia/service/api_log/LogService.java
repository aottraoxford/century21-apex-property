package com.century21.century21cambodia.service.api_log;

import com.century21.century21cambodia.repository.api_log.Log;

import java.util.List;

public interface LogService {
    List<Log> getLog(String route);
}
