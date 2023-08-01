package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i%10)).build();

            Board result = boardRepository.save(board);
            log.info("bno : " + result.getBno());
        });
    }

    @Test
    public void testRead(){
        Long bno = 3L;
        Optional<Board> byId = boardRepository.findById(bno);

        Board board = byId.orElseThrow();
        log.info(board);
    }

    // title : title...100
//    @Test
//    public void testTitle(){
//        String title = "title...100";
//        Board byTitle = boardRepository.findByTitle(title);
//        log.info(byTitle);
//    }


    @Test
    public void testDelete() {

        Optional<Board> Id = boardRepository.findById(3L);

        Board board = Id.orElseThrow();

        boardRepository.delete(board);
    }


    @Test
    public void testPaging() {

        PageRequest request = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> page = boardRepository.findAll(request);
        log.info(page.getTotalPages());
        log.info(page.getSize());
        log.info(page.getTotalElements());
        log.info(page.getNumber());

        page.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testWriter() {
        boardRepository.findByWriter("user5").forEach(
                board -> log.info("board :" + board)
        );
    }

    @Test
    public void testWriterAndContent() {

        boardRepository.findByWriterAndContent("user5", "content...75")
                .forEach(board -> log.info(board));

    }

    @Test
    public void testBetween() {
        boardRepository.findByBnoBetween(10L, 15L)
                .forEach(board -> log.info(board));
    }

    @Test
    public void testLike() {
        boardRepository.findByWriterLike("5") //5포함한 모두
                .forEach(board -> log.info(board));
    }

    @Test
    public void testContaining() {
        boardRepository.findByWriterContaining("4")
                .forEach(board -> log.info(board));
    }

    @Test
    public void findByBnoLessThanOrderByContentDesc() {
        boardRepository.findByBnoLessThanOrderByContentDesc(100L)
                .forEach(board -> log.info(board));
    }

    @Test
    public void testQuery() {
        boardRepository.findByWriterDetail("2").
                forEach(board -> log.info(board));
    }
    @Test
    public void testQuery2() {
        boardRepository.findByWriterTitleDetail("2", "1").
                forEach(board -> log.info(board));
    }

    @Test
    public void testKeywordPaging() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());

        Page<Board> page = boardRepository.findKeyword("title", pageable);

        log.info(page.getTotalPages());
        log.info(page.getSize());
        log.info(page.getTotalElements());
        log.info(page.getNumber());

        page.getContent().forEach(list -> log.info(list));
    }

    @Test
    public void testOne() {
        Board board = boardRepository.searchBno(3L);
        log.info(board);
    }

    @Test
    public void testByTitle() {
        boardRepository.findByTitle("2").forEach(board -> log.info(board));
    }

//    @Test
//    public void testFindByTitle3() {
//        boardRepository.findByTitle("3").forEach(board -> log.info(Arrays.toString(board)));
//    }

    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
        Page<Board> search1 = boardRepository.search1(pageable);

        log.info("getTotalPages : "+search1.getTotalPages());
        log.info("get : "+ search1.getTotalElements());
        search1.getContent().forEach(list -> log.info(list));
    }

}