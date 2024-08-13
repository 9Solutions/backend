package com.caixadesapato.api.dto.dash;

public class DashUserTokenDTO {

    private Long dashUserId;
    private String email;
    private String token;

    public DashUserTokenDTO(Long dashUserId, String email, String token) {
        this.dashUserId = dashUserId;
        this.email = email;
        this.token = token;
    }

    public DashUserTokenDTO() {
    }

    public Long getDashUserId() {
        return dashUserId;
    }

    public void setDashUserId(Long dashUserId) {
        this.dashUserId = dashUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
