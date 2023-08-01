package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;

        // select * from where title like %'1'%
        JPQLQuery<Board> query = from(board);       // select * from board
        query.where(board.title.contains("1"));     // where~
        List<Board> list = query.fetch();           // 전체 데이터
        long count = query.fetchCount();            // 전체 데이터 개수

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        return new PageImpl<>(list, pageable, count);
    }

    @Override                           // tcw          java              0,10,bno.desc
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        // select * from board
        // where
        // (title = %java% or content = %java% or writer = %java%);
        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(board.bno.gt(0L));

        // select * from board
        // where
        // (title = %java% or content = %java% or writer = %java%)
        // and bno > 0L;


        System.out.println("query===>" + query);
        this.getQuerydsl().applyPagination(pageable, query);



        //paging                            0, 10, bno.desc
        this.getQuerydsl().applyPagination(pageable, query);


        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }
}
