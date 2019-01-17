package com.century21.century21cambodia.service.api_save_noti;

import com.century21.century21cambodia.repository.api_save_noti.SaveNoti;
import org.springframework.stereotype.Repository;

public interface SaveNotiService {
    void saveNoti(SaveNoti saveNoti,String email);
}
