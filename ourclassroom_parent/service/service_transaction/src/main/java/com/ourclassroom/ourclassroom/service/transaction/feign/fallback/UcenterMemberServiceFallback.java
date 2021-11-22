package com.ourclassroom.ourclassroom.service.transaction.feign.fallback;

import com.ourclassroom.ourclassroom.service.base.dto.MemberDto;
import com.ourclassroom.ourclassroom.service.transaction.feign.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UcenterMemberServiceFallback implements UcenterMemberService {
    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        log.info("Degrade protection");
        return null;
    }
}
