package com.codenameone.app.tracking;


/**
 * 
 *  @author shannah
 */
public interface AppTrackingNative {

	public void requestTrackingAuthorization(int callbackId);

	public int getTrackingAuthorizationStatus();
}
