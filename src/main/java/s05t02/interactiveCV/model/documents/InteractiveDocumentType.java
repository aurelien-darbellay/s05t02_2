package s05t02.interactiveCV.model.documents;

import lombok.Getter;
import reactor.core.publisher.Mono;
import s05t02.interactiveCV.model.documents.cv.InteractiveCv;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Getter
public enum InteractiveDocumentType {
    INTERACTIVE_CV(InteractiveCv.class,"Interactive Cv");
    private final String simpleName;
    private final Class<? extends InteractiveDocument> clazz;
    InteractiveDocumentType(Class<? extends InteractiveDocument> clazz, String simpleName){
        this.clazz = clazz;
        this.simpleName = simpleName;
    }
    public Mono<InteractiveDocument> createDoc(String title) {
        try {
            InteractiveDocument newDoc = this.clazz.getDeclaredConstructor().newInstance();
            newDoc.setTitle(title);
            newDoc.setId(UUID.randomUUID().toString());
            return Mono.just(newDoc);
        }catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e){
            return Mono.error(e);
        }
    }

}
