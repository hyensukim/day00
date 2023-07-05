package org.koreait.services.file;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.entities.file.FileEntity;
import org.koreait.exceptions.file.AuthorizationException;
import org.koreait.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final FileInfoService infoService;
    private final MemberUtil memberUtil;
    private final FileRepository repository;

    /**
     *  파일 등록 번호로 파일 정보, 업로드 파일 삭제
     * @param id
     */
    public void delete(Long id){
        FileEntity fileInfo = infoService.get(id);

        // 관리자 및 파일을 등록한 사용자만 삭제 가능 여부 확인.
        String createdBy = fileInfo.getCreatedBy();
        if( fileInfo.isDone() && createdBy != null && !memberUtil.isAdmin() &&
                (!memberUtil.isLogin() || !memberUtil.getMember().getMemberId().equals(createdBy))){
            throw new AuthorizationException("File.notYours");
        }

        /**
         * 1. 정보 삭제
         * 2. 실제 파일 삭제
         */

        String filePath = fileInfo.getFilePath();
        repository.delete(fileInfo);
        repository.flush();

        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * gId로 파일 정보, 업로드된 파일 삭제
     * @param gId : 그룹 Id
     */

    public void delete(String gId){
        List<FileEntity> files = infoService.gets(gId);

        files.stream().forEach(f->delete(f.getId()));

    }
}
