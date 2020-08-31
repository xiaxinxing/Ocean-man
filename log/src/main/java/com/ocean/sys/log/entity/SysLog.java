package com.ocean.sys.log.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@TableName("sys_log")
public class SysLog {

  @TableId(type=IdType.ASSIGN_UUID)
  private String id;
  private LocalDate requestTime;
  private String url;
  private String requestBody;
  private String ip;




}
