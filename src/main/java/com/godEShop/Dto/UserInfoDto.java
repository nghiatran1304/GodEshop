package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    private String accountUsername;
    private Boolean accountIsDeleted;
    private String accountPassword;
    
    private String roleId;
    private String roleName;
    
    private Integer userId;
    private String userAddress;
    private Date userDob;
    private String userEmail;
    private String userFullname;
    private Integer userGender;
    private String userPhone;
    private String userPhoto;
    
    
}
