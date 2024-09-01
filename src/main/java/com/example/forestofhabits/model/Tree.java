package com.example.forestofhabits.model;

import com.example.forestofhabits.enums.TreeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"forest"})
public class Tree {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TreeType type;
  private ZonedDateTime createdAt;
  private int limitActionCount;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="forest_id", nullable=false)
  private Forest forest;

  @ToString.Exclude
  @OneToMany(mappedBy="tree", fetch = FetchType.LAZY)
  private Set<Action> actions;

  @PrePersist
  protected void onCreate() {
    createdAt = ZonedDateTime.now();
  }

}

//  forest_id SERIAL,

