package cn.geny.pilipili.controller;

import cn.geny.pilipili.entity.Files;
import cn.geny.pilipili.service.FileService;
import cn.geny.pilipili.service.FileTmpService;
import cn.geny.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

@RestController
@RequestMapping("file")
@Slf4j
public class UploadFileController {
    @Autowired
    private FileService fileServiceImpl;
    @Autowired
    private String hostAddress;
    @Autowired
    private FileTmpService fileTmpService;

    @Value("${server.port}")
    private String port;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private String storagePath;

    @PostMapping("upload/{fileId}/{fileMD5}/{partNum}")
    public R uploadFile(MultipartFile file, @PathVariable("fileId") String fileId, @PathVariable("fileMD5") String fileMD5, @PathVariable("partNum") String partNum) throws IOException {
        String md5Hex = DigestUtils.md5Hex(file.getInputStream());
        String fileNum = fileId + "_" + partNum;
        boolean uploadIsSuccess = false;
        if (md5Hex.equals(fileMD5)) {
            try {
                file.transferTo(new File(storagePath + fileNum));
                fileTmpService.insert(fileNum, hostAddress + ":" + port);
                uploadIsSuccess = true;
            } catch (IOException ioException) {
                log.warn("upload file '{} is fail'", fileNum);
            }
        }
        if (uploadIsSuccess) {
            return R.ok().put("fileNum",fileNum);
        } else {
            return R.error("file pieces " + fileNum + "upload is fail ");
        }
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
        return R.ok().put("uuid", uuid);
    }

    @GetMapping("merge")
    public String mergeFile(String fileId) {
        Files files = fileServiceImpl.selectByPrimaryKey(fileId);
        String fileName = files.getName();
        long totalSize = files.getSize();
        long totalPieces = (long) Math.ceil((totalSize / (1024 * 1024F)));
        for (int index = 1; index <= totalPieces; index++) {
            try (FileInputStream fis = new FileInputStream(new File(storagePath + fileId + "_" + index)); FileOutputStream fos = new FileOutputStream(new File(storagePath + fileName), true)) {
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

    @GetMapping("newMerge/{fileNum}")
    public R newMergeFile(@PathVariable("fileNum") String fileNum) {
        Files files = fileServiceImpl.selectByPrimaryKey(fileNum);
        String fileName = files.getName();
        long totalSize = files.getSize();
        int totalPieces = (int) Math.ceil((totalSize / (1024 * 1024F)));
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean mergeIsSuccess = true;
                for (int index = 1; index <= totalPieces; index++) {
                    String hostAddress = fileTmpService.get(fileNum + "_" + index);
                    String resourceURL = "http://" + hostAddress + "/file/getFilePart/" + fileNum + "_" + index;
                    try (FileOutputStream fos = new FileOutputStream(new File(storagePath + fileName), true)) {
                        byte[] cache = restTemplate.getForObject(resourceURL, byte[].class);
                        fos.write(cache);
                    } catch (Exception e) {
                        mergeIsSuccess = false;
                        log.warn("get resource is fail '{}'", resourceURL);
                    }
                }
                if (mergeIsSuccess){
                    log.info(fileNum + " merge is done");
                }else {
                    log.info(fileNum + " merge is fail");
                }
            }
        }).start();
        return R.ok("upload is done");
    }

    @GetMapping("getFilePart/{fileNum}")
    public void getFile(@PathVariable("fileNum") String fileNum,HttpServletResponse response) throws IOException {
        boolean getFilePieceIsSuccess = false;
        try (   // get your file as InputStream
                InputStream is = new FileInputStream(new File(storagePath + fileNum));
        ) {
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException ex) {
            log.warn("File is not found '{}'", fileNum);
        } catch (IOException ex) {
            log.warn("Error writing file to output stream. Filename was '{}'", fileNum);
        }
    }
}