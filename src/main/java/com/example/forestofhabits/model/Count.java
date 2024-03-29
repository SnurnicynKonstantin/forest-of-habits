package com.example.forestofhabits.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Date;

//@Getter
//@Setter
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Count {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "created_time")
//    private Date createdTime;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="habit_id", nullable=false)
//    private Habit habit;
//
//    @PrePersist
//    protected void onCreate() {
//        createdTime = new Date();
//    }
}
