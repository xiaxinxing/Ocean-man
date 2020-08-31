package com.ocean.core.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ocean.core.module.sys.entity.SysDict;
import com.ocean.core.module.sys.mapper.DictMapper;
import com.ocean.core.module.sys.service.IDictService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxx
 * @since 2020-07-29
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, SysDict> implements IDictService {

}
