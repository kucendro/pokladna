package pokladna.shared.templates;

import java.io.Serializable;

//@formatter:off
/**
 *? Receipt table structure:
 *? ┌─────────────────┐
 *? │ id (String)     │
 *? │ content         │
 *? └─────────────────┘
 */
//@formatter:on

public class Reciept implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String content;

    public Reciept(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
