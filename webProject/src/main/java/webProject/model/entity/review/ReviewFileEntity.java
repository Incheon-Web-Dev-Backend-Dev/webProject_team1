package webProject.model.entity.review;

import jakarta.persistence.*;
import lombok.*;
import webProject.model.entity.BaseTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="revfile")
public class ReviewFileEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int revfno; // 요청파일번호

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String revfname; //요청파일명

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "revno")
    private ReviewEntity reviewEntity;

}
