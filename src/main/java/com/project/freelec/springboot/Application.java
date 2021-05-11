package com.project.freelec.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Jpa Auditing 활성화 ( Jpa Auditing 어노테이션들을 모두 활성화 할 수 있도록 )
@SpringBootApplication // 스프링 부트의 자동 설정, Bean 읽기와 생성을 자동으로설정해줌 . 이 어노테이션이 있는 위치부터 설정 읽어감(프로젝트 최 상단 )
public class Application {
    public static void main(String[] args){
        /*
         * SpringApplication.run으로 인해 내장 WAS(Web application server)실행
         * 내장 WAS란 별도로 외부에 WAS를 두지 않고 애플리케이션을 실행할 때 WAS를 실행하는것을 이야기함.
         * 서버에 톰캣을 설치할 필요없이 스프링 부트로 만들어진 JAR파일을 실행하면됨.(jar:실행가능한 java 패키징 파일)
         */
        SpringApplication.run(Application.class,args);
    }
}
