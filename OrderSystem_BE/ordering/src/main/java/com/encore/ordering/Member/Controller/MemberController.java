package com.encore.ordering.Member.Controller;

import com.encore.ordering.Member.Domain.Member;
import com.encore.ordering.Member.Dto.*;
import com.encore.ordering.Member.Service.MemberService;
import com.encore.ordering.Common.CommonResponse;
import com.encore.ordering.Ordering.Dto.OrderResDto;
import com.encore.ordering.Ordering.Service.OrderService;
import com.encore.ordering.Securities.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberController(MemberService memberService, OrderService orderService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.orderService = orderService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //로그인
    @PostMapping("/doLogin")
    public ResponseEntity<CommonResponse> memberLogin(@Valid @RequestBody LoginReq loginReq) {
        Member member = memberService.login(loginReq);
        Map<String, Object> member_info = new HashMap<>();
        //토큰 생성 로픽
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        member_info.put("id", member.getId());
        member_info.put("token", jwtToken);
        CommonResponse body = new CommonResponse(HttpStatus.OK, "Login Successfully", member_info);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    //회원가입
    @PostMapping("/member/create")
    public ResponseEntity<CommonResponse> MemberSave(@Valid @RequestBody MemberSaveReq memberSaveReq) {
        Member member = memberService.memberSave(memberSaveReq);
        CommonResponse body = new CommonResponse(HttpStatus.CREATED, "Member Successfully Created", member.getId());
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    //회원 목록 조회
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/members")
    public List<MemberResponseDto> MemberList(MemberListRes memberListRes) {
        List<MemberResponseDto> members = memberService.findAll(memberListRes);
        return members;
    }

    //특정 회원 조회
    @GetMapping("/member/find/{id}")
    public MemberFindRes memberFind(@PathVariable Long id) {
        MemberFindRes memberFindRes = memberService.findDetail(id);
        return memberFindRes;
    }


    @GetMapping("/member/myInfo")
    public MemberResponseDto findMyInfo() {
        return memberService.findMyInfo();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/member/{id}/orders")
    public List<OrderResDto> findMemberOrders(@PathVariable Long id){
        List<OrderResDto> orderResDtoList = orderService.findByMember(id);
        return orderResDtoList;
    }


//    @PreAuthorize(#email == authentication.principal.username")
    @GetMapping("/member/myorders")
    public List<OrderResDto> findMyOrders(){
        List<OrderResDto> orderResDtoList = orderService.findMyOrders();
        return orderResDtoList;
    }
}
