package com.whoami.milkcheque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "session")
public class SessionModel {
    @Id
    @Column(name = "session_id")
    private String sessionId;
}
