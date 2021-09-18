package cn.geny.pilipili.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * files
 * @author 
 */
@Data
public class Files implements Serializable {
    private String id;

    private Integer policyId;

    private String picInfo;

    private Long size;

    private Integer folderId;

    private String sourceName;

    private Integer userId;

    private String name;

    private Date updatedAt;

    private Date createdAt;

    private Date deletedAt;

    private static final long serialVersionUID = 1L;
}