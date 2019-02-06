package com.century21.century21cambodia.service.api_log;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_log.Log;
import com.century21.century21cambodia.repository.api_log.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepo logRepo;

    @Override
    public List<Log> getLog(String route) {
        List<Log> logs=logRepo.getLog(route);
        if(logs.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        return logs;
    }
}
