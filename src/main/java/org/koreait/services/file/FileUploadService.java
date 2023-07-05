package org.koreait.services.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.file.FileEntity;
import org.koreait.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileRepository repository;
    @Value("file.upload.path")
    private final String fileUploadPath;
    private final HttpServletRequest request;

    /**
     *
     * @param files
     * @param gId - null || blank인 경우, random하게 하나 생성.
     * @param location
     */
    public List<FileEntity> upload(MultipartFile[] files,String gId, String location){

        gId = gId == null || gId.isBlank() ? UUID.randomUUID().toString() : gId;

        List<FileEntity> datas = new ArrayList<>();
        for(MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            String extension = fileName.lastIndexOf('.') != -1 ? fileName
                    .substring(fileName.lastIndexOf('.') + 1) : null; //
            FileEntity data = FileEntity.builder()
                    .gId(gId)
                    .location(location)
                    .fileName(fileName)
                    .extension(extension)
                    .contentType(file.getContentType())
                    .build();
            data = repository.saveAndFlush(data);

            Long id = data.getId();
            String folder = "" + id % 10;
            String path = fileUploadPath + folder + "/" + id;
            String url = request.getContextPath() + "/uploads/" + folder + "/" + id;
            if(extension != null && !extension.isBlank()) {
                path += "." + extension;
                url += "." + extension;
            }
            data.setFilePath(path);
            data.setFileUrl(url);
            try {
                file.transferTo(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            datas.add(data);
        }


        return datas;
    }

    public void upload(MultipartFile[] files, String gId){
        upload(files, gId, null);
    }

    public void upload(MultipartFile[] files){
        upload(files,null,null);
    }
}
