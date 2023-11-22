package com.checo.bigevent.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.checo.bigevent.util.ThreadLocalUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        if (metaObject.hasSetter("createUser"))
            metaObject.setValue("createUser", ((Map<String, Objects>) ThreadLocalUtil.get()).get("id"));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
