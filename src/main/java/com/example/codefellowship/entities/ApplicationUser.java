package com.example.codefellowship.entities;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int dateOfBirth;
    private String bio;

    @OneToMany(mappedBy = "user")
    public List<Post> postList;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_follower",
    joinColumns = {@JoinColumn(name = "user1_id")},
    inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private Set<ApplicationUser> usersManyToMany = new HashSet<ApplicationUser>();

    @ManyToMany(mappedBy = "usersManyToMany")
    private Set<ApplicationUser> follower= new HashSet<ApplicationUser>();

    public ApplicationUser(){}

    public ApplicationUser(String bio ,int dateOfBirth, String firstName, String lastName, String password, String username ){
        this.username =username;
        this.password =password;
        this.firstName= firstName;
        this.lastName=lastName;
        this.dateOfBirth=dateOfBirth;
        this.bio= bio;
    }


    public int getId() {
        return id;
    }

    public void addNewFollowed(ApplicationUser user){
        usersManyToMany.add(user);
    }


    public Set<ApplicationUser> getFollower() {
        return follower;
    }

    public void setFollower(Set<ApplicationUser> follower) {
        this.follower = follower;
    }

    public Set<ApplicationUser> getUsersManyToMany() {
        return usersManyToMany;
    }

    public void setUsersManyToMany(Set<ApplicationUser> usersManyToMany) {
        this.usersManyToMany = usersManyToMany;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
