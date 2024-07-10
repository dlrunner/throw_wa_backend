package com.project.throw_wa.member.contoller;

import com.project.throw_wa.member.dto.MemberCreateDto;
import com.project.throw_wa.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@Slf4j
@Validated
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/api/signUp")
    @CrossOrigin()
    public ResponseEntity<?> signup(@Valid @RequestBody MemberCreateDto memberCreateDto, BindingResult bindingResult) {
        log.debug("memberCreateDto: {}", memberCreateDto);
        if (bindingResult.hasErrors()) {
            log.error("bindingResult: {}", bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList()
            );
        }
        return memberService.createMember(memberCreateDto);
    }
}
