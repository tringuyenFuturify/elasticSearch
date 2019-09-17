//package io.futurify.authentication.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_role")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class UserRole {
//
//  @PrimaryKeyJoinColumn
//  @Column(name="user_id")
//  private Integer userId;
//
//  @PrimaryKeyJoinColumn
//  @Column(name="role_id")  
//  private Integer roleId;
//  
//  @JoinColumn(name = "role_id", referencedColumnName = "id")
//  @ManyToOne
//  private Role role;  
//}
