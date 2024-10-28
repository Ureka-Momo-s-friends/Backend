package io.bootify.momo.domain.cat.dto.request;

import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public record PetRequest(
        String petName,
        LocalDate birthDate,
        String breed,
        Boolean gender,
        String profileImgUrl,
        MultipartFile profileImage // 이 필드는 필요할 경우 추가
) {
    // Setter 메서드를 추가
    public void setProfileImgUrl(String profileImgUrl) {
        // 필드의 변경 가능성을 위해, Record 대신 클래스로 선언해야 할 수도 있습니다.
        // profileImgUrl 필드를 설정하는 메서드를 추가합니다.
    }

    // Getter 메서드가 필요하면 여기에 추가하세요.
    public MultipartFile profileImage() {
        return this.profileImage;
    }
}
