package com.century21.apexproperty.service.api_get_noti;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.api_get_noti.GetNoti;

import java.util.List;

public interface GetNotiService {
    List<GetNoti> getNoti(Pagination pagination);
}
