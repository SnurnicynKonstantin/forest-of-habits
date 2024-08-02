package com.example.forestofhabits.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime createdAt;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tree_id", nullable=false)
    private Tree tree;
}
