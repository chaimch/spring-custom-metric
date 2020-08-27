package com.dayuwuxian.metric.autoconfigure;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomWebMvcTagsProvider extends DefaultWebMvcTagsProvider {
    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
        Tags tags = (Tags) super.getTags(request, response, handler, exception);
        String degradeLevelKey = "degrade_level";
        String degradeLevelVal = request.getHeader("degrade_level");
        if (degradeLevelVal == null){
            degradeLevelVal = "0";
        }
        tags = tags.and(degradeLevelKey, degradeLevelVal);
        return tags;
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        Tags tags = (Tags) super.getLongRequestTags(request, handler);
        String degradeLevelKey = "degrade_level";
        String degradeLevelVal = request.getHeader("degrade_level");
        if (degradeLevelVal == null){
            degradeLevelVal = "0";
        }
        tags = tags.and(degradeLevelKey, degradeLevelVal);
        return tags;
    }
}