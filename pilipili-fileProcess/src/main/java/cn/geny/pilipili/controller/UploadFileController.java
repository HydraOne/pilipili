package cn.geny.pilipili.controller;

import cn.geny.pilipili.entity.Files;
import cn.geny.pilipili.service.FileService;
import cn.geny.utils.R;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class UploadFileController {
    @Autowired
    FileService fileServiceImpl;

    String filePath = "D:/DevolopSpace/files/";

    @PostMapping("upload/{fileId}/{fileMD5}/{partNum}")
    public R uploadFile(MultipartFile file, @PathVariable("fileId") String fileId, @PathVariable("fileMD5")String fileMD5, @PathVariable("partNum")String partNum) throws IOException, NoSuchAlgorithmException {
        String md5Hex = DigestUtils.md5Hex(file.getInputStream());
        System.out.println(md5Hex + ":" + fileMD5);
        String tmp = fileId + "_" + partNum;
        System.out.println(tmp);
        if (md5Hex.equals(fileMD5)) {
            file.transferTo(new File(filePath + tmp));
        }
        return R.ok().put("partNum", partNum);
//        return null;
    }

    @PostMapping("uploadFileInfo")
    public R uploadFileInfo(String name, Long size) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Files files = new Files();
        files.setId(uuid);
        files.setName(name);
        files.setSize(size);
        files.setCreatedAt(new Date());
        fileServiceImpl.insertSelective(files);
        return R.ok().put("uuid",uuid);
    }

    @PostMapping("merge")
    public String mergeFile(String fileId){
        Files files = fileServiceImpl.selectByPrimaryKey(fileId);
        String fileName = files.getName();
        long totalSize = files.getSize();
        System.out.println(totalSize + "/ 1024*1024 =" + totalSize / (1024*1024));
        long totalPieces = (long) Math.ceil((totalSize / (1024*1024F)));
        System.out.println(totalPieces);
        for (int index=1;index<=totalPieces;index++) {
                try (FileInputStream fis = new FileInputStream(new File(filePath+fileId+"_"+index)); FileOutputStream fos = new FileOutputStream(new File(filePath+fileName),true)) {
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
            } catch (IOException e) {
                // ... handle IO exception
            }
        }
        System.out.println(fileId);
        return null;
    }
}