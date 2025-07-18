package s05t02.interactiveCV.model.documents.entries.concreteEntries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.PersistenceCreator;
import s05t02.interactiveCV.model.documents.entries.genEntriesFeatures.ContainerEntry;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Identity extends ContainerEntry {
    @JsonCreator
    @PersistenceCreator
    public Identity() {
        super();
    }

    @Builder.Default
    private List<String> names = new ArrayList<>();
    @Builder.Default
    private List<String> lastNames = new ArrayList<>();
}
