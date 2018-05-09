package org.nix.movieticketingsystem;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by zhangpe0312@qq.com on 2018/5/9.
 */
@Configuration
@ConditionalOnClass({FastJsonHttpMessageConverter.class}) //1
@ConditionalOnProperty(//2
        name = {"spring.http.converters.preferred-json-mapper"},
        havingValue = "fastjson",
        matchIfMissing = true
)
public class FastJson2HttpMessageConverterConfiguration {
    protected FastJson2HttpMessageConverterConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})//3
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();//4
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteClassName,
                SerializerFeature.WriteMapNullValue
        );
        ValueFilter valueFilter = new ValueFilter() {//5
            //o 是class
            //s 是key值
            //o1 是value值
            public Object process(Object o, String s, Object o1) {
                if (null == o1){
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);

        converter.setFastJsonConfig(fastJsonConfig);

        return converter;
    }
}
