/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SequenceService
 * Author:   silence
 * Date:     2019/10/10 15:27
 * Description: 序列服务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.service;

import com.silence.robot.mapper.TSequenceMapper;
import com.silence.robot.model.TSequence;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈序列服务〉
 *
 * @author silence
 * @since 2019/10/10
 * 
 */
@Service
public class SequenceService {

    @Resource
    private TSequenceMapper sequenceMapper;

    public synchronized int getSequence(String seqName){
        TSequence sequence = sequenceMapper.selectBySeqName(seqName);
        if(sequence == null){
            sequence = new TSequence();
            sequence.setSeqName(seqName);
            sequence.setSeqValue(1);
            sequenceMapper.insert(sequence);
        }else{
            sequence.setSeqValue(sequence.getSeqValue()+1);
            sequenceMapper.updateByPrimaryKey(sequence);
        }
        return sequence.getSeqValue();
    }

}