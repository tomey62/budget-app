package pl.zukowski.jwtauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.zukowski.jwtauth.dto.SumDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@NamedNativeQuery(name= "Transaction.sumByDate",
        query = "SELECT SUM(t.price) as sumTransaction, t.category from Transaction t where t.type = 'outgoing' " +
                "and MONTH(t.date_created)=?1 and t.card_id = ?2 group by t.category",
        resultSetMapping = "Mapping.SumDto")

@SqlResultSetMapping(name = "Mapping.SumDto",
                        classes = @ConstructorResult(targetClass = SumDto.class,
                        columns = {@ColumnResult(name = "sumTransaction", type = Long.class),
                                @ColumnResult(name = "category")}))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @NotNull
    private Long price;
    private String category;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_created")
    private String dateCreated;
    @ManyToOne
    @JoinColumn(name="card_id", nullable=false)
    private Card card;

}
