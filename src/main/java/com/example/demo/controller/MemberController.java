package com.example.demo.controller;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.PathVariable;
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
			//対象のメンバーを取得、役職と事業所をsaveMemberにセットする（リダイレクト先の画面で表示するため）
			saveMember = memberService.getMember(memberForm.getMemberId());
			saveMember.setMstPlace(memberService.placeById(saveMember.getPlaceId()));
			saveMember.setMstPosition(memberService.positionById(saveMember.getPositionId()));
		}catch(NotFoundException e) {
			//メンバー取得エラーの場合エラーメッセージをセットしてerror.htmlを表示する
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		//役職と事業所をあらかじめセットしたsaveMemberをリダイレクト先に渡す
		redirectAttribute.addFlashAttribute("saveMember",saveMember);
		
		return "redirect:/addComp";
	}
	
	/** 
	 * 新規登録時のリダイレクト処理
	 * 
	 * */
	@GetMapping("/addComp")
	private String addComp(Model model) {
		//Springの仕様でフラッシュスコープ（addFlashAttribute）で渡したデータは、
		//リダイレクト先では何もしなくても自動で Model の中に格納される。
		//そのため「@ModelAttribute」は不要
		
		
		/*
		 * ModelAttributeでフラッシュスコープのオブジェクトをmodelにセットする
		 * viewを返す
		 * */
		
		/*
		 リダイレクトもとで取得する際にOptionalでnullチェックはしているのでここでは不要
		 if(Objects.isNull(member)) {
			model.addAttribute("errorMessage", "対象のメンバー情報が存在しません");
			return "error";
		}
		 * */
		
		/*
		 リダイレクトもとで役職や事業所もsaveMemberにセットしているのでここでの取得は不要
		 try {
			model.addAttribute("place", memberService.placeById(member.getPlaceId()));
			model.addAttribute("position", memberService.positionById(member.getPositionId()));
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		 
		 * */
		
		return "addComp";
		
	}
	
	
	/** 
	 * 詳細画面表示処理
	 * @param id メンバーID
	 * 
	 * */
	@GetMapping("/member/detail/{id}")
	private String memberDetail(@PathVariable String id, Model model) {
		/*
		 URLからIDを取得する
		 IDを条件にDBからメンバー情報を取得する
		 役職、事業所も取得してmemberにセットする
		 memberをmodelにセットしてViewを返す
		 * */
		
		MemberDto memberDto = null;
		
		try {
			memberDto = memberService.getMember(id);
			memberDto.setMstPlace(memberService.placeById(memberDto.getPlaceId()));
			memberDto.setMstPosition(memberService.positionById(memberDto.getPositionId()));
		} catch(NotFoundException e) {
			//メンバー取得エラーの場合エラーメッセージをセットしてerror.htmlを表示する
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		model.addAttribute("member", memberDto);
		
		return "detail";
	}
	
	
	/** 
	 * 更新画面表示処理
	 * @param id メンバーID
	 * 
	 * */
	@GetMapping("/member/update/{id}")
	private String memberUpdate(@PathVariable String id, Model model) {
		/*
		 IDを受け取る
		 IDを条件にDBからデータを取得する
		 取得したメンバー情報の役職IDと事業所IDを条件にデータを取得する
		 DtoをFormに変換する
		 役職と事業所のリストを取得する
		 modelにセットする
		 Viewを返す
		 * */
		MemberDto memberDto = null;
		
		try {
			memberDto = memberService.getMember(id);
			memberDto.setMstPlace(memberService.placeById(memberDto.getPlaceId()));
			memberDto.setMstPosition(memberService.positionById(memberDto.getPositionId()));
		}catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		MemberForm memberForm = MemberDto.convertDtoToForm(memberDto);
		model.addAttribute("memberForm", memberForm);
		//更新画面で事業所と役職のプルダウン表示用に取得
		model.addAttribute("places", memberService.getPlace());
		model.addAttribute("positions", memberService.getPosition());
		
		return "update";
	}
	
	
	/** 
	 * 更新確認画面表示処理
	 * @param form 更新情報
	 * @param result バリデーションのチェック結果
	 * @param redirect リダイレクト用のパラメータ
	 * 
	 * */
	@PostMapping("member/update")
	private String updateConf(@Valid @ModelAttribute("memberForm") MemberForm form, BindingResult result, Model model) {
		/*
		 バリデーションチェック
		 チェックでエラーの場合、エラーメッセージをセットして前画面を表示する
		 チェックでエラーがなければ、そのまま確認画面を表示する
		 * */
		
		if(result.hasErrors()) {
			model.addAttribute("places", memberService.getPlace());
			model.addAttribute("positions", memberService.getPosition());
			return "update";
		}
		
		try {
			//formオブジェクトに役職と事業所をセットすることでmodelにも反映される
			form.setPlace(memberService.placeById(form.getPlaceId()));
			form.setPosition(memberService.positionById(form.getPositionId()));
		}catch(NotFoundException e) {
			//エラーメッセージをセットしてerror.htmlを表示する
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		return "updateConf";
	}
	
	
	/** 
	 * 更新処理
	 * @param form 更新するメンバー情報
	 * @param redirect リダイレクト先に渡すパラメータ
	 * 
	 * */
	@PostMapping("member/update/conf")
	private String update(@ModelAttribute("memberForm") MemberForm form, RedirectAttributes redirect, Model model) {
		/*
		 formをDtoに変換
		 DBから更新する前のメンバー情報を取得する（Dto形式）
		 formとoldMemberDtoを変換メソッドし更新後のDtoを取得
		 受け取ったMemberDtoに役職と事業所をセットする
		 Dtoを引数で渡してDBにデータを登録
		 再度DBからデータを取得する
		 役職と事業をセット
		 リダイレクトする
		 * */
		
		MemberDto memberDto = MemberDto.convertFormToDto(form);
		MemberDto oldMemberDto = null;
		
		try {
			oldMemberDto = memberService.getMember(form.getMemberId());
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		//更新前と画面入力されたデータをマージする
		MemberDto mergeMemberDto = MemberDto.memberDto(memberDto, oldMemberDto);
		
		//この中でsaveメソッドを使っている
		memberService.insert(mergeMemberDto);
		
		//idをリダイレクト先に渡す
		redirect.addFlashAttribute("memberId",mergeMemberDto.getMemberId());
		
		return "redirect:/updateComp";
		
	}
	
	
	/** 
	 * 更新時のリダイレクト処理
	 * 
	 * */
	@GetMapping("/updateComp")
	private String updateComp(Model model) {
		/*
		 modelにメンバーIDが格納されている
		 メンバーIDを条件に最新のデータをDBから取得する
		 それぞれ役職と事業所もセットする
		 Viewを返す
		 * */
		
		MemberDto updateMemberDto = null;
		
		try {
			updateMemberDto = memberService.getMember((String)model.getAttribute("memberId"));
			updateMemberDto.setMstPlace(memberService.placeById(updateMemberDto.getPlaceId()));
			updateMemberDto.setMstPosition(memberService.positionById(updateMemberDto.getPositionId()));
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		model.addAttribute("updateMember", updateMemberDto);
		
		return "updateComp";
	}
	
	/** 
	 * 削除確認画面表示処理
	 * @param id メンバーID
	 * 
	 * */
	@GetMapping("/member/delete/{id}")
	private String deleteConf(@PathVariable String id, Model model) {
		/*
		 URLからidを受け取る
		 受け取ったidを条件にメンバー情報を取得する（Dto形式）
		 メンバー情報の役職と事業ををセットする
		 modelにaddAttributeする
		 Viewを返す
		 * */
		
		MemberDto memberDto = null;
		
		try {
			memberDto = memberService.getMember(id);
			memberDto.setMstPlace(memberService.placeById(memberDto.getPlaceId()));
			memberDto.setMstPosition(memberService.positionById(memberDto.getPositionId()));
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		model.addAttribute("member", memberDto);
		
		return "deleteConf";
	}
	
	
	/** 
	 * 削除処理
	 * @param 
	 * 
	 * */
	@GetMapping("/member/delete/comp/{id}")
	private String delete(@PathVariable String id, RedirectAttributes redirect, Model model) {
		/*
		 URLからidを受け取る
		 受け取ったidを条件にメンバー情報を取得する（Dto形式）
		 メンバー情報の役職と事業ををセットする
		 受け取ったidを条件に対象データ削除する
		 Viewを返す
		 * */
		
		MemberDto memberDto = null;
		
		try {
			memberDto = memberService.getMember(id);
			memberDto.setMstPlace(memberService.placeById(memberDto.getPlaceId()));
			memberDto.setMstPosition(memberService.positionById(memberDto.getPositionId()));
		} catch(NotFoundException e) {
			model.addAttribute("errorMessage", "対象が存在しません、または削除されています");
			return "error";
		}
		
		memberService.delete(id);
		
		//リダイレクト先ではデータの取得ができないのでフラッシュスコープにセットしリダイレクト先に渡す。
		redirect.addFlashAttribute("member", memberDto);
		redirect.addFlashAttribute("memberId", memberDto.getMemberId());
		
		return "redirect:/deleteComp";
		
	}
	
	
	/** 
	 * 削除時のリダイレクト処理
	 * 
	 * */
	@GetMapping("/deleteComp")
	private String deleteComp(Model model) {
		//DBにデータが存在しないことを確認後Viewを返す
		
		String memberId = (String)model.getAttribute("memberId");
		
		//リロード時のメッセージ表示
		if(Objects.isNull(memberId)) {
			model.addAttribute("errorMessage", "この手続きはすでに完了しているか、URLが正しくありません。");
			return "error";
		}
		
		if(memberService.checkMember(memberId)) {
			model.addAttribute("errorMessage", "データが適切に削除されていません。");
			return "error";
		}
		
		return "deleteComp";
		
	}
}






















