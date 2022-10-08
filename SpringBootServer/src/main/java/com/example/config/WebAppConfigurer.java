package com.example.config;

import com.example.interceptor.SysInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2021-01-25 21:54
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Value("${StaticRootPath}")
    private String StaticRootPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600);
    }

    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns=new String[]{"/adminLogin","/product/**","/bigType/**","/users/wxlogin","/weixinpay/**","/image/**","/webjars/**","/swagger-resources",
                "/publish/get_random"
        };
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/productParaImgs/**").addResourceLocations("file:"+StaticRootPath+"/productParaImgs/");
        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:"+StaticRootPath+"/swiperImgs/");
        registry.addResourceHandler("/image/bigType/**").addResourceLocations("file:"+StaticRootPath+"/bigTypeImgs/");
        registry.addResourceHandler("/image/product/**").addResourceLocations("file:"+StaticRootPath+"/productImgs/");
        registry.addResourceHandler("/image/productSwiperImgs/**").addResourceLocations("file:"+StaticRootPath+"/productSwiperImgs/");
        registry.addResourceHandler("/image/productIntroImgs/**").addResourceLocations("file:"+StaticRootPath+"/productIntroImgs/");
        registry.addResourceHandler("/image/productParaImgs/**").addResourceLocations("file:"+StaticRootPath+"/productParaImgs/");
        registry.addResourceHandler("/uploads/audios/**").addResourceLocations("file:"+StaticRootPath+"/audios/");
        registry.addResourceHandler("/file/defaults/**").addResourceLocations("file:"+StaticRootPath+"/defaults/");
    }



}
