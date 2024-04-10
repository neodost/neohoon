package com.neohoon.api.config

import org.springframework.core.MethodParameter
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order
import org.springframework.data.web.SortArgumentResolver
import org.springframework.util.StringUtils
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer


class CustomSortArgumentResolver : SortArgumentResolver {

    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return Pageable::class.java == methodParameter.parameterType
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Sort {
        val sortParameter = webRequest.getParameter("sort")

        val orders: MutableList<Sort.Order> = ArrayList<Sort.Order>()
        if (StringUtils.hasText(sortParameter)) {
            val sortProperties = sortParameter!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            for (sortProp in sortProperties) {
                val sortParams = sortProp.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (sortParams.size == 2) {
                    val property = sortParams[0]
                    val direction = sortParams[1]
                    orders.add(Order(Sort.Direction.fromString(direction), property))
                }
            }
        }

        return if (orders.isEmpty()) Sort.unsorted() else Sort.by(orders)
    }



}