package cn.geny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;

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

    @DeleteMapping("/delete/{key}")
    public String delete(@PathVariable("key") String key){
        return map.remove(key);
    }

    @GetMapping("/failPieces/{key}/{total}")
    public LinkedList<Integer> getFailPiece(@PathVariable("key") String key,@PathVariable("total") Integer total){
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 1; i <= total; i++) {
            if (!map.containsKey(key+"_" +i)){
                linkedList.add(i);
            }
        }
        return linkedList;
    }
}
