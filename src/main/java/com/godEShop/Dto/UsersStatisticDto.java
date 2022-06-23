package com.godEShop.Dto;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersStatisticDto implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String username;
    private String fullname;
    private String image;
    private Long orders;
    private double amount;
}
