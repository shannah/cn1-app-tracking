package com.codenameone.app.tracking;


/**
 * 
 *  @author shannah
 */
public class AppTracking {

	public AppTracking() {
	}

	public static AppTracking.AppTrackingResponse requestTrackingAuthorization() {
	}

	public static AppTracking.AuthorizationStatus getTrackingAuthorizationStatus() {
	}

	public static boolean isSupported() {
	}

	public static final class AuthorizationStatus {


		public static final AppTracking.AuthorizationStatus NotDetermined;

		public static final AppTracking.AuthorizationStatus Restricted;

		public static final AppTracking.AuthorizationStatus Denied;

		public static final AppTracking.AuthorizationStatus Authorized;

		public static AppTracking.AuthorizationStatus[] values() {
		}

		public static AppTracking.AuthorizationStatus valueOf(String name) {
		}
	}

	public static class AppTrackingResponse {


		@java.lang.Override
		public void complete(AppTracking.AuthorizationStatus value) {
		}

		@java.lang.Override
		public void error(Throwable t) {
		}
	}
}
