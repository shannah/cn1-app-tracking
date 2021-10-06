/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codenameone.app.tracking;

import com.codename1.system.NativeInterface;

/**
 *
 * @author shannah
 */
public interface AppTrackingNative extends NativeInterface {
    
    public void requestTrackingAuthorization(int callbackId);
    public int getTrackingAuthorizationStatus();
    
}
