package domain;

import javax.persistence.*;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "GIF_ID")
    private Long gifId;

    // TODO user

    @Column
    private String text;

    public Long getId() { return id; }
    public String getText() { return this.text; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
}
