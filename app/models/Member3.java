package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import play.db.ebean.Model;
import tools.StringUtil;

import com.avaje.ebean.Ebean;

@Entity
public class Member3 extends Model {
    private static final long serialVersionUID = 1L;

    private static String passwordSeed = "danslajungleterriblejungle";

    @Id
    public long id;
    @Column(unique = true)
    public String username;
    @Column(unique = true)
    public String email;
    public String password;
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<Member3Todo> todos = new ArrayList<Member3Todo>();

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

    public static Member3Todo addTodo(Member3 member, String s) {
        Member3Todo todo = new Member3Todo(s);
        member.todos.add(todo);
        member.saveManyToManyAssociations("todos");
        member.save();
        return todo;
    }

    public static void removeTodo(Member3 member, Long todoId) {
        Member3Todo todo = Member3Todo.finder.byId(todoId);
        todo.delete();
        member.saveManyToManyAssociations("todos");
        member.save();
    }

    public static Member3 authenticate(String email, String password) {
        return Ebean.find(Member3.class).where().eq("email", email).eq("password", getStoredPassword(password)).findUnique();
    }

    private static String getStoredPassword(String s) {
        return StringUtil.encrypt("SHA1", s, passwordSeed);
    }
}
