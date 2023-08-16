package ru.skyeng.task.models;

import lombok.*;
import ru.skyeng.task.enums.Status;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "history")
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "postal_item_id")
    private Long postalItemId;
    @Column(name = "post_office_id")
    private Long postOfficeId;
    @Enumerated(EnumType.STRING)
    private Status status;
}
