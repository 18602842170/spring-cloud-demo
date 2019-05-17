package com.cloud.client.common;

import java.util.Map;

import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;

public class SentryUtil {
    
    public static SentryClient getSentryClient() {
        return SentryClientFactory.sentryClient();
    }
    
    /**
     * 发送异常信息
     * @param throwable 抛出的异常
     * @param breadcrumbs 异常抛出的位置信息
     * @param extras 导致异常的数据信息
     * @param tags 异常标签
     * @return boolean 是否发送成功
     */
    public static Boolean sendErrorMessage(Throwable throwable, String[] breadcrumbs, Map<String, Object> extras, Map<String, String> tags) {
        
        try {
            SentryClient sentry = SentryClientFactory.sentryClient();
            Context context = sentry.getContext();
            setBreadcrumbs(context, breadcrumbs);
            setExtras(context, extras);
            setTags(context, tags);
            
            sentry.sendException(throwable);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    private static void setExtras(Context context, Map<String, Object> extras) {
        if (extras != null) {
            for (String key : extras.keySet()) {
                context.addExtra(key, extras.get(key));
            }
        }
    }
    
    private static void setBreadcrumbs(Context context, String[] breadcrumbs) {
        if (breadcrumbs != null) {
            for (String breadcrumb : breadcrumbs) {
                context.recordBreadcrumb(new BreadcrumbBuilder().setMessage(breadcrumb).build());
            }
        }
    }
    
    private static void setTags(Context context, Map<String, String> tags) {
        if (tags != null) {
            for (String key : tags.keySet()) {
                context.addTag(key, "" + tags.get(key));
            }
        }
    }
    
}
