package com.bulletinboard.member.controller;

import com.bulletinboard.member.dto.MemberNewRequest;
import com.bulletinboard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberNewRequest memberNewRequest) {
        memberService.saveMember(memberNewRequest);

        return ResponseEntity.created(URI.create("/members/signup")).build();
    }

}
