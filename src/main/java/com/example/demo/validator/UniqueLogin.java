package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})//当アノテーションが使える場所を指定
@Retention(RetentionPolicy.RUNTIME)//アノテーションの保持期間
@Constraint(validatedBy = UniqueLoginValidator.class)
public @interface UniqueLogin {
	String message() default "このユーザー名は既に登録されています";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};
}

/* @interface 〇〇
 * 〇〇アノテーションを作成
 * 
 * 
 */

