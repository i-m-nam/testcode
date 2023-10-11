package simple.testtool.service.member;


import simple.testtool.domain.member.Member;
import simple.testtool.domain.study.Study;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {

//    StudyService studyService;

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.empty();
    }

    @Override
    public void validate(Long memberId) {
//        studyService.hi();

    }

    @Override
    public void notify(Study newstudy) {

    }

    @Override
    public void notify(Member member) {

    }
}
