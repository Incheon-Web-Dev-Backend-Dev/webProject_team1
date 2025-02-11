INSERT INTO member (memail, mpwd, mname, mphone, maddr, role, cdate, udate)
VALUES
('user1@gmail.com', 'password123', '김철수', '010-1234-5678', '서울시 강남구', 'requester', NOW(), NOW()),
('company1@gmail.com', 'password456', '주식회사 인테리어', '010-9876-5432', '서울시 서초구', 'company', NOW(), NOW()),
('expert1@gmail.com', 'password789', '이민수', '010-5555-1234', '서울시 송파구', 'master', NOW(), NOW()),
('company2@gmail.com', 'passwordabc', '행복한 리모델링', '010-4444-8888', '서울시 강동구', 'company', NOW(), NOW()),
('expert2@gmail.com', 'passworddef', '정현우', '010-7777-9999', '서울시 마포구', 'master', NOW(), NOW()),
('admin@goguma.com', 'admin1234', '관리자', '010-6666-8888', '서울시 강서구', 'admin', NOW(), NOW());

INSERT INTO memberfile (mno, mfname, cdate, udate)
VALUES
(1, 'profile1.jpg', NOW(), NOW()),
(2, 'profile2.jpg', NOW(), NOW()),
(3, 'profile3.jpg', NOW(), NOW()),
(4, 'profile4.jpg', NOW(), NOW()),
(5, 'profile5.jpg', NOW(), NOW());

INSERT INTO service_request (mno, reqtitle, reqcontent, reqspace, reqbigarea, reqsmallarea, reqstate, cdate, udate)
VALUES
(1, '욕실 리모델링', '욕실 전체 리모델링 견적 요청합니다', '욕실', '서울시', '강남구', true, NOW(), NOW()),
(2, '주방 싱크대 교체', '주방 싱크대 교체 견적 문의', '주방', '서울시', '서초구', true, NOW(), NOW()),
(3, '전등 교체 작업', '거실 전등 교체 작업 요청', '거실', '서울시', '송파구', true, NOW(), NOW()),
(4, '베란다 확장', '베란다 확장 공사 견적 요청', '베란다', '서울시', '강동구', true, NOW(), NOW()),
(5, '화장실 수도공사', '화장실 수도 누수 수리 필요', '화장실', '서울시', '마포구', true, NOW(), NOW());

INSERT INTO reqfile (reqno, reqfname, cdate, udate)
VALUES
(1, 'request1_image.jpg', NOW(), NOW()),
(2, 'request2_image.jpg', NOW(), NOW()),
(3, 'request3_image.jpg', NOW(), NOW()),
(4, 'request4_image.jpg', NOW(), NOW()),
(5, 'request5_image.jpg', NOW(), NOW());

INSERT INTO service_estimate (mno, reqno, esttitle, estcontent, estcash, eststate, cdate, udate)
VALUES
(2, 1, '욕실 리모델링 견적서', '욕실 리모델링 전체 견적입니다', '500만원', false, NOW(), NOW()),
(1, 2, '싱크대 교체 견적', '주방 싱크대 교체 견적입니다', '200만원', false, NOW(), NOW()),
(3, 3, '전등 교체 견적서', '거실 전등 교체 견적입니다', '50만원', false, NOW(), NOW()),
(4, 4, '베란다 확장 견적', '베란다 확장 공사 견적입니다', '800만원', false, NOW(), NOW()),
(5, 5, '수도 공사 견적서', '화장실 수도 누수 수리 견적입니다', '100만원', false, NOW(), NOW());

INSERT INTO joboffer (mno, jono, jostate, jocity, jocontent, jodistrict, joservice, jotitle, cdate, udate)
VALUES
(1, 1, 1, '서울시', '전기 배선 공사 구인', '강남구', '전기공사', '전기기술자 구합니다', NOW(), NOW()),
(2, 2, 1, '경기도', '도배장판 시공', '수원시', '인테리어', '도배 기술자 구인', NOW(), NOW()),
(3, 3, 1, '서울시', '욕실 타일 시공', '송파구', '타일시공', '타일 기술자 모집', NOW(), NOW()),
(4, 4, 1, '인천시', '주방 설비 공사', '부평구', '설비공사', '설비 기술자 구함', NOW(), NOW()),
(5, 5, 1, '서울시', '페인트 도색', '마포구', '도색작업', '페인트 작업자 구인', NOW(), NOW());

INSERT INTO jobfile (jono, jfname, cdate, udate)
VALUES
(1, 'job1_image.jpg', NOW(), NOW()),
(2, 'job2_image.jpg', NOW(), NOW()),
(3, 'job3_image.jpg', NOW(), NOW()),
(4, 'job4_image.jpg', NOW(), NOW()),
(5, 'job5_image.jpg', NOW(), NOW());

INSERT INTO like_job (jono, lno, lstatus, mno, cdate, udate)
VALUES
(1, 1, 1, 2, NOW(), NOW()),
(2, 2, 1, 1, NOW(), NOW()),
(3, 3, 1, 4, NOW(), NOW()),
(4, 4, 1, 3, NOW(), NOW()),
(5, 5, 1, 5, NOW(), NOW());

INSERT INTO review (revno, revcontent, revstar, mno, reqno, estno, cdate, udate)
VALUES
(1, "청소 너무 잘됬네요!", 4, 2, 1, 2, NOW(), NOW()),
(2, "서비스가 조금 부족했어요.", 3, 3, 2, 1, NOW(), NOW()),
(3, "금방 확실하게 끝내주셨어요.", 5, 4, 3, 2, NOW(), NOW()),
(4, "가격이 비싸서 그런지 꼼꼼하게 잘 봐주시네요 ", 5, 5, 4, 3, NOW(), NOW()),
(5, "서비스가 조금 부족했어요.", 2, 6, 5, 1, NOW(), NOW());


INSERT INTO revfile (revfname, revno, cdate, udate)
VALUES
( "UUID-안방사진 후기.jpg", 1, NOW(), NOW()),
( "UUID-안방사진2 후기.jpg", 1, NOW(), NOW()),
( "UUID-주방사진 후기.jpg", 2, NOW(), NOW()),
( "UUID-주방사진2 후기.jpg", 2, NOW(), NOW()),
( "UUID-옷장정리후기.jpg", 3, NOW(), NOW()),
( "UUID-옷장정리후기2.jpg", 3, NOW(), NOW()),
( "UUID-기타사진 후기.jpg", 4, NOW(), NOW());
