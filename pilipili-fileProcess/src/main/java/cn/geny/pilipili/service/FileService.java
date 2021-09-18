package cn.geny.pilipili.service;

import cn.geny.pilipili.entity.Files;

public interface FileService {
    int deleteByPrimaryKey(String id);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);
}
