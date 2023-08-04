package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.b01.dto.ReplyDTO;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    // json 타입으로 들어오는걸 replyDTO가 받아줘야하는데 타입이 다르네?
    // >> 그걸 @RequestBody가 json형태를 객체 replyDTO로 바꿔주는역할, 그래서 없으면 오류남!
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody ReplyDTO replyDTO,
                                                      BindingResult bindingResult) throws BindException{
        log.info("replyDTO =>" + replyDTO);             // @Vaild 이건 유효성 체크 해달란 뜻! replyDTO에 있는 Notnull Notempty같은거

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = Map.of("rno", 1L);

        return ResponseEntity.ok(resultMap);
    }

}
