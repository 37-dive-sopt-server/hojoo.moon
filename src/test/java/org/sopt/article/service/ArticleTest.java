package org.sopt.article.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.article.dto.response.ArticleDetailResponse;
import org.sopt.article.dto.response.ArticleListResponse;
import org.sopt.article.entity.Article;
import org.sopt.article.entity.ArticleTag;
import org.sopt.article.repository.ArticleRepository;
import org.sopt.member.entity.Gender;
import org.sopt.member.entity.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ArticleTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember1;
    private Member testMember2;

    @BeforeEach
    void setUp() {
        testMember1 = Member.create(
                "홍길동",
                LocalDate.of(1990, 1, 1),
                "kim@example.com",
                Gender.MALE
        );
        testMember2 = Member.create(
                "동길홍",
                LocalDate.of(1995, 5, 15),
                "lee@example.com",
                Gender.FEMALE
        );

        memberRepository.save(testMember1);
        memberRepository.save(testMember2);

        for (int i = 1; i <= 10; i++) {
            Member author = (i % 2 == 0) ? testMember1 : testMember2;
            Article article = Article.create(
                    author,
                    "Test Article " + i,
                    "Content " + i,
                    ArticleTag.SPRING
            );
            articleRepository.save(article);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("단일 조회 - N+1 문제 발생")
    void nPlusOneProblem_findById() {
        Article found = articleRepository.findById(49150L).orElseThrow();
        ArticleDetailResponse response = ArticleDetailResponse.from(found);
    }

    @Test
    @DisplayName("전체 조회 - N+1 문제 발생")
    void nPlusOneProblem_findAll() {
        List<ArticleListResponse> responses = articleRepository.findAll()
                .stream()
                .map(ArticleListResponse::from)
                .toList();
    }

    @Test
    @DisplayName("Fetch Join")
    void fetchJoin_oneQuery() {
        List<ArticleListResponse> responses = articleRepository.findAllWithFetchJoin()
                .stream()
                .map(ArticleListResponse::from)
                .toList();
    }

    @Test
    @DisplayName("EntityGraph")
    void entityGraph_oneQuery() {
        List<ArticleListResponse> responses = articleRepository.findAllWithEntityGraph()
                .stream()
                .map(ArticleListResponse::from)
                .toList();
    }

    @Test
    @DisplayName("BatchSize로 완화 - ceil(N/10) + 1번 쿼리")
    void batchSize_improvedQuery() {
        List<ArticleListResponse> responses = articleRepository.findAll()
                .stream()
                .map(ArticleListResponse::from)
                .toList();
    }

}
