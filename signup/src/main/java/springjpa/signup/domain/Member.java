package springjpa.signup.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String phone;
    private String email;
    @Column(length = 500)
    private String password;

    @Embedded
    private Address address;
}
