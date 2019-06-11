package com.century21.apexproperty.service.api_enable_email;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_enable_email.EnableEmailRepo;
import com.century21.apexproperty.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnableEmailServiceImpl implements EnableEmailService {
    @Autowired
    private EnableEmailRepo enableEmailRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public void enableEmail(String email, int code) {
        userRepo.removeCode();
        if (enableEmailRepo.enableEmail(email, code) < 1) throw new CustomRuntimeException(404, "code not match");
        else enableEmailRepo.removeVerifiesByEmail(email);
    }
}
