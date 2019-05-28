package com.century21.apexproperty.service.api_get_noti;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.model.Pagination;
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
    public List<GetNoti> getNoti(String email, Pagination pagination) {
        Integer userID=getNofiRepo.getUserID(email);
        if(userID==null) throw new CustomRuntimeException(404,"USER NOT FOUND.");
        List<GetNoti> getNotiList=getNofiRepo.getNoti(userID);
        if(getNotiList==null || getNotiList.size()<1) throw new CustomRuntimeException(404,"NO RESULT");
        for(int i=0;i<getNotiList.size();i++){
            if(getNotiList.get(i).getType().equalsIgnoreCase("project")) {
                String thumnail=getNofiRepo.getThumbnail(getNotiList.get(i).getRefID());
                if(thumnail!=null)
                    getNotiList.get(i).setImage(thumnail);
            }
            else if(getNotiList.get(i).getType().equalsIgnoreCase("event")){
                String banner = getNofiRepo.getBanner(getNotiList.get(i).getRefID());
                if(banner!=null)
                    getNotiList.get(i).setImage(banner);
            }else if(getNotiList.get(i).getType().equalsIgnoreCase("property")){
                String thumbnail = getNofiRepo.getPropertyThumbnail(getNotiList.get(i).getRefID());
                if(thumbnail!=null)
                    getNotiList.get(i).setImage(thumbnail);
            }
        }
        pagination.setTotalItem(getNofiRepo.getNotiCount(userID));
        return getNotiList;
    }
}
