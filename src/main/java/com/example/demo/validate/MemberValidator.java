package com.example.demo.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.form.MemberForm;
import com.example.demo.service.MemberService;

/** 
 * メンバー情報のバリデーションクラス
 * 
 * @author ryo_kaminosono
 * 
 * */
@Component
public class MemberValidator implements Validator {
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * コントローラークラスの@initBinderの引数に指定したキー名で、
	 * ModelにaddAttributeされているオブジェクトがバリデーション対象クラスかどうか確認するメソッド.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberForm.class.isAssignableFrom(clazz) ;
	}
	
	/**
	 * カスタムバリデーションメソッド.
	 *
	 * @param Object target 画面入力値
	 * @param Errors errors  バリデーションエラー内容格納クラス
	 **/
	@Override
	public void validate(Object target, Errors errors) {
		
	}

}
