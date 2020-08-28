package com.dayuwuxian.metric.autoconfigure;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.Nullable;
import io.micrometer.spring.web.servlet.DefaultWebMvcTagsProvider;
import io.micrometer.spring.web.servlet.WebMvcTags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@NonNullApi
public class CustomWebMvcTagsProvider extends DefaultWebMvcTagsProvider {
    @Override
    public Iterable<Tag> httpLongRequestTags(@Nullable HttpServletRequest request, @Nullable Object handler) {
        String degradeLevelKey = "degrade_level";
        return Arrays.asList(WebMvcTags.method(request),
                WebMvcTags.uri(request, null),
                request == null || request.getHeader(degradeLevelKey) == null ?
                        Tag.of(degradeLevelKey, "0") :
                        Tag.of("degrade_level", request.getHeader(degradeLevelKey))
        );
    }

    @Override
    public Iterable<Tag> httpRequestTags(@Nullable HttpServletRequest request,
                                         @Nullable HttpServletResponse response,
                                         @Nullable Object handler,
                                         @Nullable Throwable ex) {
        String degradeLevelKey = "degrade_level";
        return Arrays.asList(
                WebMvcTags.method(request),
                WebMvcTags.uri(request, response),
                WebMvcTags.exception(ex),
                WebMvcTags.status(response),
                request == null || request.getHeader(degradeLevelKey) == null ?
                        Tag.of(degradeLevelKey, "0") :
                        Tag.of("degrade_level", request.getHeader(degradeLevelKey))
        );
    }
}
