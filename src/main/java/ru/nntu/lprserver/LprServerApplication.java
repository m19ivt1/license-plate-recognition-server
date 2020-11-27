package ru.nntu.lprserver;

import com.openalpr.jni.AlprException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LprServerApplication {

    public static void main(String[] args) throws AlprException {
        SpringApplication.run(LprServerApplication.class, args);
    }

}
