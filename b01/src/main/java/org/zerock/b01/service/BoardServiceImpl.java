package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;           // DTO(BoardDTO) --> Entity(Board)로 변환하기 위해서
    private final BoardRepository boardRepository;   // 영속 계층에 저장하기 위해서

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Long bno = boardRepository.save(board).getBno();
        //  --------- DB 저장 -------> 저장된 bno값을 반환
        
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);    // save --> insert, update 두 기능 수행
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();     // t c w
        String keyword = pageRequestDTO.getKeyword();   // java
        Pageable pageable = pageRequestDTO.getPageable("bno");  // page 정보 / 0, 10, bno 기준으로 정렬
                                                                // 중복 발생시 가변인자라서 "bno"뒤에 "정렬기준" 더 추가해도 됨

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // 확인 검색...
        log.info("--------------------------------------");
        log.info("aaa getTotalPages : " + result.getTotalPages());
        log.info("aaa getSize : " + result.getSize());
        log.info("aaa getTotalElements : " + result.getTotalElements());
        result.getContent().forEach(board -> log.info(board));
        log.info("--------------------------------------");


        // ---------------------------------------------
        // board(entity) ---> boardDTO로 변환 (mapper)
        List<BoardDTO> dtoList = result.getContent().stream()   // 전체 데이터 스트림에 흘려보내!
                .map(board -> modelMapper.map(board, BoardDTO.class))   // board를 boardDTO로 변환하면서 나와!
                .collect(Collectors.toList());  // Collectors가 스트림에서 나오는걸 모아서 List로 만들어줌

        PageResponseDTO<BoardDTO> pageResponseDTO =
                new PageResponseDTO<>(pageRequestDTO, dtoList, (int)result.getTotalElements());

        return pageResponseDTO;



//        return PageResponseDTO.<BoardDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
    }


}
