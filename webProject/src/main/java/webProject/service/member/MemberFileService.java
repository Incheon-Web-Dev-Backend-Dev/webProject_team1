package webProject.service.member;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class MemberFileService {

    String uploadPath = "C:\\Users\\rmsgh\\OneDrive\\Desktop\\프로젝트1\\webProject\\build\\resources\\main\\static\\img\\";

    public String fileUpload(MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "-" + multipartFile.getOriginalFilename().replaceAll("-","_");
        String uploadFile = uploadPath + fileName;

        File file = new File(uploadFile);
        try {multipartFile.transferTo(file);} catch (Exception e){System.out.println(e); return null;}
        return fileName;
    }
}
