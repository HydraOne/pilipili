package cn.geny.pilipili.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(value = "pilipili-tmp")
public interface FileTmpService {
    @PutMapping(value = "fileStorageInfo/put/{key}/{value}")
    String insert(@PathVariable("key") String key, @PathVariable("value") String value);

    @GetMapping(value = "fileStorageInfo/get/{key}")
    String get(@PathVariable("key") String key);
}