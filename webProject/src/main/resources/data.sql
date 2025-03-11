INSERT INTO member (memail, mpwd, mname, mphone, maddr, role, isapproved, cdate, udate)
VALUES
('admin@gmail.com', 'admin1234', '관리자', '010-6666-8888', '서울시 강서구', 'admin', true,  NOW(), NOW()),
('dmlfhlwk@gmail.com', '1234', '요청자영', '010-1234-1234', '서울시 강남구', 'requester', true, NOW(), NOW()),
('dmlfhlwk1@gmail.com', '1234', '요청자일', '010-1234-2345', '인천시 부평구', 'requester', true, NOW(), NOW()),
('dmlfhlwk2@gmail.com', '1234', '요청자이', '010-1234-3456', '경기도 시흥시', 'requester', true, NOW(), NOW()),
('dmlfhlwk3@gmail.com', '1234', '요청자삼', '010-1234-4567', '인천시 남동구', 'requester', true, NOW(), NOW()),
('dmlfhlwk4@gmail.com', '1234', '요청자사', '010-1234-5678', '인천시 부평구', 'requester', true, NOW(), NOW()),
('dmlfhlwk5@gmail.com', '1234', '요청자오', '010-1234-6789', '인천시 남동구', 'requester', true, NOW(), NOW()),
('dmlfhlwk6@gmail.com', '1234', '요청자육', '010-1234-7890', '경기도 부천시', 'requester', true, NOW(), NOW()),
('dmlfhlwk7@gmail.com', '1234', '요청자칠', '010-1234-1478', '경기도 안양시', 'requester', true, NOW(), NOW()),
('dmlfhlwk8@gmail.com', '1234', '요청자팔', '010-1234-2589', '서울시 금천구', 'requester', true, NOW(), NOW()),
('dmlfhlwk9@gmail.com', '1234', '요청자구', '010-1234-3698', '서울시 구로구', 'requester', true, NOW(), NOW()),
('djqcp@gmail.com', '1234', '㈜인테리어왕', '010-2345-1234', '인천시 남동구', 'company', true,  NOW(), NOW()),
('djqcp1@gmail.com', '1234', '㈜이쁜내집찾기', '010-2345-2345', '경기도 시흥시', 'company', true,  NOW(), NOW()),
('djqcp2@gmail.com', '1234', '㈜청소시스템', '010-2345-3456', '인천시 부평구', 'company', true,  NOW(), NOW()),
('djqcp3@gmail.com', '1234', '㈜온락', '010-2345-4567', '인천시 부평구', 'company', true,  NOW(), NOW()),
('djqcp4@gmail.com', '1234', '㈜참이슬처럼', '010-2345-5678', '경기도 안양시', 'company', true,  NOW(), NOW()),
('aktmxj@gmail.com', '1234', '마스터영', '010-3456-1234', '인천시 부평구', 'master', true,  NOW(), NOW()),
('aktmxj1@gmail.com', '1234', '마스터일', '010-3456-2345', '경기도 안양시', 'master', true,  NOW(), NOW()),
('aktmxj2@gmail.com', '1234', '마스터이', '010-3456-3456', '인천시 부평구', 'master', true,  NOW(), NOW()),
('aktmxj3@gmail.com', '1234', '마스터삼', '010-3456-4567', '경기도 시흥시', 'master', true,  NOW(), NOW()),
('aktmxj4@gmail.com', '1234', '마스터사', '010-3456-5678', '경기도 부천시', 'master', true,  NOW(), NOW()),
('aktmxj5@gmail.com', '1234', '마스터오', '010-3456-6789', '인천시 부평구', 'master', true,  NOW(), NOW());

--INSERT INTO memberfile (mno, mfname, cdate, udate)
--VALUES
--(1, 'profile1.jpg', NOW(), NOW()),
--(2, 'profile2.jpg', NOW(), NOW()),
--(3, 'profile3.jpg', NOW(), NOW()),
--(4, 'profile4.jpg', NOW(), NOW()),
--(5, 'profile5.jpg', NOW(), NOW());

INSERT INTO service_request (mno, reqtitle, reqcontent, reqspace, raddress, latitude, longitude, reqstate, reqrole, cdate, udate)
VALUES
(2, '거실 청소 및 정리수납 요청', '거실의 청소와 함께 정리수납 서비스를 요청합니다.', '4', '경기도 시흥시 능곡중앙로 120', 37.3584, 126.7834, true, 2, NOW(), NOW()),
(3, '안방 청소 및 정리수납 요청', '안방의 청소와 정리수납을 부탁드립니다.', '2', '인천시 송림로 120', 37.4810, 126.6353, false, 1, NOW(), NOW()),
(4, '주방 청소 및 정리수납 요청', '주방의 청소와 함께 정리수납 서비스를 요청합니다.', '1', '경기도 부천시 원미로 200', 37.4848, 126.7596, true, 2, NOW(), NOW()),
(5, '욕실 청소 및 정리수납 요청', '욕실의 청소와 정리수납을 부탁드립니다.', '7', '인천시 부평구 부평대로 300', 37.4841, 126.7241, false, 1, NOW(), NOW()),
(6, '거실 및 주방 청소 요청', '거실과 주방의 청소를 요청합니다.', '4', '경기도 안양시 동안구 시민대로 400', 37.3917, 126.9305, true, 2, NOW(), NOW()),
(7, '침실 청소 및 정리수납 요청', '침실의 청소와 정리수납을 부탁드립니다.', '2', '인천시 연수구 송도과학로 500', 37.3922, 126.6520, false, 1, NOW(), NOW()),
(8, '거실 및 침실 청소 요청', '거실과 침실의 청소를 요청합니다.', '2', '경기도 광명시 오리로 600', 37.4521, 126.8585, true, 2, NOW(), NOW()),
(9, '주방 및 욕실 청소 요청', '주방과 욕실의 청소를 부탁드립니다.', '1', '인천시 서구 가정로 700', 37.4860, 126.6743, false, 1, NOW(), NOW()),
(10, '전체 청소 및 정리수납 요청', '집 전체의 청소와 정리수납을 요청합니다.', '7', '경기도 시흥시 정왕동 800', 37.3707, 126.7404, true, 2, NOW(), NOW()),
(11, '거실 및 주방 정리수납 요청', '거실과 주방의 정리수납 서비스를 부탁드립니다.', '4', '인천시 남동구 예술로 900', 37.4569, 126.7209, false, 1, NOW(), NOW()),
(2, '안방 및 침실 청소 요청', '안방과 침실의 청소를 요청합니다.', '2', '경기도 김포시 김포대로 1000', 37.6302, 126.7354, true, 2, NOW(), NOW()),
(3, '욕실 청소 요청', '욕실의 청소를 부탁드립니다.', '7', '인천시 중구 신포로 1100', 37.4829, 126.6170, false, 1, NOW(), NOW()),
(4, '거실 및 욕실 청소 요청', '거실과 욕실의 청소를 요청합니다.', '7', '경기도 오산시 오산로 1200', 37.1465, 127.0786, true, 2, NOW(), NOW()),
(5, '주방 정리수납 요청', '주방의 정리수납 서비스를 부탁드립니다.', '1', '인천시 동구 만석로 1300', 37.4820, 126.6169, false, 1, NOW(), NOW()),
(6, '침실 정리수납 요청', '침실의 정리수납을 요청합니다.', '3', '경기도 의정부시 의정부로 1400', 37.7387, 127.0334, true, 2, NOW(), NOW()),
(7, '거실 청소 요청', '거실의 청소를 부탁드립니다.', '4', '인천시 미추홀구 경인로 1500', 37.4502, 126.6598, false, 1, NOW(), NOW()),
(8, '안방 청소 요청', '안방의 청소를 요청합니다.', '2', '경기도 양주시 양주로 1600', 37.8011, 127.0520, true, 2, NOW(), NOW()),
(9, '주방 청소 요청', '주방의 청소를 부탁드립니다.', '1', '인천시 부평구 부평대로 1700', 37.4957, 126.7252, false, 1, NOW(), NOW()),
(10, '욕실 청소 요청', '욕실의 청소를 요청합니다.', '7', '경기도 파주시 파주읍 1800', 37.7569, 126.7631, true, 2, NOW(), NOW()),
(11, '전체 청소 요청', '집 전체의 청소를 부탁드립니다.', '7', '인천시 계양구 계양대로 1900', 37.5130, 126.7321, false, 1, NOW(), NOW()),
(2, '거실 및 침실 청소 요청', '거실과 침실의 청소를 요청합니다.', '7', '경기도 군포시 군포로 2000', 37.3575, 126.9339, true, 2, NOW(), NOW()),
(3, '주방 및 욕실 정리수납 요청', '주방과 욕실의 정리수납 서비스를 부탁드립니다.', '7', '인천시 서구 서곶로 2100', 37.4969, 126.6592, false, 1, NOW(), NOW());


INSERT INTO service_estimate (mno, reqno, esttitle, estcontent, estcash, eststate, cdate, udate)
VALUES
(14, 1, '거실 청소 및 정리수납 견적', '거실의 바닥과 창문, 가구 표면을 청소하고, 정리수납을 위한 공간을 확보하여 깔끔하게 정돈합니다.', 90000, false, NOW(), NOW()),
(19, 1, '거실 청소 요청 견적', '거실의 바닥 청소 및 커튼, 테이블 등의 먼지를 제거하고, 청소 후 공간을 정리합니다.', 85000, false, NOW(), NOW()),
(22, 2, '안방 청소 및 정리수납 견적', '안방의 바닥 청소와 침대 아래 공간 청소, 정리수납을 통해 깔끔한 공간을 만듭니다.', 95000, false, NOW(), NOW()),
(15, 3, '주방 청소 및 정리수납 견적', '주방의 조리대, 싱크대, 냉장고 외부를 청소하고, 조리도구 및 식기류를 정리하여 공간을 넓힙니다.', 87000, false, NOW(), NOW()),
(18, 4, '욕실 청소 및 정리수납 견적', '욕실 타일 및 세면대 청소, 욕조 주변의 곰팡이 제거와 정리수납을 통한 공간 활용을 제안합니다.', 98000, false, NOW(), NOW()),
(21, 5, '거실 및 주방 청소 견적', '거실과 주방의 바닥, 주방 조리대 및 식기 정리, 청소를 진행합니다.', 91000, false, NOW(), NOW()),
(16, 6, '침실 청소 및 정리수납 견적', '침대 주변과 바닥을 청소하고, 옷장을 정리하여 침실을 깔끔하게 만듭니다.', 87000, false, NOW(), NOW()),
(19, 7, '거실 및 침실 청소 요청 견적', '거실과 침실의 바닥을 청소하고, 테이블, 의자 및 침대의 먼지를 제거합니다.', 88000, false, NOW(), NOW()),
(20, 8, '주방 및 욕실 청소 견적', '주방과 욕실의 타일 및 세면대 청소를 진행하고, 공간을 정리하여 쾌적한 환경을 만듭니다.', 95000, false, NOW(), NOW()),
(13, 9, '전체 청소 및 정리수납 견적', '집 전체 청소, 각 방의 바닥 및 가구 청소, 정리수납을 통한 효율적인 공간 활용을 제공합니다.', 99000, false, NOW(), NOW()),
(22, 10, '거실 및 주방 정리수납 견적', '거실과 주방의 바닥 청소 후, 주방 도구 및 식기류를 정리하여 공간을 넓히는 서비스를 제공합니다.', 89000, false, NOW(), NOW()),
(14, 11, '안방 및 침실 청소 견적', '안방과 침실의 바닥 청소 및 침대 아래 청소를 포함한 전체 정리수납을 제공합니다.', 86000, false, NOW(), NOW()),
(17, 12, '욕실 청소 요청 견적', '욕실의 세면대와 욕조 청소, 타일 곰팡이 제거 및 청소를 진행합니다.', 90000, false, NOW(), NOW()),
(19, 13, '거실 및 욕실 청소 요청 견적', '거실의 바닥 청소 및 욕실 타일 청소, 세면대와 욕조 청소를 포함합니다.', 88000, false, NOW(), NOW()),
(16, 14, '주방 정리수납 요청 견적', '주방의 식기 및 도구를 정리하고, 싱크대 및 조리대 청소를 포함한 정리수납 작업을 제공합니다.', 92000, false, NOW(), NOW()),
(20, 15, '침실 정리수납 요청 견적', '침실의 옷장을 정리하고, 바닥 청소 및 침대 주변 청소를 진행합니다.', 89000, false, NOW(), NOW()),
(13, 16, '거실 청소 요청 견적', '거실의 소파 및 의자 청소와 바닥 청소, 간단한 정리수납 작업을 포함합니다.', 85000, false, NOW(), NOW()),
(22, 17, '안방 청소 요청 견적', '안방의 침대 및 바닥 청소, 창문 및 커튼을 청소합니다.', 92000, false, NOW(), NOW()),
(14, 18, '주방 청소 요청 견적', '주방 조리대, 싱크대 및 바닥 청소 후, 주방용품을 정리합니다.', 87000, false, NOW(), NOW()),
(19, 19, '욕실 청소 요청 견적', '욕실의 세면대 및 욕조 청소와 타일 곰팡이 제거 작업을 진행합니다.', 90000, false, NOW(), NOW()),
(21, 20, '전체 청소 요청 견적', '집 전체 청소, 각 방의 바닥, 창문, 가구 및 욕실 청소를 포함한 전체 청소 서비스를 제공합니다.', 98000, false, NOW(), NOW()),
(17, 21, '거실 및 침실 청소 요청 견적', '거실과 침실의 바닥 청소 및 소파, 침대의 먼지를 제거합니다.', 86000, false, NOW(), NOW()),
(18, 22, '주방 및 욕실 정리수납 요청 견적', '주방과 욕실의 물건을 정리하고, 각 공간의 청소 및 정리수납 작업을 진행합니다.', 94000, false, NOW(), NOW());


--INSERT INTO joboffer (mno, jono, jostate, jocity, jocontent, jodistrict, joservice, jotitle, cdate, udate)
INSERT INTO joboffer (mno, jono, jostate, joaddr, detailaddr, longitude, latitude ,jocontent, joservice, jotitle, cdate, udate)
VALUES
(12, 2, 1, '서울 용산구 남산공원길 105','', 126.978371, 37.532600, '거실 및 주방 청소, 창문 청소가 필요합니다. 정리수납 서비스도 원합니다.', '주방', '거실 주방 청소 및 정리수납 서비스', NOW(), NOW()),
(13, 3, 0, '인천 부평구 시장로 7 5층','', 126.734100, 37.494680, '욕실 청소와 함께 냉장고 정리가 필요합니다. 주방도 깨끗하게 청소해주세요.', '안방', '욕실 청소 및 냉장고 정리 서비스', NOW(), NOW()),
(14, 4, 0, '인천광역시 계양구 주부토로387번길 8-1','', 126.730000, 37.538000, '전체 청소 및 침실, 거실 청소가 필요하며, 바닥 청소와 먼지 제거를 포함합니다.', '거실', '전체 집 청소 및 침실, 거실 청소', NOW(), NOW()),
(15, 5, 1, '서울 영등포구 여의도동 의사당대로 1','', 126.930000, 37.526000, '이사 후 청소와 정리정돈이 필요합니다. 가구 배치도 요청합니다.', '자녀방', '이사 후 청소 및 정리정돈 서비스', NOW(), NOW()),
(12, 7, 1, '경기 시흥시 시청로 20 시흥시청','', 126.783500, 37.384400, '방 청소와 함께 책상 및 의자 정리정돈을 원합니다.', '주방', '방 청소 및 정리수납 서비스', NOW(), NOW()),
(13, 8, 1, '인천 중구 연안부두로 70','', 126.633300, 37.475500, '전체 청소 후 가구 배치를 돕는 서비스가 필요합니다.', '거실', '전체 청소 및 가구 배치 서비스', NOW(), NOW()),
(14, 9, 0, '경기 성남시 분당구 정자일로 95','', 127.073400, 37.497000, '집 전체 청소와 함께 욕실 청소가 필요합니다. 바닥 및 창문 청소를 포함해주세요.','서재', '집 전체 청소 및 욕실 청소', NOW(), NOW()),
(15, 10, 0, '제주 제주시 첨단로 242','', 126.492400, 33.489300, '정리수납 및 방 청소 서비스가 필요합니다. 특히 서랍 정리와 옷 정리가 중요합니다.','거실', '방 청소 및 정리수납 서비스', NOW(), NOW()),
(12, 11, 1, '서울 강남구 테헤란로 311','', 127.032100, 37.500400, '주방 및 욕실 청소, 창문 청소가 필요합니다. 수납정리도 부탁드립니다.', '서재', '주방, 욕실 청소 및 수납정리 서비스', NOW(), NOW()),
(13, 12, 0, '경기 고양시 일산동구 정발산로 24','', 126.758300, 37.660200, '전체 청소와 함께 침실 및 거실 청소, 그리고 벽면 청소가 필요합니다.', '기타', '전체 청소 및 침실, 거실 청소', NOW(), NOW()),
(14, 13, 0, '서울 마포구 독막로 123','', 126.945100, 37.550300, '가전제품 청소 및 정리정돈 서비스가 필요합니다. 냉장고, 전자레인지 등 청소해 주세요.', '기타', '가전제품 청소 및 정리정돈 서비스', NOW(), NOW()),
(15, 14, 1, '경기 성남시 수정구 복정로 125','', 127.107000, 37.429200, '이사 후 청소 및 정리정돈 서비스가 필요합니다. 또한 가구 배치도 요청합니다.', '사무실', '이사 후 청소 및 가구 배치 서비스', NOW(), NOW()),
(12, 15, 1, '서울 송파구 위례광장로 258','', 127.124500, 37.497700, '거실과 주방 청소가 필요하며, 식기류 정리와 수납도 요청합니다.', '거실', '거실, 주방 청소 및 수납정리 서비스', NOW(), NOW()),
(13, 16, 0, '인천 서구 가정로 201','', 126.649700, 37.459600, '욕실 및 방 청소가 필요하며, 옷 정리 및 수납정리 서비스도 요청합니다.', '안방', '욕실 및 방 청소, 옷 정리 서비스', NOW(), NOW()),
(14, 17, 0, '서울 용산구 한강대로 101','', 126.964800, 37.530300, '전체 청소 및 침실, 욕실 청소를 원합니다. 또한 바닥 청소를 포함해주세요.', '기타', '전체 청소 및 침실, 욕실 청소', NOW(), NOW()),
(15, 18, 1, '경기 수원시 영통구 광교로 144','', 127.027300, 37.258400, '이사 후 청소와 방 정리정돈이 필요합니다. 또한 냉장고 및 서랍 정리도 요청합니다.', '안방', '이사 후 청소 및 방 정리정돈 서비스', NOW(), NOW()),
(12, 19, 1, '서울 강동구 성내로 70','', 127.137500, 37.528600, '욕실 청소와 함께 전체 청소 및 창문 청소를 요청합니다.', '자녀방', '욕실 청소 및 전체 청소', NOW(), NOW()),
(13, 20, 0, '서울 구로구 디지털로 29길 38','', 126.881200, 37.487800, '전체 청소 및 거실 청소가 필요하며, 책상과 서랍 정리도 원합니다.', '서재', '전체 청소 및 책상 정리 서비스', NOW(), NOW());


INSERT INTO like_job (jono, lno, lstate, mno, cdate, udate)
VALUES
(2, 2, true,  17, NOW(), NOW()),
(3, 3, true,  18, NOW(), NOW()),
(4, 4, true,  19, NOW(), NOW()),
(5, 5, true,  20, NOW(), NOW()),
(7, 7, true,  16, NOW(), NOW()),
(8, 8, true,  17, NOW(), NOW()),
(9, 9, true,  18, NOW(), NOW()),
(10, 10, true, 19, NOW(), NOW());


INSERT INTO review (revno, revcontent, revstar, mno, estno, cdate, udate)
VALUES
(1, "청소 너무 잘됬네요!", 4, 2, 2, NOW(), NOW()),
(2, "서비스가 조금 부족했어요.", 3, 3, 1, NOW(), NOW()),
(3, "금방 확실하게 끝내주셨어요.", 5, 4, 2, NOW(), NOW()),
(4, "가격이 비싸서 그런지 꼼꼼하게 잘 봐주시네요 ", 5, 5, 3, NOW(), NOW()),
(5, "서비스가 조금 부족했어요.", 2, 6, 1, NOW(), NOW());


INSERT INTO revfile (revfname, revno, cdate, udate)
VALUES
( "UUID-안방사진 후기.jpg", 1, NOW(), NOW()),
( "UUID-안방사진2 후기.jpg", 1, NOW(), NOW()),
( "UUID-주방사진 후기.jpg", 2, NOW(), NOW()),
( "UUID-주방사진2 후기.jpg", 2, NOW(), NOW()),
( "UUID-옷장정리후기.jpg", 3, NOW(), NOW()),
( "UUID-옷장정리후기2.jpg", 3, NOW(), NOW()),
( "UUID-기타사진 후기.jpg", 4, NOW(), NOW());
