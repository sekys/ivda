package sk.stuba.fiit.perconik.ivda.cord.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Seky on 5. 9. 2014.
 */
public final class Author implements Serializable {
    private String mail;
    private String name;

    public Author() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (mail != null ? !mail.equals(author.mail) : author.mail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mail != null ? mail.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("mail", mail).append("name", name).toString();
    }
}
