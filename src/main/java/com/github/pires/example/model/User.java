package com.github.pires.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * TODO add description
 */
@JsonIgnoreProperties(value = {"handler"})
public class User {

  @Id
  private String id;
  @Version
  @JsonIgnore
  private Long version;
  private Long created;
  private String email;
  private String name;
  private Boolean active;
  private String password;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", version=" + version + ", created=" +
        created + ", email=" + email + ", name=" + name + ", active=" + active + '}';
  }

}
