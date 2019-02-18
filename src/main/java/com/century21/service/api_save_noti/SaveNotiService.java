package com.century21.service.api_save_noti;

import com.century21.repository.api_save_noti.SaveNoti;

public interface SaveNotiService {
    void saveNoti(SaveNoti saveNoti, String email);
}
