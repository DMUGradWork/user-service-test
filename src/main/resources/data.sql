-- 기존 테이블이 존재하면 삭제하고 새로 생성
DROP TABLE IF EXISTS users;

-- 테이블 생성 (버전 관리 필드 추가)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,             -- id 필드 (수동으로 삽입
    name VARCHAR(255) NOT NULL,        -- 사용자 이름
    email VARCHAR(255) NOT NULL UNIQUE, -- 이메일
    phone VARCHAR(20) NOT NULL,        -- 전화번호
    password VARCHAR(255) NOT NULL,    -- 비밀번호
    role VARCHAR(50) NOT NULL DEFAULT 'USER', -- 사용자 역할 (기본값 'USER')
    join_date DATE NOT NULL,           -- 가입 날짜
    last_login DATE,                   -- 마지막 로그인 날짜
    version BIGINT NOT NULL DEFAULT 0  -- 버전 관리 필드 추가 (기본값 0)
);


INSERT INTO users (name, email, phone, password, role, join_date, version)
VALUES
('홍길동', 'hong@example.com', '010-1234-5678', 'password123', 'USER', '2025-03-31', 0),
('김철수', 'kim@example.com', '010-9876-5432', 'password456', 'ADMIN', '2025-03-30', 0),
('박영희', 'park@example.com', '010-1122-3344', 'password789', 'USER', '2025-03-28', 0);