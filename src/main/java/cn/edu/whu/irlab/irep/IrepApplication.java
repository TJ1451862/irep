package cn.edu.whu.irlab.irep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.whu.irlab.irlep.mapper")
public class IrepApplication {

    public static void main(String[] args) {

        SpringApplication.run(IrepApplication.class, args);
    }

}
