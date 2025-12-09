package pokladna.shared.templates;

//@formatter:off
/**
 *? Receipt table structure:
 *? ┌─────────────────┐
 *? │ id (UUID)       │
 *? │ content         │
 *? └─────────────────┘
 */
//@formatter:on

public class Reciept {
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
