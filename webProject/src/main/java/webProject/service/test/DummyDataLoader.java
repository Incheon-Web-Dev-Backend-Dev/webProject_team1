package webProject.service.test;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.datafaker.Faker;
import webProject.model.dto.member.MemberDto;
import webProject.model.dto.request.RequestDto;
import webProject.model.entity.member.MemberEntity;
import webProject.model.repository.member.MemberRepository;
import webProject.model.repository.request.RequestRepository;
import webProject.service.member.MemberService;
import webProject.model.entity.request.RequestEntity;

@Component
@Profile("dev") // application-dev.yml 활성화 시에만 실행됨
public class DummyDataLoader implements CommandLineRunner {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private RequestRepository requestRepository;

    private final Faker faker = new Faker(new Locale("ko"));
    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 이미 데이터가 있으면 skip
        if (memberRepository.count() > 0) {
            System.out.println("⚠️ 더미 데이터가 이미 존재하여 생성하지 않습니다.");
            return;
        }

        // 순서대로 데이터 생성
        createSpecificMembers(); // 필수 회원 데이터 생성
        createRandomMembers(100); // 추가 랜덤 회원 생성
        createServiceRequests(); // 서비스 요청서 생성
        createRandomRequests(1000); // 추가 랜덤 요청서 생성

        System.out.println("✅ 회원 및 요청서 더미 데이터 생성 완료");
    }

    // 필수 회원 생성 (기본 테스트용)
    private void createSpecificMembers() {
        // 필수 회원 정보
        Object[][] members = {
                {"admin@gmail.com", "admin1234", "관리자", "010-6666-8888", "서울시 강서구", "admin", true},
                {"dmlfhlwk@gmail.com", "1234", "요청자영", "010-1234-1234", "서울시 강남구", "requester", true},
                {"dmlfhlwk1@gmail.com", "1234", "요청자일", "010-1234-2345", "인천시 부평구", "requester", true},
                {"dmlfhlwk2@gmail.com", "1234", "요청자이", "010-1234-3456", "경기도 시흥시", "requester", true},
                {"dmlfhlwk3@gmail.com", "1234", "요청자삼", "010-1234-4567", "인천시 남동구", "requester", true},
                {"djqcp@gmail.com", "1234", "㈜인테리어왕", "010-2345-1234", "인천시 남동구", "company", true},
                {"djqcp1@gmail.com", "1234", "㈜이쁜내집찾기", "010-2345-2345", "경기도 시흥시", "company", true},
                {"aktmxj@gmail.com", "1234", "마스터영", "010-3456-1234", "인천시 부평구", "master", true}
        };

        for (Object[] member : members) {
            MemberDto dto = MemberDto.builder()
                    .memail((String)member[0])
                    .mpwd((String)member[1])
                    .mname((String)member[2])
                    .mphone((String)member[3])
                    .maddr((String)member[4])
                    .role((String)member[5])
                    .isapproved((Boolean)member[6])
                    .build();

            boolean result = memberService.signup(dto);
            if (!result) {
                System.out.println("❌ 회원 생성 실패: " + member[0]);
            }
        }

        System.out.println("✅ 필수 회원 데이터 생성 완료");
    }

    // 랜덤 회원 생성
    private void createRandomMembers(int count) {
        for (int i = 0; i < count; i++) {
            String email = "user" + i + "@test.com";
            String password = "Test1234!";
            String name = faker.name().fullName();
            String phone = "010-" + (1000 + random.nextInt(9000)) + "-" + (1000 + random.nextInt(9000));
            String address = faker.address().fullAddress();

            String role = switch (i % 3) {
                case 0 -> "requester";
                case 1 -> "company";
                default -> "master";
            };

            MemberDto dto = MemberDto.builder()
                    .memail(email)
                    .mpwd(password)
                    .mname(name)
                    .mphone(phone)
                    .maddr(address)
                    .role(role)
                    .isapproved(true)
                    .build();

            boolean result = memberService.signup(dto);
            if (!result) {
                System.out.println("❌ 회원 생성 실패: " + email);
            }
        }

        System.out.println("✅ 랜덤 회원 데이터 생성 완료");
    }

    // 필수 서비스 요청서 생성
    @Transactional
    private void createServiceRequests() {
        // 필수 서비스 요청 정보 (일부만 포함)
        Object[][] requests = {
                {2, "거실 청소 요청", "거실에 먼지 제거 및 청소를 요청합니다.", "4", "서울시 강남구 테헤란로 120", 37.3584, 126.7834, true, 2},
                {3, "안방 청소 요청", "안방의 먼지 제거 및 바닥 청소 요청.", "2", "부산시 해운대구 해운대해변로 120", 37.4810, 126.6353, false, 1},
                {4, "주방 청소 요청", "주방 바닥과 조리대 청소를 부탁드립니다.", "1", "대전시 유성구 가정로 200", 37.4848, 126.7596, true, 2},
                {5, "욕실 청소 요청", "욕실 타일과 변기 청소를 요청합니다.", "7", "광주광역시 북구 삼각산로 300", 37.4841, 126.7241, false, 1},
                {6, "거실 청소 요청", "거실의 카펫과 소파 청소를 요청합니다.", "4", "대구시 수성구 알파로 400", 37.3917, 126.9305, true, 2}
        };

        for (Object[] req : requests) {
            // 회원 조회
            Optional<MemberEntity> memberOpt = memberRepository.findById(((Integer)req[0]));
            if (memberOpt.isEmpty()) {
                System.out.println("❌ mno " + req[0] + "인 회원이 없어 요청 생성 실패");
                continue;
            }

            // RequestEntity 직접 생성 및 저장 (세션을 사용하지 않는 방식)
            RequestEntity requestEntity = RequestEntity.builder()
                    .memberEntity(memberOpt.get())
                    .reqtitle((String)req[1])
                    .reqcontent((String)req[2])
                    .reqspace((String)req[3])
                    .raddress((String)req[4])
                    .latitude((Double)req[5])
                    .longitude((Double)req[6])
                    .reqstate((Boolean)req[7])
                    .reqrole((Integer)req[8])
                    .build();

            requestRepository.save(requestEntity);
        }

        System.out.println("✅ 필수 서비스 요청 데이터 생성 완료");
    }

    // 랜덤 요청서 생성
    @Transactional
    private void createRandomRequests(int count) {
        List<MemberEntity> requesters = memberRepository.findAll().stream()
                .filter(m -> "requester".equals(m.getRole()))
                .collect(Collectors.toList());

        if (requesters.isEmpty()) {
            System.out.println("❌ 요청서를 생성할 requester 회원이 없습니다.");
            return;
        }

        String[] spaces = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] titles = {
                "거실 청소 요청", "안방 청소 요청", "주방 청소 요청", "욕실 청소 요청",
                "전체 청소 요청", "침실 청소 요청", "정리수납 요청", "가전제품 청소 요청"
        };

        for (int i = 0; i < count; i++) {
            MemberEntity member = requesters.get(i % requesters.size()); // 골고루 배분

            // RequestEntity 직접 생성 및 저장 (세션을 사용하지 않는 방식)
            RequestEntity requestEntity = RequestEntity.builder()
                    .memberEntity(member)
                    .reqtitle(titles[random.nextInt(titles.length)])
                    .reqcontent(faker.lorem().paragraph(2))
                    .reqspace(spaces[random.nextInt(spaces.length)])
                    .raddress(faker.address().fullAddress())
                    .latitude(37.0 + random.nextDouble() * 1.5) // 한국 위도 범위 내에서 임의 생성
                    .longitude(126.0 + random.nextDouble() * 3.0) // 한국 경도 범위 내에서 임의 생성
                    .reqstate(random.nextBoolean())
                    .reqrole(random.nextInt(3))
                    .build();

            requestRepository.save(requestEntity);
        }

        System.out.println("✅ 랜덤 요청서 데이터 생성 완료");
    }
}