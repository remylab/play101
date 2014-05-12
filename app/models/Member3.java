package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import play.db.ebean.Model;
import tools.StringUtil;

@Entity
public class Member3 extends Model {
    private static final long serialVersionUID = 1L;

    private static String passwordSeed = "danslajungleterriblejungle";
    public static Finder<String, Member3> find = new Finder<String, Member3>(String.class, Member3.class);

    @Id
    public long id;
    @Column(unique = true)
    public String username;
    @Column(unique = true)
    public String email;
    public String password;

    public Member3(String email, String username, String encPassword) {
        this.email = StringUtils.trim(email);
        this.username = username;
        this.password = encPassword;
    }

    public static Member3 create(String email, String username, String password) {
        Member3 member = new Member3(email, username, getStoredPassword(password));
        member.save();
        return member;
    }

    public static Member3 authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", getStoredPassword(password)).findUnique();
    }

    private static String getStoredPassword(String s) {
        return StringUtil.encrypt("SHA1", s, passwordSeed);
    }
}
