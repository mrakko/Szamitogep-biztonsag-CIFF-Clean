package io.swagger.domain;


import javax.persistence.*;

@Entity
public class Auth {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String email;

    private String passwordBCrypt;

}
