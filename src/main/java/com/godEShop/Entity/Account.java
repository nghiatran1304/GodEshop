package com.godEShop.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "Username")
    private String username;
    
    @Column(name = "Password")
    private String password;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateDate")
    private Date createDate = new Date();
    
    @Column(name = "IsDeleted")
    private Boolean isDeleted;
    
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Authority> authorities;
    
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<RefAccount> refAccounts;
    
    @JsonIgnore
    @OneToOne(mappedBy = "accountUser")
    private User user;
    

}
