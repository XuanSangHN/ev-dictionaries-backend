package com.evdictionaries.payload.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
  @JsonProperty("Profile_Id")
  private Long profile_id;
  @JsonProperty("Username")
  private String username;
  @JsonProperty("Email")
  private String email;
  @JsonProperty("Firstname")
  private String firstname;
  @JsonProperty("Lastname")
  private String lastname;
  @JsonProperty("Phonenumber")
  private String phonenumber;
  @JsonProperty("Avatar_Url")
  private String avatar_url;
  @JsonProperty("Roles")
  private List<String> roles;
  @JsonProperty("Type")
  private String type = "Bearer";
  @JsonProperty("Token")
  private String token;
  @JsonProperty("CreatedBy")
  private String createdBy;
  @JsonProperty("CreatedDate")
  private Date createdDate;
  @JsonProperty("Status")
  private Long status;
  @JsonProperty("Address")
  private String address;

  public UserResponse(String accessToken, Long id, String username, String email, List<String> roles, String firstname, String lastname, String phonenumber, String avatar,Long status, String address,String createdBy, Date createdDate) {
    this.token = accessToken;
    this.profile_id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.firstname = firstname;
    this.lastname = lastname;
    this.phonenumber = phonenumber;
    this.avatar_url = avatar;
    this.status = status;
    this.address = address;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
  }

  public UserResponse(String accessToken, Long id, String username, String email, List<String> roles, String firstname, String lastname, String phonenumber, String avatar) {
    this.token = accessToken;
    this.profile_id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.firstname = firstname;
    this.lastname = lastname;
    this.phonenumber = phonenumber;
    this.avatar_url = avatar;
  }

  public UserResponse(Long profile_id, String username, String email, String firstname, String lastname, String phonenumber, String avatar_url, List<String> roles, String type, String token, String createdBy, Date createdDate) {
    this.profile_id = profile_id;
    this.username = username;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.phonenumber = phonenumber;
    this.avatar_url = avatar_url;
    this.roles = roles;
    this.type = type;
    this.token = token;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
  }

  public UserResponse() {
  }
}
