package com.springboot.action.saas.common.exception;

        import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz,
                                   String key,
                                   String value) {
        super(EntityNotFoundException.generateMessage(
                clazz.getSimpleName(),
                key,
                value));
    }
    //转化为字符串，类的名字和k-v结构可变参数
    private static String generateMessage(String entity,
                                          String key,
                                          String value) {
        return StringUtils.capitalize(entity) +
                " 未找到 " +
                key +
                " : " +
                value;
    }

}
