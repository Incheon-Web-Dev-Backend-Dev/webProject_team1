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
(1, '주방 정리수납', '주방 정리수납 견적 요청합니다', '주방', '서울시', '강남구', true, NOW(), NOW()),
(2, '안방 붙박이장', '안방 붙박이장 설치 문의드립니다', '안방', '서울시', '서초구', true, NOW(), NOW()),
(3, '거실 선반 설치', '거실 벽면 선반 설치 요청', '거실', '서울시', '송파구', true, NOW(), NOW()),
(4, '서재 책장 정리', '서재 책장 정리 견적 요청', '서재', '서울시', '강동구', true, NOW(), NOW()),
(5, '사무실 수납장', '사무실 서류 수납장 견적 필요', '사무실', '서울시', '마포구', true, NOW(), NOW()),
(1, '자녀방 옷장', '자녀방 옷장 정리 요청합니다', '자녀방', '서울시', '용산구', true, NOW(), NOW()),
(2, '다용도실 정리', '다용도실 선반 설치 및 정리', '기타', '서울시', '성동구', true, NOW(), NOW()),
(3, '드레스룸 수납', '드레스룸 수납장 설치 요청', '안방', '서울시', '광진구', true, NOW(), NOW()),
(4, '현관 신발장', '현관 신발장 교체 견적 문의', '기타', '서울시', '중구', true, NOW(), NOW()),
(1, '주방 정리수납 도움요청', '주방 수납장이 너무 복잡해서 필요한 물건을 찾기가 힘듭니다. 그릇류, 조리도구, 식재료 등을 체계적으로 분류해서 정리하고 싶어요. 특히 오래된 그릇들과 중복된 주방도구들을 처리하는 방법도 조언 부탁드립니다. 주방 정리에 전문성 있는 분이면 좋겠습니다.', '주방', '서울시', '강남구', true, NOW(), NOW()),
(2, '옷방 정리 도와주세요', '옷장과 서랍장이 옷들로 가득 차서 더이상 수납이 어려운 상황입니다. 계절별 옷 분류가 안 되어있고, 입지 않는 옷들도 많이 쌓여있어요. 옷 정리와 함께 보관방법, 관리방법도 컨설팅 받고 싶습니다.', '안방', '서울시', '서초구', true, NOW(), NOW()),
(3, '아이방 장난감 정리', '초등학생 두 아이의 방에 장난감이 너무 많이 쌓여있어요. 자주 쓰는 장난감과 그렇지 않은 것들을 구분하고, 연령에 맞지 않는 장난감들의 처리도 고민입니다. 아이들이 스스로 정리할 수 있는 시스템을 만들어주세요.', '자녀방', '서울시', '송파구', true, NOW(), NOW()),
(4, '거실 정리정돈', '거실에 책과 잡지, 아이들 학습지, 각종 생활용품들이 뒤섞여 있습니다. 깔끔한 거실을 만들고 싶은데 어떻게 정리해야 할지 모르겠어요. 거실 공간을 효율적으로 활용할 수 있게 도와주세요.', '거실', '서울시', '강동구', true, NOW(), NOW()),
(5, '사무실 서류정리', '회사 서류와 문서들이 산더미처럼 쌓여있습니다. 보관해야 할 서류와 폐기할 서류 분류가 필요하고, 앞으로의 문서 관리 시스템도 구축하고 싶습니다. 업무 효율을 높일 수 있는 정리 방법을 알려주세요.', '사무실', '서울시', '마포구', true, NOW(), NOW()),
(1, '다용도실 정리', '다용도실에 계절용품, 세탁용품, 청소도구가 너무 복잡하게 있어서 필요할 때 찾기가 힘듭니다. 자주 쓰는 물건과 가끔 쓰는 물건을 구분해서 정리하고 싶어요. 깔끔한 다용도실을 만들고 싶습니다.', '기타', '서울시', '용산구', true, NOW(), NOW()),
(2, '주방 냉장고 정리', '냉장고 속이 너무 지저분하고 정리가 안되어있어서 식재료 관리가 안 됩니다. 유통기한 지난 음식들도 많고, 비슷한 재료를 중복 구매하는 경우도 잦아요. 깔끔한 냉장고 정리방법을 배우고 싶습니다.', '주방', '서울시', '성동구', true, NOW(), NOW()),
(3, '드레스룸 옷 정리', '드레스룸 옷장이 너무 복잡해서 매일 아침 옷 고르기가 스트레스예요. 안 입는 옷도 많고 시즌별 옷 구분도 안되어있어서 정리가 시급합니다. 깔끔한 옷장 정리 부탁드려요.', '안방', '서울시', '광진구', true, NOW(), NOW()),
(4, '신발 정리 도움요청', '4인 가족의 신발이 현관에 너무 많이 쌓여있어요. 계절별로 신발을 구분하고, 관리가 필요한 신발들도 많습니다. 신발 정리와 보관 방법을 전문가와 상담하고 싶습니다.', '기타', '서울시', '중구', true, NOW(), NOW()),
(5, '서재 책장 정리', '책장에 책이 너무 많이 쌓여있어요. 읽은 책, 읽을 책, 보관할 책 등을 분류하고 정리하고 싶습니다. 책 정리와 함께 앞으로의 관리 방법도 조언 부탁드립니다.', '서재', '서울시', '종로구', true, NOW(), NOW());

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
