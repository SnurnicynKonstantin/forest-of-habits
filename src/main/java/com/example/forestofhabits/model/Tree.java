package com.example.forestofhabits.model;

import com.example.forestofhabits.enums.TreeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  @Enumerated(EnumType.STRING)
  private TreeType type;
  private Date createdAt;
  private int topLimit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="forest_id", nullable=false)
  private Forest forest;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

}

//  forest_id SERIAL,

