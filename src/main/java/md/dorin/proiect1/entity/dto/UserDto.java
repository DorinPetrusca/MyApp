package md.dorin.proiect1.entity.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

//import org.hibernate.validator.constraints.Length;

@Table
public class UserDto {

    @NotBlank(message = "Username should not be blank or empty")
    private String username;

    @NotBlank(message = "Username should not be blank or empty")
    @Length(min = 2, message = "Password ")
    private String password;

    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
