package com.lingyun.user.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class IdEntity {
    @CreatedBy //自动插入操作人
    private String createBy;
    @CreatedDate //自动插入操作时间
    private Timestamp createDate;
    @LastModifiedBy //自动插入操作人
    private String updateBy;
    @LastModifiedDate //有修改时 会自动更新时间
    private Timestamp updateDate;

}
