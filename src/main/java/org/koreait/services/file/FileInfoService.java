package org.koreait.services.file;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.file.FileEntity;
import org.koreait.entities.file.QFileEntity;
import org.koreait.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.koreait.exceptions.file.FileNotFoundException;

import java.io.File;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
public class FileInfoService {
    @Value("${file.upload.path}")
    private String fileUploadPath;
    private final FileRepository repository;
    private final HttpServletRequest request;

    /**
     * 파일 등록 번호 조회
     * gId + location으로 조회
     * @param id - 등록 번호
     * @return
     */
    public FileEntity get(Long id){
        FileEntity file = repository.findById(id).orElseThrow(FileNotFoundException::new);

        // 파일이 올라간 경로, 파일 접근 URL
        String extension = file.getExtension();
        file.setFilePath(getFilePath(id,extension));
        file.setFileUrl(getFileUrl(id,extension));

        return file;
    }

    public List<FileEntity> gets(String gId){
        return gets(gId,null,true);
    }

    public List<FileEntity> gets(String gId, boolean done){
        return gets(gId, null, done);
    }

    public List<FileEntity> gets(String gId, String location){
        return gets(gId,location,true); // 그룹 작업 완료 파일.
    }

    /**
     *
     * @param gId 그룹 ID
     * @param location 각 파일별 용도 위치
     * @param done 파일 업로드 작업 완료 여부확인값
     * @return
     */
    public List<FileEntity> gets(String gId, String location, boolean done){
        QFileEntity fileEntity = QFileEntity.fileEntity;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileEntity.gId.eq(gId))
                .and(fileEntity.done.eq(done));
        if(location != null && !location.isBlank()){
            builder.and(fileEntity.location.eq(location));
        }

        List<FileEntity> datas = (List<FileEntity>) repository.findAll(builder, Sort.by(asc("createdAt")));

        datas.stream().forEach(data -> {
            Long id = data.getId();
            String extension = data.getExtension();
            data.setFilePath(getFilePath(id,extension));
            data.setFileUrl(getFileUrl(id,extension));
        });

        return datas;
    }

    public String getFilePath(Long id, String extension){ // 서버에 올라간 업로드 경로
        String folderPath = fileUploadPath + getFolder(id);
        File dir = new File(folderPath);
        if(!dir.exists()){
            dir.mkdir();
        }

        String path = folderPath + File.separator + id;
        if(extension != null && !extension.isBlank()) path += "." + extension;

        return path;
    }

    public String getFileUrl(Long id, String extension){ // 브라우저에서 접근하는 Url 경로
        String url = request.getContextPath() + "/uploads/" + getFolder(id) + "/" + id;
        if(extension != null && !extension.isBlank()) url += "." + extension;

        return url;
    }

    private String getFolder(Long id){
        return String.valueOf(id % 10); // 0 - 9 폴더 갯수 10개로 한정
    }

}
