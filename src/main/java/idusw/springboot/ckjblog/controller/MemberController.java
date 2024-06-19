package idusw.springboot.ckjblog.controller;

import idusw.springboot.ckjblog.model.MemberDto;
import idusw.springboot.ckjblog.serivce.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("members/")
public class MemberController {

    final MemberService memberService;
    public MemberController(MemberService memberService) { // 생성자 주입
        this.memberService = memberService;
    }

    @GetMapping("logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("{idx}")
    public String getMemberById(@PathVariable("idx") Long idx, Model model) {
        MemberDto dto = memberService.readByIdx(idx);
        model.addAttribute("dto", dto);
        return "./members/profile";
    }

    @GetMapping("/list")
    public String getMembers(Model model) {
        List<MemberDto> dtoList = memberService.readAll();
        model.addAttribute("dtoList", dtoList);
        return "./members/list";
    }
    // get방식으로 members/login을 요청하면 main/login.html로 이동
    @GetMapping("login")
    public String getLogin(Model model) {
        model.addAttribute("memberDto", MemberDto.builder().build());
        return "./main/login";
    }
    // main/login.html 에서 폼을 통해 post 요청, 처리후 main/index.html로 이동
    @PostMapping("login")
    public String postLogin(@ModelAttribute("memberDto") MemberDto memberDto, Model model, HttpSession session) {
        String id = memberDto.getId();
        String pw = memberDto.getPw();

        MemberDto m = MemberDto.builder()
                .id(id)
                .pw(pw)
                .build();
        String msg = "";

        // Database로 부터 정보를 가져올 예정임
        // 사용자가 제공한 정보와 DB로 부터 가져온 정보를 처리
        // 동작전 로그
        MemberDto ret = memberService.loginById(m);
        // 동작 후 로그
        if(ret != null) {
            session.setAttribute("id", id);
            session.setAttribute("idx", ret.getIdx());
            msg = "로그인 성공";
        } else {
            msg = "로그인 실패";
        }
        model.addAttribute("message", msg );
        return "./errors/error-message";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        model.addAttribute("memberDto", MemberDto.builder().build());
        return "./main/register";
    }

    @PostMapping("register")
    public String postRegister(@ModelAttribute("memberDto") MemberDto memberDto, Model model) {
        System.out.println(memberDto);
        // 등록 처리 -> service -> repository -> service -> controller
        if(memberService.create(memberDto) > 0 ) // 정상적으로 레코드의 변화가 발생하는 경우 영향받는 레코드 수를 반환
            return "redirect:/";
        else
            return "./errors/error-message";
    }

    @GetMapping("/edit/{idx}")
    public String editMemberForm(@PathVariable("idx") Long idx, Model model) {
        MemberDto dto = memberService.readByIdx(idx);
        model.addAttribute("memberDto", dto);
        return "./members/edit";
    }

    @PostMapping("/edit/{idx}")
    public String updateMember(@PathVariable("idx") Long idx, @ModelAttribute("memberDto") MemberDto memberDto) {
        memberDto.setIdx(idx); // URL에서 받은 ID 값을 설정
        memberService.update(memberDto);
        return "redirect:/";
    }

    @GetMapping("/delete/{idx}")
    public String deleteMember(@PathVariable("idx") Long idx, HttpSession session) {
        MemberDto memberDto = memberService.readByIdx(idx);
        if (memberDto != null) {
            memberService.delete(memberDto);
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchMembersByName(@RequestParam(value = "name", required = false) String name, Model model) {
        List<MemberDto> members;
        if (name != null && !name.isEmpty()) {
            members = memberService.findByName(name);
        } else {
            members = memberService.readAll();
        }
        model.addAttribute("members", members);
        return "./members/list";  // list.html 뷰를 반환
    }
}
