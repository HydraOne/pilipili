package cn.geny.pilipili.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.LinkedList;

@Component
@FeignClient(value = "pilipili-tmp")
public interface FileTmpService {
    @PutMapping(value = "fileStorageInfo/put/{key}/{value}")
    String insert(@PathVariable("key") String key, @PathVariable("value") String value);

    @GetMapping(value = "fileStorageInfo/get/{key}")
    String get(@PathVariable("key") String key);

    @DeleteMapping("fileStorageInfo/delete/{key}")
    String delete(@PathVariable("key") String key);

    @GetMapping("fileStorageInfo/failPieces/{key}/{total}")
    LinkedList<Integer> getFailPiece(@PathVariable("key") String key, @PathVariable("total") Integer total);
}