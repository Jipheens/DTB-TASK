package com.example.customerservice.Utils.shared.security;


import com.example.customerservice.Utils.shared.HeaderRequestInfo;

public class HeaderRequestContext {
    private static ThreadLocal<HeaderRequestInfo> currentRequest = new InheritableThreadLocal<>();
    public static HeaderRequestInfo getCurrentRequest() {
        return currentRequest.get();
    }
    public static void setCurrentRequest(HeaderRequestInfo headerRequestInfo) {
        currentRequest.set(headerRequestInfo);
    }
    public static void clear() {
        currentRequest.set(null);
    }
}
