package org.koreait.dtos.admin.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberCount {
    private int page = 1;

    private int limit = 20;
}
