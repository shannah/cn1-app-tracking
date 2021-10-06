/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codenameone.app.tracking;

import com.codename1.system.NativeLookup;
import com.codename1.util.AsyncResource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shannah
 */
public class AppTracking {
    private static final Object lock = new Object();
    private static int nextResponseId;
    private static List<AppTrackingResponse> pendingResponses;
    
    private static AppTrackingResponse newAppTrackingResponse() {
        synchronized(lock) {
            AppTrackingResponse response = new AppTrackingResponse(nextResponseId++);
            if (pendingResponses == null) {
                pendingResponses = new ArrayList<>();
            }
            pendingResponses.add(response);
            return response;
        }
    }
    
    private static AppTrackingResponse findResponseById(int id) {
        synchronized(lock) {
            if (pendingResponses != null) {
                for (AppTrackingResponse response : pendingResponses) {
                    if (response.id == id) return response;
                }
            }
        }
        return null;
    }
    
    public static enum AuthorizationStatus {
        NotDetermined,
        Restricted,
        Denied,
        Authorized
    }
    
    public static class AppTrackingResponse extends AsyncResource<AuthorizationStatus> {
        private final int id;
        private AppTrackingResponse(int id) {
            this.id = id;
        }

        @Override
        public void complete(AuthorizationStatus value) {
            synchronized(lock) {
                if (pendingResponses != null) {
                    pendingResponses.remove(this);
                }
            }
            super.complete(value);
        }

        @Override
        public void error(Throwable t) {
            synchronized(lock) {
                if (pendingResponses != null) {
                    pendingResponses.remove(this);
                }
            }
            super.error(t);
        }        
 
    }
    
    static void requestTrackingAuthorizationCallback(int callbackId, int result) {
        AppTrackingResponse response = findResponseById(callbackId);
        if (response == null) {
            throw new IllegalStateException("requestTrackingAuthorizationErrorCallback fired with id "+callbackId+", but not found");
        }
        response.complete(AuthorizationStatus.values()[result]);
    }
    
    static void requestTrackingAuthorizationErrorCallback(int callbackId, String message, int code) {
        AppTrackingResponse response = findResponseById(callbackId);
        if (response == null) {
            throw new IllegalStateException("requestTrackingAuthorizationErrorCallback fired with id "+callbackId+", but not found");
        }
        response.error(new RuntimeException(message));
        
    }
    
    public static AppTrackingResponse requestTrackingAuthorization() {
        AppTrackingResponse response = newAppTrackingResponse();
        
        try {
            AppTrackingNative n = NativeLookup.create(AppTrackingNative.class);
            if (n.isSupported()) {
                n.requestTrackingAuthorization(response.id);
            } else {
                response.error(new RuntimeException("AppTracking API not supported on this platform."));
            }
        } catch (Exception ex) {
            response.error(ex);
        }
        return response;
    }
    
    public static AuthorizationStatus getTrackingAuthorizationStatus() {
        try {
            AppTrackingNative n = NativeLookup.create(AppTrackingNative.class);
            if (n.isSupported()) {
                return AuthorizationStatus.values()[n.getTrackingAuthorizationStatus()];
            } else {
                throw new RuntimeException("AppTracking API not supported on this platform");
            }
        } catch (Exception ex) {
            if (ex instanceof RuntimeException) {
                throw ex;
            }
            throw new RuntimeException("AppTracking API not supported on this platform");
        }
    }
    
    
    
    public static boolean isSupported() {
        try {
            AppTrackingNative n = NativeLookup.create(AppTrackingNative.class);
            if (n.isSupported()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    
}
