package com.bulletinboard.member.service;

import com.bulletinboard.member.domain.Member;
import com.bulletinboard.member.dto.MemberNewRequest;
import com.bulletinboard.member.exception.InvalidMemberEmailException;
import com.bulletinboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberNewRequest memberNewRequest) {
        validateDuplicateEmail(memberNewRequest);

        Member member = memberNewRequest.toEntity(memberNewRequest);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void validateDuplicateEmail(MemberNewRequest memberNewRequest) {
        if (memberRepository.existsByEmail(memberNewRequest.getEmail())) {
            throw new InvalidMemberEmailException("중복된 이메일입니다.");
        }
    }
}
