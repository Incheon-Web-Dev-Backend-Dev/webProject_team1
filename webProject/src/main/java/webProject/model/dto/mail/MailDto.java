package webProject.model.dto.mail;

import lombok.Data;

@Data
public class MailDto {
    private int id;
    private String name;
    private String email;
    private String message;
}
