package idusw.springboot.ckjblog.service;

import idusw.springboot.ckjblog.model.MemberDto;
import idusw.springboot.ckjblog.serivce.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Test
    public void initMembers() {
        IntStream.rangeClosed(1, 9).forEach(i -> {
            MemberDto dto = MemberDto.builder()
                    .id("id-" + i)
                    .pw("cometrue")
                    .name("name-" + i)
                    .email("id-" + i + "@induk.ac.kr")
                    .address("서울시 노원구 "+ i)
                    .phone("010-1234-232"+i)
                    .build();

            memberService.create(dto);
        });
    }
    @Test
    public void readById() {
        MemberDto dto = MemberDto.builder()
                .idx(6L)
                .build();
        MemberDto ret;
        //memberService.readById(dto.getIdx()) ;-> memberService.readByIdx() 변경
        if((ret = memberService.readByIdx(dto.getIdx())) != null)
            System.out.println("success" + ret );
        else
            System.out.println("fail");
    }
    @Test
    public void readList() {
        List<MemberDto> list = memberService.readAll();
        if(list != null) {
            System.out.println("success");
            for(MemberDto dto : list) {
                System.out.println(dto);
            }
        }
        else
            System.out.println("fail");
    }
    @Test
    public void registerOne() {
        MemberDto dto = MemberDto.builder()
                    .id("admin")
                    .pw("cometrue")
                    .name("administrator")
                    .email("admin" + "@induk.ac.kr")
                    .phone("010-1234-5678")
                    .address("서울특별시 노원구 초안산로 12")
                    .build();

        if(memberService.create(dto) > 0)
            System.out.println("success");
        else
            System.out.println("fail");
    }



}
