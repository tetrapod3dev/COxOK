![logo](assets/코옥_소형_로고.png)

<center><h2>A104 - 씨없는 수박</h2></center>

<center>
    <img src="https://img.shields.io/badge/platform-web-green">
    <img src="https://img.shields.io/static/v1.svg?label=&message=Vue&style=flat-square&logo=Vue.js&logoColor=white&color=42b883">
    <img src="https://img.shields.io/badge/framework-spring boot-blue">
    <img src="https://img.shields.io/badge/database-MariaDB-9cf">
    <img src="https://img.shields.io/badge/server-AWS-yellow">
    <img src="https://img.shields.io/badge/language-java%2C javascript-yellowgreen">
    <img src="https://img.shields.io/badge/swagger-valid-brightgreen">
</center>


<center>
  <a href="http://i3a104.p.ssafy.io">
    <h3>
      CO~OK 바로가기
    </h3>
  </a>
</center>
<br>
<center>
    사용자들이 자유롭게 레시피를 등록하여 다른 사용자와 공유할 수 있습니다.<br>
    함께 요리하는 기능인 코옥하기를 통해 기존에는 시도하지 못했던 레시피를 시도하며,<br>
    요리를 해본 적 없는 사용자들도 요리에 자신감을 가질 수 있습니다. 
</center>

[CO~OK wiki](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13a104/wikis/Home)




## :point_right: 프로젝트 기술 특이점

* JWT 토큰을 활용한 로그인 기능
* vue-charts를 사용한 데이터 시각화
* 카카오 지도 API를 통한 소모임 위치 검색 및 지도 표시(API)
* 유튜브 API를 활용한 관련 유튜브 영상 저장 및 출력




## :closed_book: 프로젝트 주재료

![image-20200821094723586](assets/web_stack.png)



## :package: 프로젝트 추가 재료

### frontend

|          Package          |    Version     |                      Comment                      |
| :-----------------------: | :------------: | :-----------------------------------------------: |
|        vue-router         |     3.2.0      |      요청 URL에 따라 브라우저에서 돔을 변경       |
|           vuex            |     3.5.1      |            상태 관리 패턴 + 라이브러리            |
|           axios           |     0.19.2     |  REST API 서버로부터 데이터를 받아오기 위해 사용  |
| bootstrap / bootstrap-vue | 4.5.0 / 2.15.0 | 프론트엔드 디자인을 효율적으로 진행하기 위해 사용 |
|          vuetify          |     2.3.8      |                Calendar 요소 사용                 |
|  chart.js / vue-chartjs   | 2.9.3 / 3.5.0  |      데이터를 시각화하여 보여주기 위해 사용       |
|       scrollmonitor       |     1.2.4      |          무한스크롤을 구현하기 위해 사용          |
|          lodash           |    4.17.19     |     요리대전의 순서를 랜덤으로 섞기위해 사용      |

### backend

| Package | Version | Comment |
| :--: | :--: | :--- |
| OpenJDK - zulu |  8.33   | 프로젝트의 Java vendor로써 사용 |
|   SpringBoot   |  2.3.1  | Java의 서버 개발 환경을 구성하기 위해 사용 |
| JWT | 0.9.1 | 클라이언트와 서버의 stateless 환경 구성을 위해 사용 |
|    MariaDB     | 10.5.4  | 프로젝트의 DB vendor로써 사용 |
|    MyBatis     |  2.1.2  | 서버와 DB의 연동을 위해 사용 |
|     Lombok     | 1.18.10 | Annotation을 이용한 코드 생성으로 가독성 & 유지 보수를 위해 사용 |
|    Swagger     |  2.9.2  | API 스펙 문서의 자동화를 위해 사용 |
|     Nginx     | 1.14.0  | 서버 배포를 용이하게 하기 위해 사용 |
| pm2 | 4.4.0 | 서버의 무중단 배포를 위해 사용 |



## :cookie: 프로젝트 레시피

### Preparation

- Please must install Java 1.8  or even higher version

- install Node.js / NPM

- Clone Repository

    ```
    git clone https://lab.ssafy.com/s03-webmobile1-sub3/s03p13a104.git
      
    cd s03p13a104
    ```

### Installation

- Build front-end environment & run front-end server

    ```
    cd s03p13a104/frontend
    
    yarn install 
    
    yarn serve --port 3000
    ```

- Build back-end & run back-end server

  ``` 
  mvn clean package
  
  java -jar target/cookblog-0.0.1-SNAPSHOT.war
  ```

- Use Nginx & pm2

  ``` 
  pm2 start co-ok
  ```

  



## :globe_with_meridians: 지원 브라우저

| <img width="50" src="https://simpleicons.org/icons/googlechrome.svg" alt="Chrome" /> |
| ------------------------------------------------------------ |
| Latest                                                       |



## :two_men_holding_hands: 요리사들

* **박태록** - *PM, frontend* - [sdf7575](https://lab.ssafy.com/sdf7575)
* **권민** - *backend* - [minkwon4](https://lab.ssafy.com/minkwon4)
* **이동혁** - *frontend* - [lee33843](https://lab.ssafy.com/lee33843)
* **장윤정** - *backend* - [wkddbswjd325](https://lab.ssafy.com/wkddbswjd325)
* **최원대** - *backend* - [ryunhoo123](https://lab.ssafy.com/ryunhoo123)



### :star: 브랜치 전략

### branch

```bash
master -> developer -> frontend/backend -> frontend-feature/backend-feature
```

### merge

```bash
merge하기 전에 현재 작업 진행 상황 공유
merge 후 브랜치 지우기
```

### commit

```bash
UPDATE/ADD/FIX | feature
```



### :calendar: 개발 일정

![image-20200821094807921](assets/working_flow1.png)

![image-20200821095620235](assets/working_flow2.png)



