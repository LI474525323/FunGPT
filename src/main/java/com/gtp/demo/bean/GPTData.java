package com.gtp.demo.bean;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;



@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_session_data")
public class GPTData {
    @TableId(value = "id")
    private String id;

    private String question;
    private String answer;

    public GPTData(String id, String question, String answer, DateTime createTime) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.createTime = createTime;
    }

    public GPTData() {

    }

    /**
     * 创建时间
     */
    @JsonIgnore
    private DateTime createTime;
}
