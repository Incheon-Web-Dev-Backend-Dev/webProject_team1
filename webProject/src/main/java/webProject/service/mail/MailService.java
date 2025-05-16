package webProject.service.mail;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import webProject.model.entity.estimate.EstimateEntity;
import webProject.model.entity.member.MemberEntity;
import webProject.model.entity.request.RequestEntity;
import webProject.model.repository.estimate.EstimateRepository;
import webProject.model.repository.member.MemberRepository;
import webProject.service.estimate.EstimateService;
import webProject.service.member.MemberService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class MailService {

    @Autowired
    private EstimateRepository estimateRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${GMAIL_ID}") // applicatioin.properties에 입력한 변수 사용할 수 있는 value annotation
    private String ADMIN_GOOGLE_EMAIL;

    // QR코드 이메일로 전송하는 기능
    public boolean sendMailQrcode(int estno){
        try{
            // 1. 견적서 정보 조회
            Optional< EstimateEntity> optionalEstimate = estimateRepository.findById(estno);
            if(!optionalEstimate.isPresent()){
                return false;
            }// if end

            EstimateEntity estimateEntity = optionalEstimate.get();
            MemberEntity requestMember = estimateEntity.getRequestEntity().getMemberEntity();

            // 2. QR코드 생성 (qr코드는 byte배열로 저장 됨)
            String reviewUrl = "http://localhost:8080/review/write?estno=" + estno;
            byte[] qrCodeImg = generateQRCode(reviewUrl, 200, 200);

            // 3. MimeMessage 객체 생성
            // * MIME = Multipurpose Internet Mail Extensions, 간단한 메세지가 아니고 html으로 작성되거나 파일 전송할 때 사용
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 3. 이메일 정보 설정
            helper.setFrom(ADMIN_GOOGLE_EMAIL); // 이메일 발송자
            helper.setTo(requestMember.getMemail()); // 이메일 수신자
            helper.setSubject("수납/정리 서비스 이용 후기를 작성해주세요!");

            // 4. 이메일 본문 작성(HTML) 형식
            String mailContent =
                    "<h2>" + requestMember.getMname() + "님, 서비스 이용은 만족스러우셨나요?</h2>" +
                            "<p>소중한 의견을 들려주세요. 아래 QR 코드를 스캔하여 리뷰를 작성해주세요.</p>" +
                            "<div style='margin: 20px 0;'><img src='cid:qrcode' alt='QR Code' style='width: 200px; height: 200px;'/></div>" +
                            "<p>또는 <a href='" + reviewUrl + "'>여기를 클릭</a>하여 리뷰 페이지로 이동할 수 있습니다.</p>";

            helper.setText(mailContent, true);

            // 5. 작성된 본문을 이메일 정보에 주입
            helper.setText(mailContent.toString(), true);

            // 6. QR코드 이미지 첨부
            ByteArrayResource qrCode = new ByteArrayResource(qrCodeImg);
            helper.addInline("qrcode", qrCode, "image/png");

            // 7. 이메일 발송
            javaMailSender.send(message);

            return true;


        } catch (IOException | MessagingException | WriterException e){
            System.out.println("이메일 발송 실패" + e.getMessage());
            e.printStackTrace();
            return false;
        }// try-catch end
    }// sendMailQrcode end

    // QR코드 이미지 생성 기능
    private byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {

        // 1. QR Code - BitMatrix : qr code 정보 생성
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);

        // 2. QR Code 이미지 생성 (1회성)
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }// generateQRCode end

    // 견적서 수신 시 메일 알림
    public boolean sendEstimateNotificationToRequester(EstimateEntity estimateEntity) {
        try {
            RequestEntity request = estimateEntity.getRequestEntity();
            MemberEntity requester = request.getMemberEntity();

            // 메일 내용 구성
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(ADMIN_GOOGLE_EMAIL);
            helper.setTo(requester.getMemail());
            helper.setSubject("새 견적서가 도착했습니다!");
            helper.setText(
                    "<h2>" + requester.getMname() + "님, 새로운 견적서가 도착했습니다.</h2>" +
                            "<p>요청하신 \"" + request.getReqtitle() + "\"에 새로운 견적이 등록되었습니다.</p>" +
                            "<p>서비스 플랫폼에서 확인해보세요!</p>",
                    true
            );

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("견적 알림 메일 발송 실패: " + e.getMessage());
            return false;
        }
    }
}
