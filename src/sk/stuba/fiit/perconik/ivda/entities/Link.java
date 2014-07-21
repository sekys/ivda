package sk.stuba.fiit.perconik.ivda.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.net.URI;

/**
 * Created by Seky on 19. 7. 2014.
 */
public class Link implements Serializable {
    private String Rel;
    private URI Href;

    public Link() {
    }

    public String getRel() {
        return Rel;
    }

    public void setRel(String rel) {
        Rel = rel;
    }

    public URI getHref() {
        return Href;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Href", Href).append("Rel", Rel).toString();
    }

    public void setHref(URI href) {
        Href = href;
    }

}