package ku.kinkao.service;

import ku.kinkao.entity.Member;
import org.modelmapper.ModelMapper;
import ku.kinkao.dto.SignupRequest;
import ku.kinkao.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Service
public class SignupService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isUsernameAvailable(String username) {
        return repository.findByUsername(username) == null;
    }

    public void createMember(SignupRequest dto) {
        Member new_member = modelMapper.map(dto, Member.class);
        new_member.setCreatedAt(Instant.now());
        new_member.setRole("ROLE_USER");

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        new_member.setPassword(hashedPassword);
        repository.save(new_member);
    }

    public Member getMember(String username) {
        return repository.findByUsername(username);
    }
}
