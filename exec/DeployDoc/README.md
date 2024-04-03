## 서비스 버전 정보
* jdk 17
* SpringBoot 3.2.3
* Vue 3
* TypeScript
* MySQL
* Redis
* EC2
* node.js 20.10
* Jenkins
* Nginx
* Docker

<br><br><br>

## Nginx Configuration
### nginx - proxy.conf
```
upstream backend {
        server localhost:8081;
}

server {
        server_name j10a201.p.ssafy.io;


        root /home/ubuntu/dongdongzoo/frontend/dist;
        index index.html;

        location / {
                try_files $uri $uri/ =404 /index.html;
        }

        location ^~ /wasApi {
                proxy_pass http://backend;
        }

        location /auth {
                proxy_pass http://backend;

                proxy_set_header Host $http_host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
        }

        location /songs {
                proxy_pass http://backend;

                proxy_set_header   Host              $http_host;
                proxy_set_header   X-Real-IP         $remote_addr;
                proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
                proxy_set_header   X-Forwarded-Proto $scheme;
        }

        location /likes {
                proxy_pass http://backend;

                proxy_set_header   Host              $http_host;
                proxy_set_header   X-Real-IP         $remote_addr;
                proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
                proxy_set_header   X-Forwarded-Proto $scheme;
        }

        location /stories {
                proxy_pass http://backend;

                proxy_set_header   Host              $http_host;
                proxy_set_header   X-Real-IP         $remote_addr;
                proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
                proxy_set_header   X-Forwarded-Proto $scheme;
        }

        location /swagger-ui {
                proxy_pass http://backend;

                proxy_set_header   Host              $http_host;
                proxy_set_header   X-Real-IP         $remote_addr;
                proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
                proxy_set_header   X-Forwarded-Proto $scheme;
        }

        location /v3 {
                proxy_pass http://backend;

                proxy_set_header   Host              $http_host;
                proxy_set_header   X-Real-IP         $remote_addr;
                proxy_set_header   X-Forwarded-For   $proxy_add_x_forwarded_for;
                proxy_set_header   X-Forwarded-Proto $scheme;
        }

        location /assets/song   {
                alias /home/ubuntu/dongdongzoo/frontend/src/assets/song;
        }

        client_max_body_size 100M;
        proxy_connect_timeout 180;
        proxy_send_timeout 180;
        proxy_read_timeout 180;
        send_timeout 180;

    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/{서버 URI}/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/{서버 URI}/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}

server {
    if ($host = {서버 URI}) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


        listen 80;
        server_name {서버 URI};
    return 404; # managed by Certbot


}

```
_________

<br>
<br>

## Jenkins Pipelie
### Jenkins SpringBoot Pipeline
```
pipeline {
    agent any
    tools {
        gradle 'Gradle-8.5'
    }
    environment {
        releaseServerAccount = 'ubuntu'
        releaseServerUri = '서버 URI'
        releasePort = '8081'
        SERVER_NAME = 'server-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                // GitLab 저장소에서 'master' 브랜치의 소스 코드를 체크아웃합니다.
                git branch: 'master', credentialsId: 'smink95', url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22A201.git'
                // GitLab 저장소에서 'backend' 브랜치의 소스 코드를 체크아웃합니다.
                // git branch: 'backend', credentialsId: 'smink95', url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22A201.git'
            }
        }
        stage('Spring Boot Build') {
            steps {
                // 서버 폴더로 이동합니다.
                dir('server') {
                    sh 'gradle init'
                    // Gradle을 사용하여 서버 프로젝트 빌드
                    withGradle {
                        sh 'gradle clean build'
                    }
                    // sh 'gradle clean build'
                }
            }
        }
        stage('Deploy') {
            steps {
                sshagent(credentials: ['dongdongzoo-server']) {
                    // // 이전 서버 인스턴스 확인 및 종료
                    // sh "ssh $releaseServerAccount@$releaseServerUri 'sudo pkill -f $SERVER_NAME' || true"
                    // 빌드된 스프링 부트 애플리케이션을 서버에 복사하여 실행
                    sh "scp /var/jenkins_home/workspace/Spring_pipeline/server/build/libs/$SERVER_NAME $releaseServerAccount@$releaseServerUri:/home/ubuntu/dongdongzoo"
                    // // 서버에서 애플리케이션 실행
                    // sh "ssh $releaseServerAccount@$releaseServerUri 'sudo nohup java -jar /home/ubuntu/dongdongzoo/$SERVER_NAME --server.port=$releasePort &'"
                    //Server Deploy.sh 실행
                    sh "ssh $releaseServerAccount@$releaseServerUri 'sudo /home/ubuntu/dongdongzoo/ServerDeploy.sh'"
                }
            }
        }
    }
}

```
### Jenkins Vue Pipeline
```
pipeline {
    agent any
    
    tools {
        nodejs 'Nodejs'
    }

    environment {
        releaseServerAccount = 'ubuntu'
        releaseServerUri = '서버 URI'
        releasePort = '3000'
    }

    stages {
        stage('기존 프로젝트 파일 삭제'){
            steps {
                sh "rm -r /var/jenkins_home/workspace/Vue_pipeline/*"
            }
        }
        stage('Checkout') {
            steps {
                // // GitLab 저장소에서 'master' 브랜치의 소스 코드를 체크아웃합니다.
                git branch: 'master', credentialsId: 'smink95', url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22A201.git'
                // GitLab 저장소에서 'frontend' 브랜치의 소스 코드를 체크아웃합니다.
                // git branch: 'frontend', credentialsId: 'smink95', url: 'https://lab.ssafy.com/s10-ai-image-sub2/S10P22A201.git'
            }
        }
        stage('Vue Project Build') {
            steps {
                // 서버 폴더로 이동합니다.
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
        stage('Deploy') {
            steps {
                sshagent(credentials: ['dongdongzoo-server']) {
                    sh "ssh $releaseServerAccount@$releaseServerUri 'sudo rm -r /home/ubuntu/dongdongzoo/frontend'"
                    sh "scp -r /var/jenkins_home/workspace/Vue_pipeline/frontend/ $releaseServerAccount@$releaseServerUri:/home/ubuntu/dongdongzoo/frontend"
                    sh "ssh $releaseServerAccount@$releaseServerUri 'sudo /home/ubuntu/dongdongzoo/deploy.sh'"
                }
            }
        }
    }
}

```
_________ 

<br><br><br>

## Deploy Shell Script
### ServerDeploy.sh
SpringBoot Server deploy shell script
```
#!/bin/bash


echo ">SpringBoot Server 재시작"
echo ">실행중인 Server 종료"
sudo pkill -f server-0.0.1-SNAPSHOT.jar
sleep 3

echo "> sudo nohup java -jar -Dserver.port=8081 Server-0.0.1-SNAPSHOT.jar &"
sudo nohup java -jar -Dserver.port=8081 /home/ubuntu/dongdongzoo/server-0.0.1-SNAPSHOT.jar 1>output.log 2>error.log &

```
### deploy.sh
Vue Client deploy shell script
```
#!/bin/bash

echo ">Nginx 재배포"
sudo service nginx stop
sleep 1
sudo service nginx start
sleep 1
sudo service nginx status

```
_________ 

<br><br><br>

## Project Environment Variable
### SpringBoot : application.properties
```
spring.datasource.url=jdbc:mysql://{도메인명}/{스키마}
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.data.redis.host=localhost
spring.data.redis.port=6379

# temp
jwt.secret={난수 생성 키}
jwt.grant_type=Bearer
jwt.expiration.access=1800000
jwt.expiration.refresh=86400000

service.url={서비스url}
```
_________ 
### Vue : .env
```
VITE_API_URL={서버 URL}
# VITE_API_URL=http://localhost:8080

```
### Vue : .env.development
```
VITE_ASSET_PATH="/src"
```
### Vue : .env.production
```
VITE_ASSET_PATH=""
```
_________ 

<br><br><br>

## 사용 포트 정보들
```
8080 : Jenkins
8081 : SpringBoot
3000 : Vue
3306 : MySQL
6379 : Redis
443 : SSH
```

