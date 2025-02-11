package webProject.service.job;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class JobFileService {

    String uploadPath = "C:\\Users\\User\\Desktop\\정리수납가\\webProject\\src\\main\\resources\\static\\img\\joboffer\\";

    public String fileUpload(MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "-" + multipartFile.getOriginalFilename().replaceAll("-","_");
        String uploadFile = uploadPath+fileName;

        File file = new File(uploadFile);
        try {multipartFile.transferTo(file);
        } catch (Exception e) {
            System.out.println("파일 업로드 실패 : " + e); return null;
        }
        return fileName;
    }

}
