//package io.futurify.authentication.model;
//
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
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
//@Table(name = "role_permission")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class RolePermission {
//
//  @Id
//  @PrimaryKeyJoinColumn
//  @Column(name="role_id")
//  private Integer roleId;
//
//  @PrimaryKeyJoinColumn
//  @Column(name="permision_id")  
//  private String permissionId;
//  
//  @JoinColumn(name = "permission_id", referencedColumnName = "id")
//  @OneToMany
//  private List<Permission> permissions;  
//}
