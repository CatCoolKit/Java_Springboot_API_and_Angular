package com.meta.facebook;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@PropertySources({
        @PropertySource("classpath:custom.properties"),
        @PropertySource("classpath:custom-file-2.properties")
})
public class MyFirstService {

//    @Autowired
//    @Qualifier("bean2")
    private MyFirstClass myFirstClass;
    private Environment environment;

    @Value("${my.custom.property}")
    private String customProperty;
    @Value("${my.prop}")
    private String customPropertyFromAnotherFile;
    @Value("${my.prop.2}")
    private String customPropertyFromAnotherFile2;
    @Value("${my.custom.property.int}")
    private Integer customPropertyInt;

    public MyFirstService(
            @Qualifier("myFirstBean") MyFirstClass myFirstClass) {
        this.myFirstClass = myFirstClass;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getJavaVersion(){
        return environment.getProperty("java.version");
    }

    public String getOsName(){
        return environment.getProperty("os.name");
    }

    public String readProp(){
        return environment.getProperty("my.custom.property");
    }
//    public void injectDependencies(
//            @Qualifier("bean2") MyFirstClass myFirstClass){
//        this.myFirstClass = myFirstClass;
//    }

//    @Autowired
//    public void setMyFirstClass(
//            @Qualifier("bean2") MyFirstClass myFirstClass){
//        this.myFirstClass = myFirstClass;
//    }

    public String tellAStory(){
        return "the dependency is saying: " + myFirstClass.sayHello();
    }

}
