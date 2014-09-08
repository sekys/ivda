package sk.stuba.fiit.perconik.ivda.cord.dto;

import com.ibm.icu.util.GregorianCalendar;

import java.io.Serializable;

/**
 * Created by Seky on 5. 9. 2014.
 */
public final class Commit implements Serializable {
    private String hash;
    private String message;
    private GregorianCalendar commitDate;
    private String ancestor1;
    private String ancestor2;
    private Author author;

    public Commit() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GregorianCalendar getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(GregorianCalendar commitDate) {
        this.commitDate = commitDate;
    }

    public String getAncestor1() {
        return ancestor1;
    }

    public void setAncestor1(String ancestor1) {
        this.ancestor1 = ancestor1;
    }

    public String getAncestor2() {
        return ancestor2;
    }

    public void setAncestor2(String ancestor2) {
        this.ancestor2 = ancestor2;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commit commit = (Commit) o;

        if (hash != null ? !hash.equals(commit.hash) : commit.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }
}