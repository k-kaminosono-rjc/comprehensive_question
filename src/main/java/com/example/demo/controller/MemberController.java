package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.MemberDto;
import com.example.demo.service.MemberService;

/** 
 * メンバー管理システムのコントローラークラス
 * @author kaminosono
 * */
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	
	
	
	/** 
	 * menu画面を表示する
	 * */
	@GetMapping("/")
	private String index() {
		return "index";
	}
	
	
	/** 
	 * メンバー一覧押下時処理
	 * 
	 * */
	//Getリクエストで("/list")を受け取り、DBの一覧を画面表示させる
	@GetMapping("/list")
	private String list(Model model) {
		//Service層でDBからデータを取得し、Formにセットする
		List<MemberDto> memberDtoList = memberService.getAll();
		
		//modelにキーを指定してセットする
		model.addAttribute("members", memberDtoList);
		
		//Viewをリターンする
		return "list";	
		
	}
}
