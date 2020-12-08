package org.company.iendo.bean.event;

/**
 * LoveLin
 * <p>
 * Describe
 */
public class RefreshFragmentEvent {
    private String type;

    public RefreshFragmentEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
