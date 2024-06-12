package idusw.springboot.ckjblog.controller;

import idusw.springboot.ckjblog.model.BlogDto;
import idusw.springboot.ckjblog.repository.BlogRepository;
import idusw.springboot.ckjblog.serivce.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blogs/")
public class BlogController {
    final BlogService blogService;
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }
    @GetMapping("/delete/{idx}")
    public String deleteBlog(@PathVariable Long idx, Model model) {
        //controller가 service에게 요청
        BlogDto dto = BlogDto.builder()
                 .Idx(idx)
                 .build();
        blogService.delete(dto); //repository 호출
        // controller가 view에게 처리 결과 전달
        model.addAttribute("attr-name", "attr-value");
        return "redirect:/";
    }
}
