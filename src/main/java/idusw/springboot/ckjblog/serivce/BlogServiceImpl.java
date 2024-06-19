package idusw.springboot.ckjblog.serivce;

import idusw.springboot.ckjblog.entity.BlogEntity;
import idusw.springboot.ckjblog.entity.MemberEntity;
import idusw.springboot.ckjblog.model.BlogDto;
import idusw.springboot.ckjblog.repository.BlogRepository;
import idusw.springboot.ckjblog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    final BlogRepository blogRepository;
    final MemberRepository memberRepository;
    public BlogServiceImpl(BlogRepository blogRepository, MemberRepository memberRepository) {
        this.blogRepository = blogRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public int create(BlogDto dto) {
        blogRepository.save(dtoToEntity(dto));
        return 1;
    }

    @Override
    @Transactional
    public BlogDto read(BlogDto dto) {
        Optional<BlogEntity> blogEntityOptional = blogRepository.findById(dto.getIdx());
        //Optional<BlogEntity> entity =blogRepository.findByIdx(dto);
        Optional<MemberEntity> memberEntityOptional =
                memberRepository.findByIdx(blogEntityOptional.get().getBlogger().getIdx());
        return entityToDto(blogEntityOptional.get(), memberEntityOptional.get());
    }

    @Override
    public List<BlogDto> readList() {
        // DTO : Controller, DTO/Entity : Service, Entity : Repository
        // Service -> DTO
        List<BlogEntity> blogEntityList = blogRepository.findAll(); // select * from blog;
        List<BlogDto> blogDtoList = new ArrayList<>();
        for(BlogEntity blogEntity : blogEntityList) { // JCF & Builder Pattern
            MemberEntity memberEntity = MemberEntity.builder()
                    .idx(blogEntity.getBlogger().getIdx())
                    .build();

            blogDtoList.add(entityToDto(blogEntity, memberEntity));
        }
        return blogDtoList;
    }

    @Override
    public int update(BlogDto dto) {
        blogRepository.save(dtoToEntity(dto));
        return 0;
    }

    @Override
    public int delete(BlogDto dto) {
        blogRepository.delete(dtoToEntity(dto)); //delete from blog where idx = ~~~
        return 0;
    }
}
