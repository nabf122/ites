
1. 패키지 트리 구조

egovframework.com.ites
├─ config                # 보안/Swagger/CORS/Interceptor 등 설정
├─ common
│  ├─ exception          # CustomException, ErrorCode, GlobalExceptionHandler
│  ├─ response           # ApiResponse, ErrorResponse
│  └─ util               # 날짜/암호화/공통 유틸
├─ domain
│  ├─ auth
│  │  └─ controller
│  │     ├─ AuthController.java 		# 로그인, 로그아웃, 세션 생성
│  │     └─ PageController.java			# 화면 연결
│  ├─ user
│  │  ├─ controller
│  │  │  └─ UserController.java			# 사용자 조회/추가/수정/삭제
│  │  ├─ service
│  │  │  ├─ UserService.java
│  │  │  └─ impl
│  │  │     └─ UserServiceImpl.java
│  │  ├─ vo
│  │  │  └─ UserVO.java
│  │  └─ mapper
│  │     └─ UserMapper.java
│  ├─ equipment
│  │  ├─ controller
│  │  │  └─ EquipmentController.java	# 장비 조회/추가/수정/삭제
│  │  ├─ service
│  │  │  ├─ EquipmentService.java
│  │  │  └─ impl
│  │  │     └─ EquipmentServiceImpl.java
│  │  ├─ vo
│  │  │  └─ EquipmentVO.java
│  │  └─ mapper
│  │     └─ EquipmentMapper.java
│  ├─ admin
│  │  ├─ controller
│  │  │  └─ AdminController.java
│  │  ├─ service
│  │  │  ├─ AdminService.java
│  │  │  └─ impl
│  │  │     └─ AdminServiceImpl.java
│  │  ├─ vo
│  │  │  └─ AdminVO.java
│  │  └─ mapper
│  │     └─ AdminMapper.java
└─ EgovBootApplication.java

jsp
├─ login
│  └─ login.jsp
├─ user
│  └─ equipment.jsp
├─ admin
│  ├─ list.jsp
│  ├─ user.jsp
│  └─ code.jsp
└─ index.jsp


2. API 목록 템플릿

AuthController
├─ POST /auth/login : 로그인
└─ POST /auth/logout : 로그아웃

PageController
├─ GET /login : 로그인 페이지
├─ GET /equipment : 장비 등록 페이지
├─ GET /admin/main : 관리자 메인 페이지
├─ GET /admin/list : 장비현황 조회 페이지
├─ GET /admin/user : 사용자 관리 페이지
└─ GET /admin/code : 공통코드 관리 페이지

UserController
├─ POST /user/list : 사용자 목록 조회(검색 조건)
├─ POST /user/detail : 사용자 상세 정보 조회
├─ POST /user/save : 사용자 추가/수정
├─ POST /user/delete : 사용자 삭제
├─ POST /user/excel : 사용자 목록 엑셀 출력
└─ POST /user/pw : 패스워드 초기화

EquipmentController
├─ POST /equipment/list : 장비 목록 조회
├─ POST /equipment/save : 장비 등록/수정/삭제
└─ POST /equipment/excel : 장비 목록 엑셀 출력

AdminController
├─ POST /admin/list : 장비 목록 조회(검색 조건)
├─ POST /admin/excel : 장비 목록 엑셀 출력
├─ POST /admin/code/list : 코드 목록 조회
├─ POST /admin/code/detail : 코드 상세 정보 조회
├─ POST /admin/code/insert : 코드 추가
├─ POST /admin/code/update : 코드 수정
└─ POST /admin/code/delete : 코드 삭제


3. 흐름도
#


4. DB 테이블 스키마(DDL)

- 사용자 테이블
- 장비현황 테이블
- 코드 테이블

CREATE TABLE user (
  user_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  login_id       VARCHAR(50) NOT NULL UNIQUE,
  password_hash  VARCHAR(64) NOT NULL,
  name           VARCHAR(50) NOT NULL,
  role           VARCHAR(20) NOT NULL, -- USER / ADMIN
  status         VARCHAR(20) NOT NULL, -- ACTIVE / LOCKED
  created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     DATETIME NULL
);


5. 프로젝트 루트:

README.md : 실행/환경/구조 요약(가벼움)
docs/architecture.md : 패키지 트리 + 흐름도
docs/api.md : API 목록/요청응답
docs/db.md : DDL + ERD(있으면)
docs/troubleshooting.md : 자주 터지는 이슈(세션/리다이렉트/JSP 경로 등)
