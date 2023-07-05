# 스프링 부트 프로젝트
- 강의 신청, 강의 후기 및 관련 정보 커뮤니티.

## 프로젝트 기간
- 2023.06.01 ~

## 기술 스택

## WireFrame

## ERD

## Trouble Shooting

### 구현 내용
- 스프링 부트 기본 설정 완료
- 관리자 페이지
  - 레이아웃 / 아웃라인
  - style.css
  - 사이트 설정 저장 기능 구현중

> 기본 설정
- 사이트 설정 저장 / 수정 / 조회 / 삭제 기능 구현 & interceptor 구현.
- BaseEntity & BaseMemberEntity 추가
- Regex 이넘 클래스 : 정규표현식을 이넘 객체에 저장.

> 회원 관련 기능 구현
1. [x] 회원가입 기능
  - 회원 정보 추가/ 수정
2. [x] 로그인 기능
  - 회원 정보 조회(UserDetails & UserDetailsService 구현) - spring 시큐리티
    - List<GrantedAuthority> authorities
      - 시큐리티 인가 기능에서 사용하는 값.(회원의 권한 확인)
      - .requestMatchers("/admin/**").hasAuthority("ADMIN") // 사용 예시
    - MemberUtil : 회원 인가관련 편의 기능.
  - 로그인 성공 및 실패 시 세션처리 및 이동 페이지 Handling - spring 시큐리티
  - 아이가디 저장 기능 추 - 쿠키 애너테이션
    - Controller에 @CookieValue 애너테이션 추가 및 LoginSuccessHandler 상에 로그인 성공 시 쿠키 객체 생성 쿠키 추가함.
3. [X] 회원탈퇴 기능(구현중)
   - 마이페이지 → 회원탈퇴 버튼 → 회원 탈퇴 폼으로 이동 → 회원 아이디(로그인한 회원 아이디)/ 회원 비밀번호(입력) / 삭제하겠습니다.
     → 입력 비밀번호와 로그인 회원 비밀번호 일치 여부 확인 → true : 회원 탈퇴 확인, false : 비밀번호가 일치하지 않습니다.
     - 회원 탈퇴 서비스
   - 로그인 회원 비밀번호 해쉬 복호화 불가.. -> 일치 여부 확인 어떻게..? -> PasswordEncoder .matches(입력 비번, 해시화된 비번) 사용
4. [X] 회원 아이디 & 비밀번호 찾기 및 비밀번호 변경 기능 구현
  - controller
    - findId - 회원명과 회원 이메일로 아이디 찾기. 
    - findPw - 회원 아이디로 비밀번호 찾기.
    - findIdForm 커맨드 객체.(회원명, 회원이메일)
    - PwResetForm
    - PwValidator
  - templates
    - findid
    - findpw
    - resetPw
  - model/member
    - FindMemberService
    - WrongInfoException
5. [X] 부가 기능
   - js 확인 메시지(confirm && alert) 추가(정말로 탈퇴하시겠습니까?) -> SweetAlert 사용.

> 파일 업로드
- [X] 파일등록 및 조회 기능 구현
  - 파일 정보 저장
  - 실제 파일 업로드
  - 그룹 작업 완료 처리(done)

> 회원 관리 페이지(관리자 페이지)
1. [] 회원 조회(index) 기능 구현
2. [] 회원 강제 탈퇴(forcedDelete) 기능 구현
3. [] 회원 권한 변경(updateRole) 기능 구현

### 게시판
6/18
- 게시판 설정 등록
- 게시판 설정 수정
- 게시판 설정 조회
- 게시판 설정 목록 조회
- 
### 게시글
6/18
- 게시글 등록 폼 작성중

### 강의 파트
- 강의 등록 페이지
    - 권한 : 강사, 관리자 / 일반회원, 비회원 -> 강사 권한이 필요합니다 -> 로그인 화면
- 강의 저장
    - 검증, 저장 성공 시 페이지 이동-> 강의가 등록되었습니다.
- 강의 조회
    - DB에 저장된 리스트 조회 -> 관리자 페이지 -> 검색 기능
    - DB에 저장된 강의 카테고리 구분 후 조회 -> 사용자 페이지

- 강의 삭제
    - 권한 : 관리자