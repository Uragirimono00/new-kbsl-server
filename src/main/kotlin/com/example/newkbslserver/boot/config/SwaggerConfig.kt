package com.example.newkbslserver.boot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket? {
        val typeResolver: com.fasterxml.classmate.TypeResolver = com.fasterxml.classmate.TypeResolver()
        return Docket(DocumentationType.OAS_30)
            .securityContexts(Arrays.asList(securityContext())) //securityContext는 인증하는 방식을,
            .securitySchemes(Arrays.asList(apiKey()) as List<SecurityScheme>?) //그리고 ApiKey는 버튼을 클릭했을때 입력하는 값을 넣는 설정을 하는 것이다.
            .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404) 등의 노출 여부
            .apiInfo(apiInfo()) // Swagger UI 로 노출할 정보
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.kbsl.server")) // api 스펙이 작성되어 있는 패키지 (controller)
            .paths(PathSelectors.any()) // apis 에 위치하는 API 중 특정 path 를 선택
            .build()
    }


    private fun securityContext(): SecurityContext? {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    private fun defaultAuth(): List<SecurityReference>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Arrays.asList<SecurityReference>(SecurityReference("Authorization", authorizationScopes))
    }

    private fun apiKey(): ApiKey? {
        return ApiKey("Authorization", "Bearer", "header")
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title("KBSL API Document")
            .description(
                """한국 비트세이버 리그 API를 명세하는 문서
 해당 문서는 KBSL API를 설명하고 있음.
 Discord : Uragirimono#1234"""
            )
            .build()
    }

}