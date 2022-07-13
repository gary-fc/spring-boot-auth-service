package com.auth.securityuser.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BulletinResponse {
    private Long id;
    private Long accountId;
    private Long senderUserId;
    private String body;
    private Date createdDate;
    private Integer commentsCounter;
}
