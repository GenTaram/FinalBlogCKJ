package idusw.springboot.ckjblog.serivce;

import idusw.springboot.ckjblog.entity.MemberEntity;
import idusw.springboot.ckjblog.model.MemberDto;
import idusw.springboot.ckjblog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int create(MemberDto memberDto) {
        int ret = 0;
        MemberEntity entity = dtoToEntity(memberDto);
        memberRepository.save(entity);
        ret = 1;
        return ret;
    }

    @Override
    public MemberDto readByIdx(Long idx) {
        if(!memberRepository.findByIdx(idx).isEmpty())
            return entityToDto(memberRepository.findByIdx(idx).get());
        else
            return null;
    }

    @Override
    public List<MemberDto> readAll() {
        List<MemberEntity> list = memberRepository.findAll();
        List<MemberDto> dtoList = new ArrayList<MemberDto>();
        for (MemberEntity memberEntity : list) {
            dtoList.add(entityToDto(memberEntity));
        }
        return dtoList;
    }

    @Override
    public int update(MemberDto memberDto) {
        Optional<MemberEntity> entityOptional = memberRepository.findById(memberDto.getIdx());
        if (entityOptional.isPresent()) {
            MemberEntity entity = entityOptional.get();
            entity.setEmail(memberDto.getEmail());
            entity.setName(memberDto.getName());
            entity.setPw(memberDto.getPw());
            entity.setPhone(memberDto.getPhone());
            entity.setAddress(memberDto.getAddress());
            memberRepository.save(entity);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(MemberDto memberDto) {
        Optional<MemberEntity> entityOptional = memberRepository.findById(memberDto.getIdx());
        if (entityOptional.isPresent()) {
            memberRepository.delete(entityOptional.get());
            return 1;
        }
        return 0;
    }

    @Override
    public MemberDto loginById(MemberDto memberDto) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByIdAndPw(memberDto.getId(), memberDto.getPw());
        return memberEntityOptional.map(this::entityToDto).orElse(null);
    }

    @Override
    public List<MemberDto> findByName(String name) {
        List<MemberEntity> entities = memberRepository.findByName(name);
        List<MemberDto> dtos = new ArrayList<>();
        for (MemberEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

}
