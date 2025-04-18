package org.finalwork.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.finalwork.validation.StateValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented // 元注解
@Constraint(validatedBy = { StateValidation.class }) // 指定给注解提供校验规则的名字
@Target({ FIELD }) // 元注解，用到哪些地方
@Retention(RUNTIME) // 元注解，定义该注解在哪个阶段生成保留，此时为运行时产生
public @interface State {
    // 提供校验失败后的提示信息
    String message() default "state参数的值只能是已发布或草稿";
    // 指定分组
    Class<?>[] groups() default { };
    // 负载，获取向State注解的附加信息
    Class<? extends Payload>[] payload() default { };
}
