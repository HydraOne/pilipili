package cn.geny.pilipili.mapper;

import cn.geny.pilipili.entity.Files;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);
}