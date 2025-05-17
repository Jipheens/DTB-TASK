package com.example.customerservice.Utils.shared.security;

import com.example.customerservice.Utils.shared.HeaderRequestInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class RequestInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String username = httpRequest.getHeader("UserName");
        String authorization = httpRequest.getHeader("authorization");
        String departmentcode = httpRequest.getHeader("DepartmentCode");
        String branchcode = httpRequest.getHeader("BranchCode");
        String costcentercode = httpRequest.getHeader("CostCenterCode");
        String membercode = httpRequest.getHeader("MemberCode");

        username = username == null || username.trim().isEmpty() ? "Guest" : username;
        authorization = authorization == null || authorization.trim().isEmpty() ? "Guest" : authorization;
        departmentcode = departmentcode == null || departmentcode.trim().isEmpty() ? "Guest" : departmentcode;
        branchcode = branchcode == null || branchcode.trim().isEmpty() ? "Guest" : branchcode;
        costcentercode = costcentercode == null || costcentercode.trim().isEmpty() ? "Guest" : costcentercode;
        membercode = membercode == null || membercode.trim().isEmpty() ? "Guest" : membercode;
        HeaderRequestInfo headerRequestInfo = new HeaderRequestInfo();
        headerRequestInfo.setDepartmentcode(departmentcode);
        headerRequestInfo.setBranchcode(branchcode);
        headerRequestInfo.setCostcentercode(costcentercode);
        headerRequestInfo.setMembercode(membercode);
        UserRequestContext.setCurrentUser(username);
        HeaderRequestContext.setCurrentRequest(headerRequestInfo);
        log.info(" Request Made by: " +username+", Cost Center Code: "+costcentercode+", Member Code: "+membercode+", Department: "+departmentcode+", Branch code: "+branchcode+", Request Method: "+httpRequest.getMethod() + ", Requested URI: " +httpRequest.getRequestURI() + ", at: " + LocalDateTime.now());
        chain.doFilter(request, response);
    }
}
