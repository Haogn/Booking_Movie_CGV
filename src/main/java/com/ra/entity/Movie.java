package com.ra.entity;

import com.ra.constant.MovieStatus;
import com.ra.constant.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "MOVIE")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieName;
    private String movieImage;
    private String director;
    private String cast;
    private String description;
    private Long runningTime;
    private LocalDate releaseDate;
    private LocalDate stopDate;
    private String language;
    private String rated;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "format_id")
    private Set<Format> formats;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MOVIE_GENRE",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
    private MovieStatus movieStatus;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
    private Boolean isDeleted;
    private Set<RoomType> projectionGenre ;
 }
