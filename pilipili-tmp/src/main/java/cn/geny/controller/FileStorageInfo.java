package cn.geny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("fileStorageInfo")
public class FileStorageInfo {
    @Autowired
    private HashMap<String, String> map;

    @PutMapping("/put/{key}/{value}")
    public String insert(@PathVariable("key") String key,@PathVariable("value") String value){
        return map.put(key,value);
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key){
        return map.get(key);
    }
}
