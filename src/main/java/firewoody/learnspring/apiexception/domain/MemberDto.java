package firewoody.learnspring.apiexception.domain;

import lombok.Data;

@Data
public class MemberDto {

    public String id;

    public MemberDto(String id) {
        this.id = id;
    }
}
