package com.tgirard12.jugkotlinandroid;

public class Owner {

    private String login;
    private long id;
    private String avatar_url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (id != owner.id) return false;
        if (login != null ? !login.equals(owner.login) : owner.login != null) return false;
        return avatar_url != null ? avatar_url.equals(owner.avatar_url) : owner.avatar_url == null;

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (avatar_url != null ? avatar_url.hashCode() : 0);
        return result;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
