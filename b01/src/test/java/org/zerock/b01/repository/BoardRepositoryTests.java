package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;

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
    @Test
    public void testTitle(){
        String title = "title...100";
        Board byTitle = boardRepository.findByTitle(title);
        log.info(byTitle);
    }
}