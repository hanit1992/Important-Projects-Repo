
import java.util.function.Predicate;

public class Filter implements Predicate<RecipeRepresentation> {

    public Predicate<RecipeRepresentation> theChosenFilter;

    public Filter(Predicate<RecipeRepresentation> filterKind){
        this.theChosenFilter = filterKind;
    }

    @Override
    public boolean test(RecipeRepresentation r) {
        return theChosenFilter.test(r);
    }
}
