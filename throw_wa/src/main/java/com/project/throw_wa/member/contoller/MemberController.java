package com.project.throw_wa.member.contoller;

import com.project.throw_wa.member.dto.MemberCreateDto;
import com.project.throw_wa.member.entity.Member;
import com.project.throw_wa.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/member")
@Slf4j
@Validated
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signUp")
    public ResponseEntity<?> signup(@RequestBody MemberCreateDto memberCreateDto, BindingResult bindingResult) {
        log.debug("memberCreateDto: {}", memberCreateDto);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList()
            );
        }
        Member member = memberCreateDto.toMember();
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        log.debug("member: {}", member);

        memberService.createMember(member);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
}
