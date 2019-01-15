package com.century21.century21cambodia.repository.api_modify_event_status;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifyEventStatusRepo {
    @Update("UPDATE events set enable = #{status} " +
            "WHERE id=#{eventID}")
    int updateStatus(@Param("eventID") int eventID,@Param("status")boolean status);
}
