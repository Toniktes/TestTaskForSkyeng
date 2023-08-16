package ru.skyeng.task.models;

import lombok.Getter;
import lombok.Setter;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.enums.TypeOfPostalItem;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "postal_item")
public class PostalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeOfPostalItem type;
    @Column(nullable = false)
    private Long addresseeIndex;
    @Column(nullable = false)
    private String addresseeAddress;
    @Column(nullable = false)
    private String addresseeName;
    @Enumerated(EnumType.STRING)
    private Status status;

}
