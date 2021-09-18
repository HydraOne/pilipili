package cn.geny.pilipili.service.impl;

import cn.geny.pilipili.entity.Files;
import cn.geny.pilipili.mapper.FilesMapper;
import cn.geny.pilipili.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FilesMapper filesMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return filesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Files record) {
        return filesMapper.insert(record);
    }

    @Override
    public int insertSelective(Files record) {
        return filesMapper.insertSelective(record);
    }

    @Override
    public Files selectByPrimaryKey(String id) {
        return filesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Files record) {
        return filesMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Files record) {
        return filesMapper.updateByPrimaryKey(record);
    }
}
