package idusw.springboot.ckjblog.controller;

import idusw.springboot.ckjblog.model.BlogDto;
import idusw.springboot.ckjblog.repository.BlogRepository;
import idusw.springboot.ckjblog.serivce.BlogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/blogs/";
    }

    @GetMapping("/reg-form")
    public String getCreateForm(Model model, HttpSession session) {
        Long writerIdx = (Long) session.getAttribute("idx");
        model.addAttribute("blogDto", BlogDto.builder().writerIdx(writerIdx).build());
        return "./blogs/post";
    }

    @PostMapping("/post")
    public String createBlog(@ModelAttribute BlogDto blogDto) {
        blogService.create(blogDto);
        return "redirect:/blogs/";
    }

    @GetMapping("/{idx}")
    public String getBlogDetail(@PathVariable("idx") Long idx, Model model) {
        BlogDto dto = BlogDto.builder()
                .Idx(idx)
                .build();
        model.addAttribute("blog", blogService.read(dto));
        return "./blogs/detail";
    }

    @GetMapping("")
    public String getBlogList(Model model) {
        model.addAttribute("blogs", blogService.readList());
        return "./blogs/list";
    }

    @GetMapping("/edit/{idx}")
    public String editBlogForm(@PathVariable("idx") Long idx, Model model) {
        BlogDto dto = blogService.read(BlogDto.builder().Idx(idx).build());
        model.addAttribute("blogDto", dto);
        return "./blogs/edit";
    }

    @PostMapping("/edit")
    public String updateBlog(@ModelAttribute BlogDto blogDto) {
        blogService.update(blogDto);
        return "redirect:/blogs/";
    }
}
