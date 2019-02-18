package com.century21.service.api_modify_event_status;

public interface ModifyEventStatusService {
    void updateStatus(int statusID, boolean status, String token);
}
