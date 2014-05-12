package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import play.db.ebean.Model;

@Entity
public class Member extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public long id;
    @Column(unique = true)
    public String username;
    public String email;
    public String password;

    public Member(String email, String username, String password) {
        this.email = StringUtils.trim(email);
        this.username = username;
        this.password = password;
    }

}
