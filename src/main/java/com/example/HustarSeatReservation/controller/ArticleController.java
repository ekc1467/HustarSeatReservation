package com.example.HustarSeatReservation.controller;

import com.example.HustarSeatReservation.dto.ArticleForm;
import com.example.HustarSeatReservation.entity.Article;
import com.example.HustarSeatReservation.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor // final 필드 값을 알아서 가져옴! (@autowired 대체!)
@Controller // 컨트롤러 선언! 요청과 응답을 처리!
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/articles")
    public String index(Model model) { // 뷰 페이지로 데이터 전달을 위한 Model 객체 자동 삽입 됨!
        // 모든 Article을 가져옴
        // Iterable 인터페이스는 ArrayList의 부모 인터페이스
        Iterable<Article> articleList = articleRepository.findAll();
        // 뷰 페이지로 articles 전달!
        model.addAttribute("articles", articleList);
        // 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        // article 가져오기!
        Article article = articleRepository.findById(id)
                .orElseThrow( // 없다면, 에러 발생!
                        () -> new IllegalArgumentException("해당 Article이 없습니다.")
                );
        // article을 뷰 페이지로 전달
        model.addAttribute("article", article);
        return "articles/show";
    }



    @GetMapping("/articles/new") // GET 요청이 해당 url로 오면, 아래 메소드를 수행!
    public String newArticle() {
        return "articles/new"; // 보여줄 뷰 페이지
    }

    @PostMapping("/articles") // Post 방식으로 "/articles" 요청이 들어오면, 아래 메소드 수행!
    public String create(ArticleForm form) { // 폼 태그의 데이터가 ArticleForm 객체로 만들어 짐!
        log.info(form.toString()); // ArticleForm 객체 정보를 확인!
        return "redirect:/articles"; // 브라우저를 "/articles" url로 보냄!
    }

    @GetMapping("/articles/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model) {
        Article target = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 Article이 없습니다.")
        );
        model.addAttribute("article", target);
        return "articles/edit";
    }




}