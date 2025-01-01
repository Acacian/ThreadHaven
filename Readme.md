### 개인 프로젝트입니다. 실제 배포도 할 예정.

배포 시 링크 : 

## 기술적 특징
1. MSA 구조이며, Java와 Python의 장점을 살린 프로젝트입니다. 클라이언트는 고민중.
2. 모든 기능들이 MSA에서의 유연한 확장을 위해 Kafka로 통신하고 있습니다.
3. Token Blacklist, Search 시 속도증강을 위한 Redis를 사용합니다.
4. API-gateway에 회복탄력성을 추가함으로서, 리소스 낭비를 줄였습니다.
5. 모든 배포는 dockerfile, docker-compose를 통해서 진행중입니다.
- 혹시 모를 Port 등 노출 우려가 있어 코드는 gitignore를 통해 관리되고 있습니다.

## 그 외 특징
1. 모든 서비스들은 /docs 로 들어가면 Swagger문서 나옵니다.
2. 테스트코드 제작예정입니다. 기획이 완전하지 않아서 구현부터 하고있습니다.
3. ddl-auto는 현재 update이나, validate로 변경예정입니다.

## 실행법
1. docker network create shared-network 를 통해 공용 네트워크를 만들어줍니다.
2. 각각의 서비스에서 docker compose up 명령어를 통해 서비스를 실행시킵니다.
3. Eureka 상에서 각각의 서비스들이 모두 연결되었는가 확인합니다.