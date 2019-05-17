package com.cloud.client.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

@ControllerAdvice
public class SentryExceptionHandler {
    
    /** 
     * 全局处理Exception 
     * 提交错误到sentry
     * @param ex 
     * @param req 
     * @return 
     */
    @ExceptionHandler(value = { Exception.class })
    public Exception handleOtherExceptions(final Exception ex, final WebRequest req) {
        Sentry.capture(ex);
        return ex;
    }
    
    /**
     * An example method that throws an exception.
     */
    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }
    
    /**
     * Examples using the (recommended) static API.
     */
    void logWithStaticAPI() {
        // Note that all fields set on the context are optional. Context data is copied onto
        // all future events in the current context (until the context is cleared).
        
        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());
        
        // Set the user in the current context.
        Sentry.getContext().setUser(new UserBuilder().setEmail("hello@sentry.io").build());
        
        // Add extra data to future events in this context.
        Sentry.getContext().addExtra("extra", "thing");
        
        // Add an additional tag to future events in this context.
        Sentry.getContext().addTag("tagName", "tagValue");
        
        /*
        This sends a simple event to Sentry using the statically stored instance
        that was created in the ``main`` method.
        */
        Sentry.capture("This is a test");
        
        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }
    
    /**
     * Examples that use the SentryClient instance directly.
     */
    void logWithInstanceAPI(SentryClient sentry) {
        // Retrieve the current context.
        Context context = sentry.getContext();
        
        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());
        
        // Set the user in the current context.
        context.setUser(new UserBuilder().setEmail("hello@sentry.io").build());
        
        // This sends a simple event to Sentry.
        sentry.sendMessage("This is a test");
        
        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry.
            sentry.sendException(e);
        }
    }
    
}
