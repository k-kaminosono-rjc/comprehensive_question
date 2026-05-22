package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberDto;
import com.example.demo.form.MemberForm;
import com.example.demo.service.MemberService;
import com.example.demo.validate.MemberValidator;

/** 
 * メンバー管理システムのコントローラークラス
 * @author kaminosono
 * */
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberValidator memberValidator;
	
	/**
	 * メソッド実行前処理用メソッド.
	 * Modelにキー名「memberForm」で登録されたオブジェクトが存在する場合に実行されます
	 * 
	 * @param binder
	 */
	@InitBinder("memberForm")
	public void initBinder(WebDataBinder binder) {
		// カスタムバリデーションを実施
		binder.addValidators(this.memberValidator);
	}
	
	/** 
	 * 初期表示画面を表示する
	 * 
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
	
	/** 
	 * 新規登録押下時処理
	 * 
	 * */
	//Getリクエストで("/add")を受け取り、フォームを渡して入力画面を表示する
	@GetMapping("/add")
	private String showAdd(Model model) {
		//formクラスを生成してmodelにセットする
		model.addAttribute("memberForm", new MemberForm());
		
		//役職と事業所を取得してmodelにセットする
		model.addAttribute("places", memberService.getPlace());
		model.addAttribute("positions", memberService.getPosition());
		
		//Viewをリターンする
		return "add";
	}
	
	/** 
	 * 新規登録確認画面表示
	 * @param memberForm 画面で入力されたForm情報
	 * @param result バリデーションチェックの結果
	 * 
	 * */
	@PostMapping("/addConf")
	private String addConf(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult result, Model model) {
		/*
		 * バリデーションチェックでエラーがある場合、再度新規登録画面を表示してエラー内容を表示する
		 * 役職と事業所のリスト表示のため取得してからadd.htmlを表示する
		 * 
		 * Formを受け取ってmodelにセットする
		 * 役職IDと事業所IDを使って再度DBからデータを取得する
		 * DBからデータを取得できなかった場合エラー画面を表示させる
		 * */
		
		if (result.hasErrors()) {
			model.addAttribute("places", memberService.getPlace());
			model.addAttribute("positions", memberService.getPosition());
			return "add";
		}
		
		try {
			model.addAttribute("place", memberService.placeById(memberForm.getPlaceId()));
			model.addAttribute("position", memberService.positionById(memberForm.getPositionId()));
		}catch(NotFoundException e) {
			//エラーメッセージをセットしてerror.htmlを表示する
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		return "addConf";
	}
	
	/** 
	 * 新規メンバー登録処理
	 * @param memberForm 画面入力されたForm情報
	 * @param redirectAttribute リダイレクト用の変数
	 * */
	@PostMapping("/add")
	private String add(@ModelAttribute("memberForm") MemberForm memberForm, RedirectAttributes redirectAttribute,  Model model) {
		/*
		 * Formを受け取ってDtoに変換する
		 * メンバー登録処理を行う
		 * 登録したメンバーを再度DBから取得する（存在チェック）
		 * リダイレクト先に渡すためMemberDtoのオブジェクトをフラッシュスコープに渡す
		 * リダイレクトで「addComp」の処理を起動する
		 * */
		
		MemberDto memberdto = MemberDto.convertFormToDto(memberForm);
		memberService.insert(memberdto);
		
		MemberDto saveMember = null;
		
		try {
			saveMember = memberService.getMember(memberForm.getMemberId());
		}catch(NotFoundException e) {
			//エラーメッセージをセットしてerror.htmlを表示する
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		//リダイレクト先に新規登録したメンバーのオブジェクトを引き継ぐ
		redirectAttribute.addFlashAttribute("saveMember", saveMember);
		
		
		return "redirect:/addComp";
	}
	
	/** 
	 * 新規登録時のリダイレクト処理
	 * 
	 * */
	@GetMapping("/addComp")
	private String addComp(Model model) {
		/*
		 * リダイレクトもとから一時的に渡されたMemberDtoオブジェクトのNullチェック
		 * nullの場合エラー画面を表示する
		 * nullじゃなければ役職と事業所をDBから取得
		 * modelにセットする
		 * viewを返す
		 * */
		
		MemberDto member = (MemberDto)model.getAttribute("saveMember");
		
		/*
		 リダイレクトもとで取得する際にOptionalでnullチェックはしているのでここでは不要
		 if(Objects.isNull(member)) {
			model.addAttribute("errorMessage", "対象のメンバー情報が存在しません");
			return "error";
		}
		 * */
		
		try {
			model.addAttribute("place", memberService.placeById(member.getPlaceId()));
			model.addAttribute("position", memberService.positionById(member.getPositionId()));
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		return "addComp";
		
	}
}






















