package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceImplTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample title...")
                .content("Sample content...")
                .writer("user0").build();
  
        // 들어갈 값만 똑같이 집어넣으면 위 4줄과 아래 4줄이 같은 걸 수행함
//        BoardDTO b = new BoardDTO();
//        b.setTitle("aa");
//        b.setContent("cc");
//        b.setWriter("user0");

        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);

            
    }

    @Test
    public void testReadOne(){
        BoardDTO boardDTO = boardService.readOne(1L);
        log.info("boardDTO : " + boardDTO);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(1L).title("수정 테스트").content("수정 내용")
                .build();
        boardService.modify(boardDTO);
    }

    @Test
    public void testRemove(){
        boardService.remove(99L);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .type("tcw")
                        .keyword("1")
                        .page(1)
                        .size(10)
                        .build();
        // 위 내용이나 아래 내용이나 같은 기능, 위는 bulider(), 아래는 set
        PageRequestDTO pageRequestDTO2 = new PageRequestDTO();
        pageRequestDTO2.setType("tcw");
        pageRequestDTO2.setKeyword("1");
        pageRequestDTO2.setPage(1);
        pageRequestDTO2.setSize(10);

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info("responseDTO===> " + responseDTO);
    }
}