package com.meta.facebook;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("dev")
public class ApplicationConfig {

    @Bean
    //@Profile("dev")
    public MyFirstClass myFirstBean(){
        return new MyFirstClass("First Bean");
    }

    @Bean("bean2")
    public MyFirstClass mySecondBean(){
        return new MyFirstClass("Second Bean");
    }

    @Bean
    //@Primary
    public MyFirstClass myThirdBean(){
        return new MyFirstClass("Third Bean");
    }
}
