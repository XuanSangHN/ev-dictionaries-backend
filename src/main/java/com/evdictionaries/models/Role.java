package com.evdictionaries.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("Id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  @JsonProperty("Code")
  private ERole code;

  @JsonProperty("Name")
  private String name;

  @JsonProperty("Description")
  private String description;

  public Role() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ERole getCode() {
    return code;
  }

  public void setCode(ERole code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}