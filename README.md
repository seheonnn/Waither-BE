### 나만의 기상 비서, Waither

# ✏️ 프로젝트 요약
- 백엔드를 담당하여 사용자 데이터 설정, 설문, 알람 생성 등에 참여하였습니다.
- 기상청 OpenAPI를 이용하여 사용자에게 메인 화면과 알람을 통해 날씨 정보를 제공하는 iOS 어플리케이션입니다.
- 간단한 설문조사와 함께 사용자 맞춤 설정이 가능하여 사용자의 필요에 따라 각종 알람들을 On/Off 할 수 있습니다.
- 사용자 체감의 매우더움, 더움, 좋음, 추움, 매우추움 온도를 설정하면 해당 온도와 비슷한 날씨에 알람을 받을 수 있습니다.
- 사용자가 설정한 풍속 이상의 바람이 부는 날에는 풍속 관련 알람을 받을 수 있습니다.
- 특별한 기상 특보가 있는 날에는 해당 알람도 받을 수 있습니다.

# 🎯 프로젝트 목표 및 역할
- JWT 토큰 및 Spring Security 학습
- 기상청 OpenApi 이용
- Swagger 및 Postman을 사용한 Api 테스트
- [Amazon AWS, RDS](https://aws.amazon.com/ko/)를 이용한 배포
- UMC 3rd 데모데이 최우수상

# 🛠️ 사용 기술
Front-End : <img src="https://img.shields.io/badge/Swift-FA7343?style=for-the-flat&logo=swift&logoColor=white">
<br>
Back-End : <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-flat&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-flat&logo=spring-boot"> <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-flat&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-flat&logo=amazonaws&logoColor=white">
# 📺 프로젝트 화면 구성

| 메인 화면 | 설문 화면 |
| ------------ | ------------ |
| <img width="500" alt="7" src="https://github.com/seheonnn/Waither-BE/assets/101795921/a4f9c513-2e4a-4413-a41b-bb9c00e28e60"> | <img width="500" alt="2" src="https://github.com/seheonnn/Waither-BE/assets/101795921/af801346-d0ba-4655-bcc9-d1a05f921c76"> |

| 설정 화면 | 알림 설정 | 사용자 맞춤 설정 | 풍량, 풍속 설정 |
| ------------ | ----------- | ----------- | ----------- |
| <img width="500" alt="3" src="https://github.com/seheonnn/Waither-BE/assets/101795921/c86712db-3e8c-4a58-8b2d-91a6ee1a124c"> | <img width="500" alt="4" src="https://github.com/seheonnn/Waither-BE/assets/101795921/e3161e62-47d6-497f-8f3c-71a72b43385a"> | <img width="500" alt="5" src="https://github.com/seheonnn/Waither-BE/assets/101795921/d7c4d0de-2f96-441f-9781-47d3251db31f"> | <img width="500" alt="6" src="https://github.com/seheonnn/Waither-BE/assets/101795921/7e97ac00-6d7b-4b7c-85d8-1dbc7ccbdc31"> |

| ERD | API |
| ------------ | ------------ |
| ![ERD](https://github.com/seheonnn/Waither-BE/assets/101795921/9685d057-1388-4a6a-88e3-af3128286c82) | ![api 명세서](https://github.com/seheonnn/Waither-BE/assets/101795921/cd36b6a3-f7c0-4305-b1c7-5858e996e717) |

# 📢 프로젝트 홍보 포스터

![웨이더 배너 디자인](https://user-images.githubusercontent.com/69234788/223329313-1132cb19-7567-405f-babc-52426897f16f.jpeg)
![웨이더 포스터 디자인](https://user-images.githubusercontent.com/69234788/223329302-4eb8869a-9327-4f2d-a9c7-1d78cd31871e.jpeg)

# Waither-BE
## Commit Message Convention

|    Type     | Description  |
|:-----------:|---|
|   `Feat`    | 새로운 기능 추가 |
|    `Fix`    | 버그 수정 |
|    `Ci`     | CI관련 설정 수정 |
|   `Docs`    | 문서 (문서 추가, 수정, 삭제) |
|   `Style`   | 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없는 경우) |
| `Refactor`  | 코드 리팩토링 |
|   `Test`    | 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없는 경우) |
|   `Chore`   | 기타 변경사항 (빌드 스크립트 수정 등) |

Branches
- userManagement : log-in, log-out etc..
- userData : user data managing
- open api 

Dependencies
- JPA 
- MySQL Driver
- Spring Security
- DevTools
