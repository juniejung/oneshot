package himedia.oneshot.service;

import himedia.oneshot.dto.LoginDTO;
import himedia.oneshot.dto.MemberDTO;
import himedia.oneshot.entity.Member;
import himedia.oneshot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 로그인 정보를 컨트롤러의 모델에 넣어주는 메소드입니다.
     * LoginDTO의 필드를 사용할 수 있습니다.
     * @param request 클라이언트에게 요청받은 정보
     * @param model 로그인 정보를 컨트롤러에서 넘겨주기 위한 모델 타입
     * @see LoginDTO
     */
    public Boolean loginCheck(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        if (session.getAttribute("user")!=null){
            LoginDTO loginUser = (LoginDTO) session.getAttribute("user");
            model.addAttribute("user", loginUser);
            return loginUser.getLoginSuccess();
        }
        else {
            LoginDTO loginUser = new LoginDTO();
            model.addAttribute("user", loginUser);
            session.setAttribute("user", loginUser);
            return false;
        }
    }

    public void loginCheck(HttpServletRequest request, Model model, LoginDTO loginData){
        LoginDTO loginResult = new LoginDTO();
        Optional<Member> loginMember = memberRepository.findByLoginId(loginData.getLoginId());

        if (loginMember.isPresent() && loginMember.get().getPw().equals(loginData.getPw())) {
            Member member = loginMember.get();
            long id = member.getId();
            String loginId = member.getLogin_id();
            String name = member.getName();
            String auth = member.getAuthority();
            loginResult = new LoginDTO(id, loginId, name, auth, true);
        }
        request.getSession().setAttribute("user", loginResult);
        model.addAttribute("user", loginResult);
    }

    public MemberDTO findLoginId(MemberDTO info){
        String name = info.getName();
        String email = info.getEmail();
        String birthday = info.getIdCardNumber();
        MemberDTO member = new MemberDTO();
        Optional<Member> result = memberRepository.findLoginId(name, email, birthday);
        result.ifPresent(value -> member.setLoginId(value.getLogin_id()));
        return member;
    }
    public MemberDTO findPassword(MemberDTO info){
        String loginId = info.getLoginId();
        String name = info.getName();
        String birthday = info.getIdCardNumber();
        String email = info.getEmail();
        Optional<Member> result = memberRepository.findPassword(loginId, name, birthday, email);
        MemberDTO member = new MemberDTO();
        result.ifPresent(value -> member.setId(value.getId()));
        return member;
    }

//    /**
//     * 로그인 기능 테스트 확인용입니다.
//     * 사용하지 마십시오.
//     */
//    public LoginDTO loginCheck(LoginDTO loginData){
//        Optional<Member> loginMember = memberRepository.findByLoginId(loginData.getLoginId());
//
//        if (loginMember.isPresent() && loginMember.get().getPw().equals(loginData.getPw())) {
//            Member member = loginMember.get();
//            long id = member.getId();
//            String loginId = member.getLogin_id();
//            String name = member.getName();
//            String auth = member.getAuthority();
//            LoginDTO loginResult = new LoginDTO(id, loginId, name, auth, true);
//            return loginResult;
//        } else
//            return new LoginDTO();
//    }
}
