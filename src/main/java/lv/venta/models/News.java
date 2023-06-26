package lv.venta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Table(name = "news_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class News {
    @Column(name = "Idnews")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private long idNews;

    @Column(name = "Title")
    @NotNull
    @Size(min = 3, max = 35)
    private String title;

    @Column(name = "Description")
    @NotNull
    @Size(min = 3, max = 500)
    private String description;

    @Column(name = "StartDate")
    @NotNull
    private LocalDate startDate;

    @Column(name = "EndDate")
    @NotNull
    private LocalDate endDate;

    public News(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
