package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;

@Entity
public class Member3Todo extends Model {
    private static final long serialVersionUID = 1L;

    public static Finder<Long, Member3Todo> finder = new Finder<Long, Member3Todo>(Long.class, Member3Todo.class);

    @Id
    public long id;
    public String todo;

    public Member3Todo(String todo) {
        this.todo = todo;
    }

    public static List<Member3Todo> findInvolving(String useremail) {
        return Ebean.find(Member3Todo.class).where().eq("members.email", useremail).orderBy("id desc").findList();
    }
}
