package wanted.backend.dto.request;

import static java.util.Objects.isNull;

public record JobOpeningSearch(
    String keyword,
    Integer offset,
    Integer size
) {

    public JobOpeningSearch(String keyword, Integer offset, Integer size) {
        this.keyword = keyword;
        this.offset = isNull(offset) ? 0 : offset;
        this.size = isNull(size) ? 10 : size;
    }

}
