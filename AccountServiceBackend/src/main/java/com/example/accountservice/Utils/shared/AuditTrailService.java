package com.example.accountservice.Utils.shared;


import com.example.accountservice.Utils.shared.security.UserRequestContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class AuditTrailService {

    public static <T extends Audittrails> T POSTAudit(T data) {
        data.setPostedBy(UserRequestContext.getCurrentUser());
        data.setPostedFlag('Y');
        data.setPostedTime(LocalDateTime.now());
        data.setStatus("PENDING");
        return data;
    }
    public static <T extends Audittrails> T MODIFYAudit(T existing,T newdata) {
        newdata.setModifiedBy(UserRequestContext.getCurrentUser());
        newdata.setModifiedFlag('Y');
        newdata.setModifiedTime(LocalDateTime.now());
        newdata.setStatus("PENDING");
        newdata.setVerifiedFlag('N');
        newdata.setPostedBy(existing.getPostedBy());
        newdata.setPostedFlag(existing.getPostedFlag());
        newdata.setPostedTime(existing.getPostedTime());
        return newdata;
    }
    public static <T extends Audittrails> T VERIFYAudit(T data) {
        data.setVerifiedBy(UserRequestContext.getCurrentUser());
        data.setVerifiedFlag('Y');
        data.setVerifiedTime(LocalDateTime.now());
        return data;
    }
    public static <T extends Audittrails> T DELETEAudit(T data) {
        data.setDeletedBy(UserRequestContext.getCurrentUser());
        data.setDeletedFlag('Y');
        data.setDeletedTime(LocalDateTime.now());
        return data;
    }



}
