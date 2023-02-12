package com.waither.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OAuthDto {

    private String nickname;
    private String authId;
    private String email;
    private String provider;


}
