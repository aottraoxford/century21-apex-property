package com.century21.apexproperty.service.api_get_noti;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_get_noti.GetNofiRepo;
import com.century21.apexproperty.repository.api_get_noti.GetNoti;
import com.century21.apexproperty.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetNotiServiceImpl implements GetNotiService {
    @Autowired
    private GetNofiRepo getNofiRepo;
    @Override
    public List<GetNoti> getNoti(String email) {
        Integer userID=getNofiRepo.getUserID(email);
        if(userID==null) throw new CustomRuntimeException(404,"USER NOT FOUND.");
        List<GetNoti> getNotiList=getNofiRepo.getNoti(userID);
        if(getNotiList==null || getNotiList.size()<1) throw new CustomRuntimeException(404,"NO RESULT");
        for(int i=0;i<getNotiList.size();i++){
            if(getNotiList.get(i).getType().equals("project")) {
                getNotiList.get(i).setImage(Url.projectThumbnailUrl+getNofiRepo.getThumbnail(getNotiList.get(i).getRefID()));
            }
            else if(getNotiList.get(i).getType().equals("event")){
                getNotiList.get(i).setImage(Url.bannerUrl+getNofiRepo.getBanner(getNotiList.get(i).getRefID()));
            }
        }
        return getNotiList;
    }
}
