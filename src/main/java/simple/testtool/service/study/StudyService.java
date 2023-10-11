package simple.testtool.service.study;

import lombok.RequiredArgsConstructor;
import simple.testtool.domain.member.Member;
import simple.testtool.domain.study.Study;
import simple.testtool.domain.study.StudyRepository;
import simple.testtool.service.member.MemberService;

import java.util.Optional;

@RequiredArgsConstructor
public class StudyService {

    private final MemberService memberService;

    private final StudyRepository repository;



    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        if (member.isPresent()) {
            study.setOwnerId(memberId);
        } else {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
        }
        Study newstudy = repository.save(study);
        memberService.notify(newstudy);
        return newstudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(openedStudy);
        return openedStudy;
    }

    public void hi() {

    }
}
