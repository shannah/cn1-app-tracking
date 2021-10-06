#import <Foundation/Foundation.h>

@interface com_codenameone_app_tracking_AppTrackingNativeImpl : NSObject {
}

-(void)requestTrackingAuthorization:(int)param;
-(int)getTrackingAuthorizationStatus;
-(BOOL)isSupported;
@end
