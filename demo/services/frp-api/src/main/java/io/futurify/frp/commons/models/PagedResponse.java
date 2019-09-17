package io.futurify.frp.commons.models;


import java.util.List;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {

    private List<T> content;
    
    private int page;
    
    private int size;
    
    private long totalElements;
    
    private int totalPages;
    
    private boolean last;

}
