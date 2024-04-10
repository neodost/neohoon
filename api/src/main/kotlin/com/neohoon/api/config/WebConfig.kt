package com.neohoon.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.data.web.SortHandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver?>) {
        val sortArgumentResolver = SortHandlerMethodArgumentResolver()
        sortArgumentResolver.propertyDelimiter = "-"

        val pageableArgumentResolver = PageableHandlerMethodArgumentResolver(CustomSortArgumentResolver())
        pageableArgumentResolver.setOneIndexedParameters(true)
        pageableArgumentResolver.setMaxPageSize(500)
        pageableArgumentResolver.setFallbackPageable(PageRequest.of(0, 10))
        pageableArgumentResolver.setQualifierDelimiter(",")
        argumentResolvers.add(pageableArgumentResolver)
    }

}