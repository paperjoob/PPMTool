package io.seeyang.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// user details stores user information which is later encapsulated into Authentication objects.
// This allows non-security related user information (such as email addresses, telephone numbers etc) to be stored in a convenient location.
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email") // make sure the username is an email
    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @NotBlank(message = "Password field is required")
    private String password;

    @Transient // not persist this attribute to the database. just make sure this matches the password
    private String confirmPassword;

    private Date create_At; // date
    private Date update_At; // date

    // OneToMany with Project
    // orphan removal = if you delete a user, delete all projects associated with user
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    // no arguments constructor
    public User() {

    }

    // GETTER AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    // every time you create an object, the method should be called before the entity is inserted into the database
    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    // triggered before an entity has been updated in the database
    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    // USER DETAILS INTERFACE METHODS

    @Override
    @JsonIgnore // hide the overrides from the database
    public Collection<? extends GrantedAuthority> getAuthorities() { // role based
        return null;
    }

    @Override
    @JsonIgnore // hide the overrides from the database
    public boolean isAccountNonExpired() { // if you want to add additional logic like account expired for not paying
        return true;
    }

    @Override
    @JsonIgnore // hide the overrides from the database
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore // hide the overrides from the database
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore // hide the overrides from the database
    public boolean isEnabled() { // account is enabled
        return true;
    }
}
