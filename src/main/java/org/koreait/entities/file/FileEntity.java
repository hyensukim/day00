package org.koreait.entities.file;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.entities.BaseMemberEntity;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FileEntity extends BaseMemberEntity {

    @Id @GeneratedValue
    private Long id; // 파일 등록번호 - 실제 서버 업로드 파일 (파일 등록번호.확장자)

    @Column(length=45, nullable = false)
    private String gId; // 그룹 ID

    @Column(length = 45)
    private String location; // 파일 세부 용도 위치 - 에디터 / 파일 첨부(attach)

    @Column(length=100, nullable = false)
    private String fileName; // 원본 파일명

    @Column(length=20, nullable=false)
    private String extension; // 확장자명

    @Column(length=120)
    private String contentType; // 파일 형식

    private boolean done; // true - 그룹 작업 완료
    // 게시글 작성 중에 파일을 올린 상태에서 글쓰기 창을 나간 경우, 파일 업로드 실행 여부를 확인하기 위한 구분값.

    @Transient
    private String filePath; // 파일 업로드 경로

    @Transient
    private String fileUrl; // 파일 URL
}
