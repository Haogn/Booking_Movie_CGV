package com.ra.mapper;

import com.ra.dto.request.genre.GenreRequest;
import com.ra.dto.response.genre.GenreResponse;
import com.ra.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreResponse toResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .isDeleted(genre.getIsDeleted())
                .build();
    }

    public Genre toEntity(GenreRequest genreRequest) {
        return Genre.builder()
                .genreName(genreRequest.getGenreName())
                .isDeleted(genreRequest.getIsDeleted())
                .build();
    }
}
