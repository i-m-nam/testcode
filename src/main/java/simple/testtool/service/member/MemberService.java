package simple.testtool.service.member;


import simple.testtool.domain.member.Member;
import simple.testtool.domain.study.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
